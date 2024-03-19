package com.buyersfirst.core.models;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(schema = "deleted_desires")
public class DeletedDesires {
    @Id
    @Column(name = "id", updatable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID primKey;

    public UUID getPrimKey() {
        return primKey;
    }

    public void setPrimKey(UUID uuid) {
        primKey = uuid;
    }

    @PrePersist
    public void autofill() {
        this.setPrimKey(UUID.randomUUID());
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "owner_id")
    private String OwnerId;

    @Column(name = "title")
    private String Title;

    @Column(name = "description")
    private String Description;

    @Column(name = "desired_price")
    private Double DesiredPrice;

    @Column(name = "picture", nullable = true)
    private String Picture;

    @Column(name = "created")
    private Timestamp Created;

    @Column(name = "is_closed")
    private Integer IsClosed;

    public DeletedDesires(Desires desire) {
        this.id = desire.getId().toString();
        OwnerId = desire.getOwnerId();
        Title = desire.getTitle();
        Description = desire.getDescription();
        DesiredPrice = desire.getDesiredPrice();
        Picture = desire.getPicture();
        Created = desire.getCreated();
        IsClosed = desire.getIsClosed();
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String ownerId) {
        OwnerId = ownerId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Double getDesiredPrice() {
        return DesiredPrice;
    }

    public void setDesiredPrice(Double desiredPrice) {
        DesiredPrice = desiredPrice;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public Timestamp getCreated() {
        return Created;
    }

    public void setCreated(Timestamp created) {
        Created = created;
    }

    public Integer getIsClosed() {
        return IsClosed;
    }

    public void setIsClosed(Integer isClosed) {
        IsClosed = isClosed;
    }
}
