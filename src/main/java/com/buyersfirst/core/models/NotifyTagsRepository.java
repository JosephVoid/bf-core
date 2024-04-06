package com.buyersfirst.core.models;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;

public interface NotifyTagsRepository extends CrudRepository<NotifyTags, UUID> {
    @Query(value = """
            SELECT phone, GROUP_CONCAT(tags.name) AS tags
            FROM notify_tags_user
            LEFT JOIN tags ON tags.id = notify_tags_user.tag_id
            WHERE tag_id IN (:id)
            GROUP BY phone;
                """, nativeQuery = true)
    String[][] findContactByTag(String[] id);

    @Query("SELECT nt FROM NotifyTags nt where nt.tagId = :tagId AND nt.userId = :userId")
    List<NotifyTags> findByTagAndUser(String userId, String tagId);

    @Query("SELECT nt FROM NotifyTags nt where nt.userId = :userId")
    List<NotifyTags> findByUser(String userId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM notify_tags_user WHERE user_id = :userId", nativeQuery = true)
    void deleteNotifyTags(String userId);
}
