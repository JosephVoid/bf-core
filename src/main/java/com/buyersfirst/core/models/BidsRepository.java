package com.buyersfirst.core.models;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BidsRepository extends CrudRepository<Bids, Integer>{
    @Query("SELECT bd FROM Bids bd where bd.DesireId = ?1")
    List<Bids> findByDesireId(Integer id);
}