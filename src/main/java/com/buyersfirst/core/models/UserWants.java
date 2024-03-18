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
@Table(schema = "user_wants")
public class UserWants {
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

    @Column(name = "desire_id")
    private Integer DesireId;

    @Column(name = "wanted_on")
    private Timestamp WantedOn;

    public Timestamp getWantedOn() {
        return WantedOn;
    }

    public void setWantedOn(Timestamp wantedOn) {
        WantedOn = wantedOn;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public Integer getDesireId() {
        return DesireId;
    }

    public void setDesireId(Integer desireId) {
        DesireId = desireId;
    }
}
