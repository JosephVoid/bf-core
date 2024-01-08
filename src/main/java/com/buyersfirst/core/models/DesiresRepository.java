package com.buyersfirst.core.models;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;

public interface DesiresRepository extends CrudRepository<Desires, Integer>{
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Desires SET IsClosed = ?2 where id = ?1")
    void UpdateIsClosedStatus(Integer id, Integer status);

    /**
     * @param filterBy Valid tag names
     * @param sortBy created, wants, desired_price
     * @param sortDir ASC or DESC
     * @return List<Object>
     */
    @Query(value = """
        SELECT 
            desires.id,
            users.first_name,
            users.last_name,
            desires.title,
            desires.description,
            desires.desired_price,
            desires.picture,
            desires.created,
            desires.is_closed,
            tags.name AS tag_name,
            COUNT(bids.id) AS bids,
            COUNT(user_wants.id) AS wants, 
            COUNT(views.id) AS views 
        FROM desires
        LEFT JOIN users ON users.id = desires.owner_id
        LEFT JOIN bids ON bids.desire_id = desires.id
        LEFT JOIN user_wants ON user_wants.desire_id = desires.id
        LEFT JOIN views ON views.desire_id = desires.id
        RIGHT JOIN desire_tags ON desire_tags.desire_id = desires.id
        LEFT JOIN tags ON tags.id = desire_tags.tag_id
        WHERE tags.name LIKE %:filterBy%
        GROUP BY desires.id, tags.id
        ORDER BY 
            CASE WHEN :sortBy = 'created:ASC'  THEN created END ASC, 
            CASE WHEN :sortBy = 'created:DESC' THEN created END DESC,
            CASE WHEN :sortBy = 'wants:ASC'  THEN wants END ASC, 
            CASE WHEN :sortBy = 'wants:DESC' THEN wants END DESC,
            CASE WHEN :sortBy = 'desired_price:ASC'  THEN desired_price END ASC, 
            CASE WHEN :sortBy = 'desired_price:DESC' THEN desired_price END DESC
        """, nativeQuery = true)
    String[][] findAllDesiresJoined(String filterBy, String sortBy);

    /**
     * @param filterBy Valid tag names
     * @param sortBy created, wants, desired_price
     * @param sortDir ASC or DESC
     * @return List<Object>
     */
    @Query(value = """
        SELECT 
            desires.id,
            users.first_name,
            users.last_name,
            desires.title,
            desires.description,
            desires.desired_price,
            desires.picture,
            desires.created,
            desires.is_closed,
            tags.name AS tag_name,
            COUNT(bids.id) AS bids,
            COUNT(user_wants.id) AS wants, 
            COUNT(views.id) AS views 
        FROM desires
        LEFT JOIN users ON users.id = desires.owner_id
        LEFT JOIN bids ON bids.desire_id = desires.id
        LEFT JOIN user_wants ON user_wants.desire_id = desires.id
        LEFT JOIN views ON views.desire_id = desires.id
        RIGHT JOIN desire_tags ON desire_tags.desire_id = desires.id
        LEFT JOIN tags ON tags.id = desire_tags.tag_id
        WHERE desires.id = :id
        GROUP BY desires.id, tags.id
        """, nativeQuery = true)
    String[][] findADesireJoined(Integer id);

    @Query("SELECT dsr.id FROM Desires dsr WHERE dsr.OwnerId=?1")
    List<Integer> listDesiresByOwner(Integer id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = """
        UPDATE desires SET 
            title = COALESCE(:title, title),
            description = COALESCE(:desc, description),
            desired_price = COALESCE(:price, desired_price),
            picture = COALESCE(:pic, picture)
            WHERE desires.id = :id
    """, nativeQuery = true)
    void updateDesire(Integer id, String title, String desc, Double price, String pic);
}