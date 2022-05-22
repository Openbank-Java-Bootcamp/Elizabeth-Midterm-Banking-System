package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.model.AccountHolder;
import com.ironhack.banking_system.model.User;
import com.ironhack.banking_system.repository.AccountHolderRepository;
import com.ironhack.banking_system.repository.UserRepository;
import com.ironhack.banking_system.service.interfaces.AccountHolderServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountHolderService implements AccountHolderServiceInterface {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public AccountHolder getAccountHolderById(Long id) {
        return accountHolderRepository.findById(id).get();
    }

    public AccountHolder saveAccountHolder(AccountHolder accountHolder) {
        User foundUser = userRepository.findByUsername(accountHolder.getUsername());
        if (foundUser != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        accountHolder.setPassword(passwordEncoder.encode(accountHolder.getPassword()));
        userRepository.save(accountHolder);
        roleService.addRoleToUser(accountHolder.getUsername(), "ROLE_USER");
        return accountHolder;
    }

    public List<AccountHolder> getAccountHolders() {
        log.info("Fetching all account holders");
        return accountHolderRepository.findAll();
    }


}
