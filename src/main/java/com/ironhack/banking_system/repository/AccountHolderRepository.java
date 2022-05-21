package com.ironhack.banking_system.repository;

import com.ironhack.banking_system.model.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {

    AccountHolder findByUsername(String username);

    Optional<AccountHolder> findById(Long id);

}
