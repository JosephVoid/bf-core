package com.buyersfirst.core.models;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "desires")
public class Desires {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }    

    @Column(name = "owner_id")
    private Integer OwnerId;

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
    public Integer getOwnerId() {
        return OwnerId;
    }
    public void setOwnerId(Integer ownerId) {
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
