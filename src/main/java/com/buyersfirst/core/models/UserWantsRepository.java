package com.buyersfirst.core.models;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;

public interface UserWantsRepository extends CrudRepository<UserWants, UUID> {
    @Query("SELECT uw FROM UserWants uw where uw.DesireId = ?1 AND uw.UserId = ?2")
    List<UserWants> findByDesireUserId(String desireId, String userId);

    @Query("SELECT uw FROM UserWants uw where uw.DesireId = ?1")
    List<UserWants> findByDesire(String desireId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "INSERT INTO user_wants (`id`, `user_id`, `desire_id`, `wanted_on`) VALUES (uuid(), ?2, ?1, ?3)", nativeQuery = true)
    void addUserWants(String desireId, String userId, Timestamp wantedOn);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM UserWants WHERE UserId = ?2 AND DesireId = ?1")
    void removeUserWants(String desireId, String userId);
}