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
@Table(schema = "bids")
public class Bids {
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

    @Column(name = "desire_id")
    private String DesireId;

    @Column(name = "owner_id")
    private String OwnerId;

    @Column(name = "description")
    private String Description;

    @Column(name = "bid_price")
    private Double BidPrice;

    @Column(name = "picture", nullable = true)
    private String Picture;

    @Column(name = "bid_date_time")
    private Timestamp BidDateTime;

    @Column(name = "is_closed")
    private Integer IsClosed;

    public Bids() {
    }

    public Bids(String desireId, String ownerId, String description, Double bidPrice, String picture,
            Timestamp bidDateTime, Integer isClosed) {
        DesireId = desireId;
        OwnerId = ownerId;
        Description = description;
        BidPrice = bidPrice;
        Picture = picture;
        BidDateTime = bidDateTime;
        IsClosed = isClosed;
    }

    public String getDesireId() {
        return DesireId;
    }

    public void setDesireId(String desireId) {
        DesireId = desireId;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String ownerId) {
        OwnerId = ownerId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Double getBidPrice() {
        return BidPrice;
    }

    public void setBidPrice(Double bidPrice) {
        BidPrice = bidPrice;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public Timestamp getBidDateTime() {
        return BidDateTime;
    }

    public void setBidDateTime(Timestamp bidDateTime) {
        BidDateTime = bidDateTime;
    }

    public Integer getIsClosed() {
        return IsClosed;
    }

    public void setIsClosed(Integer isClosed) {
        IsClosed = isClosed;
    }
}
