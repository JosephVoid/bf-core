package com.buyersfirst.core.models;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "accepted_bids")
public class AcceptedBids {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name = "user_id")
    private Integer UserId;

    public Integer getUserId() {
        return UserId;
    }
    public void setUserId(Integer userId) {
        UserId = userId;
    }

    @Column(name = "bid_id")
    private Integer BidId;
    public Integer getBidId() {
        return BidId;
    }
    public void setBidId(Integer bidId) {
        BidId = bidId;
    }

    @Column(name = "accepted_on")
    private Timestamp AcceptedOn;
    public Timestamp getAcceptedOn() {
        return AcceptedOn;
    }
    public void setAcceptedOn(Timestamp acceptedOn) {
        AcceptedOn = acceptedOn;
    }
}
