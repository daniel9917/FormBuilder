package com.tourism.repository;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseObjectEntityRepository<E, ID> extends BaseRepository{
    List<E> findAllByObjectId(ID id);
}
