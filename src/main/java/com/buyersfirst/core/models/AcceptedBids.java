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
@Table(schema = "accepted_bids")
public class AcceptedBids {
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

    @Column(name = "user_id")
    private String UserId;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    @Column(name = "bid_id")
    private String BidId;

    public String getBidId() {
        return BidId;
    }

    public void setBidId(String bidId) {
        BidId = bidId;
    }

    @Column(name = "accepted_on")
    private Timestamp AcceptedOn;

    public AcceptedBids() {
    }

    public AcceptedBids(String userId, String bidId, Timestamp acceptedOn) {
        UserId = userId;
        BidId = bidId;
        AcceptedOn = acceptedOn;
    }

    public Timestamp getAcceptedOn() {
        return AcceptedOn;
    }

    public void setAcceptedOn(Timestamp acceptedOn) {
        AcceptedOn = acceptedOn;
    }
}
