package com.buyersfirst.core.models;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ViewsRepository extends CrudRepository<Views, UUID> {
    @Query("SELECT vw.DesireId FROM Views vw WHERE vw.UserId = ?1")
    List<String> findDesireViewsByUser(String userId);

    @Query("SELECT vw.BidId FROM Views vw WHERE vw.UserId = ?1 AND vw.BidId IS NOT NULL")
    List<String> findBidViewsByUser(String userId);
}