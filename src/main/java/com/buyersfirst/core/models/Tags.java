package com.buyersfirst.core.models;

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
@Table(schema = "tags")
public class Tags {
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

    @Column(name = "name")
    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
