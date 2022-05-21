package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.model.ThirdParty;
import com.ironhack.banking_system.repository.ThirdPartyRepository;
import com.ironhack.banking_system.service.interfaces.ThirdPartyServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThirdPartyService implements ThirdPartyServiceInterface {

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    public ThirdParty saveThirdParty(ThirdParty thirdParty) {
        return thirdPartyRepository.save(thirdParty);
    }

}
