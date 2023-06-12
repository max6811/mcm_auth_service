package com.maxcmart.mcm_auth_service.repositories;

import com.maxcmart.mcm_auth_service.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
//    Optional<User> findById(String id);
}
