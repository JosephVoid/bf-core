package com.buyersfirst.core.models;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface TagsRepository extends CrudRepository<Tags, UUID> {

}