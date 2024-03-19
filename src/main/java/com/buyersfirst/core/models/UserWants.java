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
@Table(schema = "user_wants")
public class UserWants {
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

    @Column(name = "desire_id")
    private String DesireId;

    @Column(name = "wanted_on")
    private Timestamp WantedOn;

    public Timestamp getWantedOn() {
        return WantedOn;
    }

    public void setWantedOn(Timestamp wantedOn) {
        WantedOn = wantedOn;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDesireId() {
        return DesireId;
    }

    public void setDesireId(String desireId) {
        DesireId = desireId;
    }
}
