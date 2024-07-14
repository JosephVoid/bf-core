package com.buyersfirst.core.dto;

import java.sql.Timestamp;
import java.util.ArrayList;

public class SingleDesire extends DesireListRsp {
    public ArrayList<String> bidList;

    public SingleDesire(String id, String title, String description, String postedBy, Double price, Integer views,
            Integer wants, Integer bids, boolean isClosed, String picture, Timestamp postedOn, ArrayList<String> tags,
            ArrayList<String> bidList, Double minPrice, Double maxPrice, String metric) {
        super(id, title, description, postedBy, price, views, wants, bids, isClosed, picture, postedOn, tags, minPrice,
                maxPrice, metric);
        this.bidList = bidList;
    }

    public SingleDesire(ArrayList<String> bidList) {
        this.bidList = bidList;
    }

}
