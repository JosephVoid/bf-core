package com.buyersfirst.core.models;

import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Integer>{
    Users findByEmail(String email);
}