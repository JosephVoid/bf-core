package com.buyersfirst.core.models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Integer>{
    Users findByEmail(String email);

    @Query(value = """
        SELECT users.*, COUNT(bids.id) AS bidded, COUNT(desires.id) AS desired FROM users
        LEFT JOIN desires ON desires.owner_id = users.id
        LEFT JOIN bids ON bids.owner_id = users.id
        WHERE users.id = :id
        GROUP BY users.id
        """, nativeQuery = true)
    String[][] findOneById(Integer id);
}