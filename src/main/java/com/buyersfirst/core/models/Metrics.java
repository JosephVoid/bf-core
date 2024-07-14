package com.buyersfirst.core.models;

import java.sql.Types;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(schema = "metrics")
public class Metrics {
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

    @Column(name = "metric")
    private String Metric;

    public String getMetric() {
        return Metric;
    }

    public void setMetric(String metric) {
        Metric = metric;
    }

    @Column(name = "display")
    private String Display;

    public String getDisplay() {
        return Display;
    }

    public void setDisplay(String display) {
        Display = display;
    }
}
