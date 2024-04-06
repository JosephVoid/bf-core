package com.buyersfirst.core.models;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;

public interface BidsRepository extends CrudRepository<Bids, UUID> {
    @Query("SELECT bd FROM Bids bd where bd.DesireId = ?1")
    List<Bids> findByDesireId(String id);

    @Query("SELECT bd.DesireId FROM Bids bd where bd.OwnerId = ?1")
    List<String> findBidForIdByUserId(String id);

    @Query(value = """
                SELECT bids.*, users.first_name, users.last_name FROM bids
                JOIN users ON users.id = bids.owner_id
                WHERE bids.desire_id = :id
            """, nativeQuery = true)
    String[][] findAllBidsJoined(String id);

    @Query(value = """
                SELECT bids.*, users.first_name, users.last_name FROM bids
                JOIN users ON users.id = bids.owner_id
                WHERE bids.owner_id = :id
            """, nativeQuery = true)
    String[][] findBidsByUser(String id);

    @Query(value = """
                SELECT bids.*, users.first_name, users.last_name FROM bids
                JOIN users ON users.id = bids.owner_id WHERE bids.id = :id
            """, nativeQuery = true)
    String[][] findABidJoined(String id);

    @Query("SELECT bds.id FROM Bids bds WHERE bds.OwnerId=?1")
    List<String> listBidsByOwner(String id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = """
                UPDATE bids SET
                    description = COALESCE(:desc, description),
                    bid_price = COALESCE(:price, bid_price),
                    picture = COALESCE(:pic, picture)
                    WHERE bids.id = :id
            """, nativeQuery = true)
    void updateBid(String id, String desc, Double price, String pic);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Bids SET IsClosed = ?2 where id = ?1")
    void UpdateIsClosedStatus(String id, Integer status);
}