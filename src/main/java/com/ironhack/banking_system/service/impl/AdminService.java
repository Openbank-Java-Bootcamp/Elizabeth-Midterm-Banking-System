package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.repository.AdminRepository;
import com.ironhack.banking_system.service.interfaces.AdminServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminService implements AdminServiceInterface {

    @Autowired
    private AdminRepository adminRepository;

}
