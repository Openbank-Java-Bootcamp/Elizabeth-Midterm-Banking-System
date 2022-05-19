package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.model.AccountHolder;
import com.ironhack.banking_system.repository.AccountHolderRepository;
import com.ironhack.banking_system.service.interfaces.AccountHolderServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountHolderService implements AccountHolderServiceInterface {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public AccountHolder getAccountHolderById(Long id) {
        Optional<AccountHolder> foundAccountHolder = accountHolderRepository.findById(id);
        if (foundAccountHolder.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account Holder with that ID");
        } else {
            return foundAccountHolder.get();
        }
    }

    public AccountHolder saveAccountHolder(AccountHolder accountHolder) {
        log.info("Saving new account holder {} inside of the database", accountHolder.getName());
        accountHolder.setPassword(passwordEncoder.encode(accountHolder.getPassword()));
        return accountHolderRepository.save(accountHolder);
    }

    public List<AccountHolder> getAccountHolders() {
        log.info("Fetching all account holders");
        return accountHolderRepository.findAll();
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //return null;
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            log.error("User not found in the database");
//            throw new UsernameNotFoundException("User not found in the database");
//        } else {
//            log.info("User is found in the database: {}", username);
//            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//            user.getRoles().forEach(role -> {
//                authorities.add(new SimpleGrantedAuthority(role.getName()));
//            });
//            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
//        }
//
//    }


}
