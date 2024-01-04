package com.buyersfirst.core.interfaces;

import java.sql.Timestamp;
import java.util.ArrayList;

public class SingleDesire extends DesireListRsp{
    public ArrayList<Integer> bidList;

    public SingleDesire(Integer id, String title, String description, String postedBy, Double price, Integer views,
            Integer wants, Integer bids, boolean isClosed, String picture, Timestamp postedOn, ArrayList<String> tags,
            ArrayList<Integer> bidList) {
        super(id, title, description, postedBy, price, views, wants, bids, isClosed, picture, postedOn, tags);
        this.bidList = bidList;
    }

    public SingleDesire(ArrayList<Integer> bidList) {
        this.bidList = bidList;
    }
    
}
