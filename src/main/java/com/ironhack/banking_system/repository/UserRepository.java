package com.ironhack.banking_system.repository;

import com.ironhack.banking_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value= "SELECT * FROM user WHERE username = :username", nativeQuery = true)
    User findByUsername(String username);

}
