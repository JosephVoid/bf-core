package com.buyersfirst.core.models;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AcceptedBidsRepository extends CrudRepository<AcceptedBids, UUID> {
    @Query("SELECT ab FROM AcceptedBids ab WHERE ab.BidId = ?1 AND ab.UserId = ?2")
    List<AcceptedBids> findByBidIdAndUserId(String BidId, String userId);

    @Query("SELECT ab.BidId FROM AcceptedBids ab WHERE ab.UserId = ?1")
    List<String> findAcceptedBidByUser(String userId);

    @Query(value = """
                SELECT COUNT(*) FROM accepted_bids
                JOIN bids ON bids.id = accepted_bids.bid_id
                JOIN desires ON bids.desire_id = desires.id
                WHERE desires.owner_id = ?1 AND desires.id = ?2
            """, nativeQuery = true)
    String countAcceptedOffersOnDesireByUser(String userId, String desireId);
}