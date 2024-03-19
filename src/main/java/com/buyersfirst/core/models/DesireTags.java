package com.buyersfirst.core.models;

import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(schema = "desire_tags")
public class DesireTags {

    public DesireTags(String tagId, String desireId) {
        TagId = tagId;
        DesireId = desireId;
    }

    public DesireTags() {
    }

    @Id
    @Column(name = "id", updatable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID uuid) {
        id = uuid;
    }

    @PrePersist
    public void autofill() {
        this.setId(UUID.randomUUID());
    }

    @Column(name = "tag_id")
    private String TagId;

    @Column(name = "desire_id")
    private String DesireId;

    public String getTagId() {
        return TagId;
    }

    public void setTagId(String tagId) {
        TagId = tagId;
    }

    public String getDesireId() {
        return DesireId;
    }

    public void setDesireId(String desireId) {
        DesireId = desireId;
    }
}
