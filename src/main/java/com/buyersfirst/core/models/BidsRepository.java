package com.buyersfirst.core.models;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;

public interface BidsRepository extends CrudRepository<Bids, Integer>{
    @Query("SELECT bd FROM Bids bd where bd.DesireId = ?1")
    List<Bids> findByDesireId(Integer id);

    @Query(value = """
        SELECT bids.*, users.first_name, users.last_name FROM bids
        JOIN users ON users.id = bids.owner_id
        WHERE bids.desire_id = :id
    """, nativeQuery = true)
    String[][] findAllBidsJoined(Integer id);

    @Query(value = """
        SELECT bids.*, users.first_name, users.last_name FROM bids
        JOIN users ON users.id = bids.owner_id WHERE bids.id = :id
    """, nativeQuery = true)
    String[][] findABidJoined(Integer id);

    @Query("SELECT bds.id FROM Bids bds WHERE bds.OwnerId=?1")
    List<Integer> listBidsByOwner(Integer id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = """
        UPDATE bids SET 
            description = COALESCE(:desc, description),
            bid_price = COALESCE(:price, bid_price),
            picture = COALESCE(:pic, picture)
            WHERE bids.id = :id
    """, nativeQuery = true)
    void updateBid(Integer id, String desc, Double price, String pic);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Bids SET IsClosed = ?2 where id = ?1")
    void UpdateIsClosedStatus(Integer id, Integer status);
}