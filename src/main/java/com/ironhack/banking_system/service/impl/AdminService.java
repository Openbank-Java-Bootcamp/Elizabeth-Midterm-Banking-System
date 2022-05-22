package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.model.Admin;
import com.ironhack.banking_system.model.User;
import com.ironhack.banking_system.repository.AdminRepository;
import com.ironhack.banking_system.repository.UserRepository;
import com.ironhack.banking_system.service.interfaces.AdminServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements AdminServiceInterface {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Admin saveAdmin(Admin admin) {
        User foundUser = userRepository.findByUsername(admin.getUsername());
        if (foundUser != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    public List<Admin> getAdmins() {
        return adminRepository.findAll();
    }

    public void deleteAdmin(Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Admin Id");
        }
        adminRepository.delete(admin.get());
    }

}
