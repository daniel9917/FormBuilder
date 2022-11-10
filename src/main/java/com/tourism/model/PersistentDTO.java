package com.tourism.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class PersistentDTO implements BaseDTO{

    protected UUID id;
    protected UUID createdBy;
    protected LocalDateTime createdAt;
    protected UUID updatedBy;
    protected LocalDateTime updatedAt;
    protected boolean deleted;
    protected long deletedAt;

//    public UUID getId() {
//        return id;
//    }
//
//    public void setId(UUID id) {
//        this.id = id;
//    }
//
//    public UUID getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(UUID createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public UUID getUpdatedBy() {
//        return updatedBy;
//    }
//
//    public void setUpdatedBy(UUID updatedBy) {
//        this.updatedBy = updatedBy;
//    }
//
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(LocalDateTime updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public boolean isDeleted() {
//        return deleted;
//    }
//
//    public void setDeleted(boolean deleted) {
//        this.deleted = deleted;
//    }
//
//    public long getDeletedAt() {
//        return deletedAt;
//    }
//
//    public void setDeletedAt(long deletedAt) {
//        this.deletedAt = deletedAt;
//    }

}
