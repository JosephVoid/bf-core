package com.buyersfirst.core.models;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "bids")
public class Bids {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }    

    @Column(name = "desire_id")
    private Integer DesireId;

    @Column(name = "owner_id")
    private Integer OwnerId;

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

    public Bids(Integer desireId, Integer ownerId, String description, Double bidPrice, String picture,
            Timestamp bidDateTime, Integer isClosed) {
        DesireId = desireId;
        OwnerId = ownerId;
        Description = description;
        BidPrice = bidPrice;
        Picture = picture;
        BidDateTime = bidDateTime;
        IsClosed = isClosed;
    }
    public Integer getDesireId() {
        return DesireId;
    }
    public void setDesireId(Integer desireId) {
        DesireId = desireId;
    }
    public Integer getOwnerId() {
        return OwnerId;
    }
    public void setOwnerId(Integer ownerId) {
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
