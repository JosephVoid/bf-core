package com.buyersfirst.core.models;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
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

    @Column(name = "uuid", updatable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID Uuid;

    public UUID getUuid() {
        return Uuid;
    }

    public void setUuid(UUID uuid) {
        Uuid = uuid;
    }

    @PrePersist
    public void autofill() {
        this.setUuid(UUID.randomUUID());
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

    public AcceptedBids() {
    }

    public AcceptedBids(Integer userId, Integer bidId, Timestamp acceptedOn) {
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
