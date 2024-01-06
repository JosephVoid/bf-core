package com.buyersfirst.core.interfaces;

import java.util.List;

import com.buyersfirst.core.models.Users;

public class UserRsp extends Users{
    public List<Integer> desire_ids;
    public List<Integer> bid_ids;
    public UserRsp(Users user, List<Integer> desire_list, List<Integer> bid_list) {
        super(user.getId(), user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getPassword(), user.getPicture(), user.getDescription(), user.getPhone(), user.getJoinedOn());
        this.desire_ids = desire_list;
        this.bid_ids = bid_list;
    }
}
