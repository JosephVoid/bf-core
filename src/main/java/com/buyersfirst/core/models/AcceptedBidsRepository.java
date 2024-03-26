package com.buyersfirst.core.models;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AcceptedBidsRepository extends CrudRepository<AcceptedBids, UUID> {
    @Query("SELECT ab FROM AcceptedBids ab WHERE ab.BidId = ?1 AND ab.UserId = ?2")
    List<AcceptedBids> findByBidIdAndUserId(String BidId, String userId);
}