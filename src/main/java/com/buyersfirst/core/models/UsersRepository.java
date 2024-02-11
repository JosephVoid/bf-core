package com.buyersfirst.core.models;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;

public interface UsersRepository extends CrudRepository<Users, Integer> {
    Users findByEmail(String email);

    @Query(value = """
            SELECT users.*, COUNT(bids.id) AS bidded, COUNT(desires.id) AS desired FROM users
            LEFT JOIN desires ON desires.owner_id = users.id
            LEFT JOIN bids ON bids.owner_id = users.id
            WHERE users.id = :id
            GROUP BY users.id
            """, nativeQuery = true)
    String[][] findOneById(Integer id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = """
                UPDATE users SET
                    first_name = COALESCE(:fname, first_name),
                    last_name = COALESCE(:lname, last_name),
                    email = COALESCE(:email, email),
                    password = COALESCE(:pass, password),
                    description = COALESCE(:desc, description),
                    phone = COALESCE(:phone, phone),
                    picture = COALESCE(:pic, picture)
                    WHERE users.id = :id
            """, nativeQuery = true)
    void updateUsers(Integer id, String fname, String lname, String email, String pass, String desc, String phone,
            String pic);

    @Query(value = """
            SELECT users.* FROM users
            RIGHT JOIN user_wants ON user_wants.user_id = users.id
            WHERE user_wants.desire_id = :desireId
            """, nativeQuery = true)
    List<Users> findUserWhoWantDesires(Integer desireId);
}