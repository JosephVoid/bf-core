package com.buyersfirst.core.models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NotifyTagsRepository extends CrudRepository<NotifyTags, Integer> {
    @Query(value = """
            SELECT phone, GROUP_CONCAT(tags.name) AS tags
            FROM notify_tags_user
            LEFT JOIN tags ON tags.id = notify_tags_user.tag_id
            WHERE tag_id IN (:id)
            GROUP BY phone;
                """, nativeQuery = true)
    String[][] findContactByTag(Integer[] id);
}
