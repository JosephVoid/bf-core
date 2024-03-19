package com.buyersfirst.core.models;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;

public interface DesireTagsRepository extends CrudRepository<DesireTags, UUID> {
    @Query("SELECT dt FROM DesireTags dt where dt.DesireId = ?1")
    List<DesireTags> findByDesireId(String id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM desire_tags WHERE desire_tags.desire_id = :id", nativeQuery = true)
    void deleteByDesireId(String id);
}