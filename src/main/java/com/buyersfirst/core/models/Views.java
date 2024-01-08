package com.buyersfirst.core.models;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
