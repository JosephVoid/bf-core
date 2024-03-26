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
@Table(name = "notify_tags_user")
public class NotifyTags {
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

    @Column(name = "tag_id")
    private String tagId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    public NotifyTags() {
    }

    public NotifyTags(String tagId, String userId, String email, String phone) {
        this.tagId = tagId;
        this.userId = userId;
        this.email = email;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
