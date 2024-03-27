package com.buyersfirst.core.models;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;

public interface DesiresRepository extends CrudRepository<Desires, UUID> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Desires SET IsClosed = ?2 where id = ?1")
    void UpdateIsClosedStatus(String id, Integer status);

    /**
     * @param filterBy Valid tag names
     * @param sortBy   created, wants, desired_price
     * @param sortDir  ASC or DESC
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
                COALESCE(bids_count, 0) AS bids,
                COALESCE(wants_count, 0) AS wants,
                COALESCE(views_count, 0) AS views
            FROM desires
            LEFT JOIN users ON users.id = desires.owner_id
            LEFT JOIN (
                SELECT desire_id, COUNT(id) AS bids_count
                FROM bids
                GROUP BY desire_id
            ) AS bid_counts ON bid_counts.desire_id = desires.id
            LEFT JOIN (
                SELECT desire_id, COUNT(id) AS wants_count
                FROM user_wants
                GROUP BY desire_id
            ) AS want_counts ON want_counts.desire_id = desires.id
            LEFT JOIN (
                SELECT desire_id, COUNT(id) AS views_count
                FROM views
                GROUP BY desire_id
            ) AS view_counts ON view_counts.desire_id = desires.id
            RIGHT JOIN desire_tags ON desire_tags.desire_id = desires.id
            LEFT JOIN tags ON tags.id = desire_tags.tag_id
            WHERE tags.name LIKE %:filterBy% AND desires.id IS NOT NULL
            GROUP BY desires.id, users.first_name, users.last_name, desires.title, desires.description, desires.desired_price, desires.picture, desires.created, desires.is_closed, tags.name, bids_count, wants_count, views_count
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
     * @param searchString Valid desire names
     * @param sortBy       created, wants, desired_price
     * @param sortDir      ASC or DESC
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
                COALESCE(bids_count, 0) AS bids,
                COALESCE(wants_count, 0) AS wants,
                COALESCE(views_count, 0) AS views
            FROM desires
            LEFT JOIN users ON users.id = desires.owner_id
            LEFT JOIN (
                SELECT desire_id, COUNT(id) AS bids_count
                FROM bids
                GROUP BY desire_id
            ) AS bid_counts ON bid_counts.desire_id = desires.id
            LEFT JOIN (
                SELECT desire_id, COUNT(id) AS wants_count
                FROM user_wants
                GROUP BY desire_id
            ) AS want_counts ON want_counts.desire_id = desires.id
            LEFT JOIN (
                SELECT desire_id, COUNT(id) AS views_count
                FROM views
                GROUP BY desire_id
            ) AS view_counts ON view_counts.desire_id = desires.id
            RIGHT JOIN desire_tags ON desire_tags.desire_id = desires.id
            LEFT JOIN tags ON tags.id = desire_tags.tag_id
            WHERE desires.title LIKE %:searchString% AND desires.id IS NOT NULL
            GROUP BY desires.id, users.first_name, users.last_name, desires.title, desires.description, desires.desired_price, desires.picture, desires.created, desires.is_closed, tags.name, bids_count, wants_count, views_count
            ORDER BY
                CASE WHEN :sortBy = 'created:ASC'  THEN created END ASC,
                CASE WHEN :sortBy = 'created:DESC' THEN created END DESC,
                CASE WHEN :sortBy = 'wants:ASC'  THEN wants END ASC,
                CASE WHEN :sortBy = 'wants:DESC' THEN wants END DESC,
                CASE WHEN :sortBy = 'desired_price:ASC'  THEN desired_price END ASC,
                CASE WHEN :sortBy = 'desired_price:DESC' THEN desired_price END DESC
            """, nativeQuery = true)
    String[][] searchDesiresJoined(String searchString, String sortBy);

    /**
     * @param filterBy Valid tag names
     * @param sortBy   created, wants, desired_price
     * @param sortDir  ASC or DESC
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
                COALESCE(bids_count, 0) AS bids,
                COALESCE(wants_count, 0) AS wants,
                COALESCE(views_count, 0) AS views
            FROM desires
            LEFT JOIN users ON users.id = desires.owner_id
            LEFT JOIN (
                SELECT desire_id, COUNT(id) AS bids_count
                FROM bids
                GROUP BY desire_id
            ) AS bid_counts ON bid_counts.desire_id = desires.id
            LEFT JOIN (
                SELECT desire_id, COUNT(id) AS wants_count
                FROM user_wants
                GROUP BY desire_id
            ) AS want_counts ON want_counts.desire_id = desires.id
            LEFT JOIN (
                SELECT desire_id, COUNT(id) AS views_count
                FROM views
                GROUP BY desire_id
            ) AS view_counts ON view_counts.desire_id = desires.id
            RIGHT JOIN desire_tags ON desire_tags.desire_id = desires.id
            LEFT JOIN tags ON tags.id = desire_tags.tag_id
            WHERE desires.id = :id AND desires.id IS NOT NULL
            GROUP BY desires.id, users.first_name, users.last_name, desires.title, desires.description, desires.desired_price, desires.picture, desires.created, desires.is_closed, tags.name, bids_count, wants_count, views_count
            """, nativeQuery = true)
    String[][] findADesireJoined(String id);

    @Query("SELECT dsr.id FROM Desires dsr WHERE dsr.OwnerId=?1")
    List<String> listDesiresByOwner(String id);

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
    void updateDesire(String id, String title, String desc, Double price, String pic);

    /**
     * @param searchString Valid desire names
     * @param sortBy       created, wants, desired_price
     * @param sortDir      ASC or DESC
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
                COALESCE(bids_count, 0) AS bids,
                COALESCE(wants_count, 0) AS wants,
                COALESCE(views_count, 0) AS views
            FROM desires
            LEFT JOIN users ON users.id = desires.owner_id
            LEFT JOIN (
                SELECT desire_id, COUNT(id) AS bids_count
                FROM bids
                GROUP BY desire_id
            ) AS bid_counts ON bid_counts.desire_id = desires.id
            LEFT JOIN (
                SELECT desire_id, COUNT(id) AS wants_count
                FROM user_wants
                GROUP BY desire_id
            ) AS want_counts ON want_counts.desire_id = desires.id
            LEFT JOIN (
                SELECT desire_id, COUNT(id) AS views_count
                FROM views
                GROUP BY desire_id
            ) AS view_counts ON view_counts.desire_id = desires.id
            RIGHT JOIN desire_tags ON desire_tags.desire_id = desires.id
            LEFT JOIN tags ON tags.id = desire_tags.tag_id
            WHERE desires.owner_id=:userId
            GROUP BY desires.id, users.first_name, users.last_name, desires.title, desires.description, desires.desired_price, desires.picture, desires.created, desires.is_closed, tags.name, bids_count, wants_count, views_count
            ORDER BY created DESC
            """, nativeQuery = true)
    String[][] desiresByUser(String userId);
}