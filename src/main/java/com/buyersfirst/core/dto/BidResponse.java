package com.buyersfirst.core.dto;

import java.sql.Timestamp;

public class BidResponse {
    public String id;
    public Double price;
    public String description;
    public String picture;
    public String bidder;
    public String bidder_phone;
    public String bidder_email;
    public String bidder_description;
    public String bidder_picture;
    public Timestamp bidOn;
    public boolean isClosed;

    public BidResponse(String id, Double price, String description, String picture, String bidder, Timestamp bidOn,
            boolean isClosed) {
        this.id = id;
        this.price = price;
        this.description = description;
        this.picture = picture;
        this.bidder = bidder;
        this.bidOn = bidOn;
        this.isClosed = isClosed;
    }

    public BidResponse(String id, Double price, String description, String picture, String bidder, String bidder_phone,
            String bidder_email, String desc, String bidder_pic, Timestamp bidOn,
            boolean isClosed) {
        this.id = id;
        this.price = price;
        this.description = description;
        this.picture = picture;
        this.bidder = bidder;
        this.bidder_phone = bidder_phone;
        this.bidder_email = bidder_email;
        this.bidder_description = desc;
        this.bidder_picture = bidder_pic;
        this.bidOn = bidOn;
        this.isClosed = isClosed;
    }
}
