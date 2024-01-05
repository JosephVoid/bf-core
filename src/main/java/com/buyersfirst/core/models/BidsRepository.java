package com.buyersfirst.core.models;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BidsRepository extends CrudRepository<Bids, Integer>{
    @Query("SELECT bd FROM Bids bd where bd.DesireId = ?1")
    List<Bids> findByDesireId(Integer id);

    @Query(value = """
        SELECT bids.*, users.first_name, users.last_name FROM bids
        JOIN users ON users.id = bids.owner_id
    """, nativeQuery = true)
    String[][] findAllBidsJoined();

    @Query(value = """
        SELECT bids.*, users.first_name, users.last_name FROM bids
        JOIN users ON users.id = bids.owner_id WHERE bids.id = :id
    """, nativeQuery = true)
    String[][] findABidJoined(Integer id);
}