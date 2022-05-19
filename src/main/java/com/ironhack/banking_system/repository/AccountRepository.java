package com.ironhack.banking_system.repository;

import com.ironhack.banking_system.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT * FROM account WHERE id = :id", nativeQuery = true)
    Optional<Account> findById(Long id);
}
