package com.ironhack.banking_system.repository;

import com.ironhack.banking_system.model.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {

    @Query(value = "SELECT * FROM third_party WHERE hashed_key = :hashedKey", nativeQuery = true)
    ThirdParty findByHashedKey(String hashedKey);
}
