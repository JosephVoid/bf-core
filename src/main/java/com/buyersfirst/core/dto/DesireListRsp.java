package com.buyersfirst.core.dto;

import java.sql.Timestamp;
import java.util.ArrayList;

public class DesireListRsp {
    public String id;
    public String title;
    public String description;
    public String postedBy;
    public String userPostedId;
    public Double price;
    public Double minPrice;
    public Double maxPrice;
    public String metric;
    public Integer views;
    public Integer wants;
    public ArrayList<String> tags;
    public Integer bids;
    public boolean isClosed;
    public String picture;
    public Timestamp postedOn;

    public DesireListRsp() {
    }

    public DesireListRsp(String id, String title, String description, String postedBy, String userPostedId,
            Double price, Integer views,
            Integer wants, Integer bids, boolean isClosed, String picture, Timestamp postedOn, ArrayList<String> tags,
            Double minPrice, Double maxPrice, String metric) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.postedBy = postedBy;
        this.userPostedId = userPostedId;
        this.price = price;
        this.views = views;
        this.wants = wants;
        this.bids = bids;
        this.isClosed = isClosed;
        this.picture = picture;
        this.postedOn = postedOn;
        this.tags = tags;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.metric = metric;
    }
}
