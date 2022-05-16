package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.repository.AddressRepository;
import com.ironhack.banking_system.service.interfaces.AddressServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService implements AddressServiceInterface {

    @Autowired
    private AddressRepository addressRepository;
}
