package com.springbank.user.auth.repository;

import com.springbank.user.core.usercore.models.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{'account.username': ?0}")
    Optional<User> findByAccountUsername(String username);

}
