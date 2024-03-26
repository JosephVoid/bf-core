package com.buyersfirst.core.models;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface DeletedDesiresRepository extends CrudRepository<DeletedDesires, UUID> {

}