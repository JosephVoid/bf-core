package com.buyersfirst.core.interfaces;

import java.sql.Timestamp;

public class BidResponse {
    public Integer id;
    public Double price;
    public String description;
    public String picture;
    public String bidder;
    public Timestamp bidOn;
    public boolean isClosed;
    
    public BidResponse(Integer id, Double price, String description, String picture, String bidder, Timestamp bidOn,
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
