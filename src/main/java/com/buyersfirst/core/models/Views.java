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
@Table(schema = "views")
public class Views {
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

    @Column(name = "desire_id", nullable = true)
    private Integer DesireId;

    @Column(name = "bid_id", nullable = true)
    private Integer BidId;

    @Column(name = "viewed_on")
    private Timestamp ViewedOn;

    public Views(Integer userId, Integer desireId, Integer bidId, Timestamp viewedOn) {
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
