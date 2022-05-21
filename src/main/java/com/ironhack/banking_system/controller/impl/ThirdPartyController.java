package com.ironhack.banking_system.controller.impl;

import com.ironhack.banking_system.model.ThirdParty;
import com.ironhack.banking_system.service.interfaces.ThirdPartyServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bank/thirdparties")
public class ThirdPartyController {

    @Autowired
    ThirdPartyServiceInterface thirdPartyService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveThirdParty(@RequestBody ThirdParty thirdParty) {
        thirdPartyService.saveThirdParty(thirdParty);
    }
}
