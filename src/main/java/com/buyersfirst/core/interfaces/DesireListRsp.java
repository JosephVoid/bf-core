package com.buyersfirst.core.interfaces;

import java.sql.Timestamp;
import java.util.ArrayList;

public class DesireListRsp {
    public Integer id;
    public String title;
    public String description;
    public String postedBy;
    public Double price;
    public Integer views;
    public Integer wants;
    public ArrayList<String> tags;
    public Integer bids;
    public boolean isClosed;
    public String picture;
    public Timestamp postedOn;
    
    public DesireListRsp(){}

    public DesireListRsp(Integer id, String title, String description, String postedBy, Double price, Integer views,
            Integer wants, Integer bids, boolean isClosed, String picture, Timestamp postedOn, ArrayList<String> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.postedBy = postedBy;
        this.price = price;
        this.views = views;
        this.wants = wants;
        this.bids = bids;
        this.isClosed = isClosed;
        this.picture = picture;
        this.postedOn = postedOn;
        this.tags = tags;
    }
}
