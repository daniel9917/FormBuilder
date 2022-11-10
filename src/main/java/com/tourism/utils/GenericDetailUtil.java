package com.tourism.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourism.repository.BaseObjectEntityRepository;
import com.tourism.domain.BaseEntity;
import com.tourism.errors.NotFoundException;
import com.tourism.errors.ServiceException;
import com.tourism.repository.BaseRepository;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

public abstract class GenericDetailUtil<Object extends BaseEntity, ObjectType extends BaseEntity, EntityObject extends BaseEntity, ID extends Serializable,
        ObjectRepository extends BaseRepository, ObjectTypeRepository extends BaseRepository, EntityObjectRepository extends BaseObjectEntityRepository>{

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private ObjectRepository objectRepository;

    private EntityObjectRepository entityObjectRepository;

    private ObjectTypeRepository objectTypeRepository;

    public GenericDetailUtil(ObjectRepository objectRepository, EntityObjectRepository entityObjectRepository, ObjectTypeRepository objectTypeRepository) {
        this.objectRepository = objectRepository;
        this.entityObjectRepository = entityObjectRepository;
        this.objectTypeRepository = objectTypeRepository;
        objectMapper.findAndRegisterModules();
    }

    public List<EntityObject> saveObjects(ID entityId, List<EntityObject> objects, Class<?> clazz, String fieldName){
        List<EntityObject> savedObjects = new ArrayList<>();
        List<EntityObject> previousEntityObjects = this.findAllByEntityId(entityId);

        if(!CollectionUtils.isEmpty(objects)){
            // Obtaining ids of list in order to validate object existence
            List<UUID> objectIds =objects.stream().map(entityObject -> {
                try {
                    return UUID.fromString(objectMapper.readValue(objectMapper.writeValueAsString(entityObject), HashMap.class).get(fieldName).toString());
                } catch (JsonProcessingException e) {
                    throw new ServiceException(e.getMessage());
                }
            }).collect(Collectors.toList());
            validateObject(objectIds);
            if(CollectionUtils.isEmpty(previousEntityObjects)){
                // Persisting all entity objects if no previows entity objects were recorded as active
                entityObjectRepository.saveAll(objects.stream().map(entityObject -> {
                    try {
                        entityObject = insertEntityId(entityId, entityObject, clazz);
                        entityObject.setFieldsOnCreation();
                        return entityObject;
                    } catch (JsonProcessingException e) {
                        throw new ServiceException(e.getMessage());
                    }
                }).collect(Collectors.toList())).forEach(entityObject -> {
                    savedObjects.add((EntityObject) entityObject);
                });
                return savedObjects;
            }else{
                // inserting entity id for each entityObject
                objects = objects.stream().map(entityObject -> {
                    try {
                        entityObject = insertEntityId(entityId, entityObject, clazz);
                        return entityObject;
                    }catch (JsonProcessingException e) {
                        throw new ServiceException(e.getMessage());
                    }
                }).collect(Collectors.toList());

                //Obtaining objects to add and delete with audit fields
                List<EntityObject> entityObjectsToAdd = getObjectsToAdd(previousEntityObjects, objects, fieldName);
                List<EntityObject> entityObjectsToDelete = getObjectsToDelete(previousEntityObjects, objects, fieldName);
                List<EntityObject> entityObjectsPersist = Stream.of(entityObjectsToAdd, entityObjectsToDelete)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
                entityObjectRepository.saveAll(entityObjectsPersist).forEach(entityObject -> savedObjects.add((EntityObject) entityObject));
                return savedObjects.size() > 0 ? savedObjects.stream().filter(not(EntityObject::isDeleted)).collect(Collectors.toList()) : previousEntityObjects;
            }
        }
        return previousEntityObjects;

    }

    public List<EntityObject> getObjectsToAdd(List<EntityObject> previosObjects, List<EntityObject> objects, String fieldName){
        List<EntityObject> objectsToAdd = objects.stream().filter(
                entityObject ->
                    previosObjects.stream().noneMatch(entityObject1 -> {
                        try {
                            return fieldMatch(entityObject, entityObject1, fieldName);
                        } catch (JsonProcessingException e) {
                            throw new ServiceException(e.getMessage());
                        }
                    })
                ).collect(Collectors.toList());
        objectsToAdd.forEach(EntityObject::setFieldsOnCreation);
        return objectsToAdd;
    }

    public List<EntityObject> getObjectsToDelete(List<EntityObject> previosObjects, List<EntityObject> objects, String fieldName){
        List<EntityObject> objectsToDelete = previosObjects.stream().filter(
                entityObject ->
                        objects.stream().noneMatch(entityObject1 -> {
                            try {
                                return fieldMatch(entityObject, entityObject1, fieldName);
                            } catch (JsonProcessingException e) {
                                throw new ServiceException(e.getMessage());
                            }
                        })
        ).collect(Collectors.toList());
        objectsToDelete.forEach(EntityObject::setFieldsOnDeletion);
        return objectsToDelete;
    }

    public boolean fieldMatch(EntityObject object1, EntityObject object2, String fieldName) throws JsonProcessingException{
        Map<String, String> objectMap1 = objectMapper.readValue(objectMapper.writeValueAsString(object1), HashMap.class);
        Map<String, String> objectMap2 = objectMapper.readValue(objectMapper.writeValueAsString(object2), HashMap.class);
        return objectMap1.get(fieldName).equals(objectMap2.get(fieldName));
    }

    public List<EntityObject> findAllByEntityId(ID id){
        return entityObjectRepository.findAllByObjectId(id);
    }

    private void validateObject(List<UUID> ids) throws NotFoundException {
        ids.forEach(uuid ->
            {
                try {
                    objectRepository.findById(uuid).orElseThrow();
                } catch (Throwable e) {
                    throw new NotFoundException((UUID) uuid);
                }
            }
        );
    }

    public ObjectType saveNewObjectType(ObjectType objectType){
        return (ObjectType) this.objectTypeRepository.save(objectType);
    }
    public Object saveNewObject(Object object){
        return (Object) this.objectRepository.save(object);
    }

    private EntityObject insertEntityId(ID id, EntityObject object, Class<?> clazz) throws JsonProcessingException {
        Map<String, String> mappedObject = objectMapper.readValue(objectMapper.writeValueAsString(object), HashMap.class);
        mappedObject.put("entityId", id.toString());
        return (EntityObject) objectMapper.readValue(objectMapper.writeValueAsString(mappedObject),  clazz);
    }
}
