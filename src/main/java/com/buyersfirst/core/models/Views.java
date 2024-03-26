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
@Table(schema = "views")
public class Views {
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

    @Column(name = "desire_id", nullable = true)
    private String DesireId;

    @Column(name = "bid_id", nullable = true)
    private String BidId;

    @Column(name = "viewed_on")
    private Timestamp ViewedOn;

    public Views(String userId, String desireId, String bidId, Timestamp viewedOn) {
        UserId = userId;
        DesireId = desireId;
        BidId = bidId;
        ViewedOn = viewedOn;
    }

    public Timestamp getViewedOn() {
        return ViewedOn;
    }

    public void setViewedOn(Timestamp viewedOn) {
        ViewedOn = viewedOn;
    }
}
