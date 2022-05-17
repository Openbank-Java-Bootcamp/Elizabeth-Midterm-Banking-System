package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.model.AccountHolder;
import com.ironhack.banking_system.model.Admin;
import com.ironhack.banking_system.repository.AdminRepository;
import com.ironhack.banking_system.service.interfaces.AdminServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AdminService implements AdminServiceInterface {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Admin saveAdmin(Admin admin) {
        log.info("Saving new admin {} inside of the database", admin.getName());
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    public List<Admin> getAdmins() {
        log.info("Fetching all admins");
        return adminRepository.findAll();
    }



}
