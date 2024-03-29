package com.buyersfirst.core.dto;

import java.sql.Timestamp;

public class BidResponse {
    public String id;
    public Double price;
    public String description;
    public String picture;
    public String bidder;
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
}
