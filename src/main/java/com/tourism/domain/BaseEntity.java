package com.tourism.domain;

import com.tourism.config.AuditTrailLog;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EntityListeners(AuditTrailLog.class)
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 3079160803954956035L;
    @Id
    @Column(name = "id")
    private UUID id;
    @Column
    private LocalDateTime createdAt;
    @Column
    private UUID createdBy;
    @Column
    private LocalDateTime updatedAt;
    @Column
    private UUID updatedBy;
    @Column
    private boolean deleted;
    @Column
    private long deletedAt;

    public void setFieldsOnCreation(){
        UUID userId = UUID.randomUUID();
        this.setId(userId);
        this.setCreatedBy(userId);
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedBy(userId);
        this.setUpdatedAt(LocalDateTime.now());

    }
    public void setFieldsOnUpdate(){
        UUID userId = UUID.randomUUID();
        this.setUpdatedBy(userId);
        this.setUpdatedAt(LocalDateTime.now());
    }
    public void setFieldsOnDeletion(){
        UUID userId = UUID.randomUUID();
        this.setUpdatedBy(userId);
        this.setUpdatedAt(LocalDateTime.now());
        this.setDeleted(true);
        this.setDeletedAt(LocalDateTime.now().getNano());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UUID getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UUID updatedBy) {
        this.updatedBy = updatedBy;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(long deletedAt) {
        this.deletedAt = deletedAt;
    }
}
