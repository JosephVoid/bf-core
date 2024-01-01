package com.buyersfirst.core.models;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;

public interface DesiresRepository extends CrudRepository<Desires, Integer>{
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Desires SET IsClosed = ?2 where id = ?1")
    void UpdateIsClosedStatus(Integer id, Integer status);
}