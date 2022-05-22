package com.ironhack.banking_system.service.interfaces;

import com.ironhack.banking_system.model.ThirdParty;
import org.springframework.web.bind.annotation.RequestBody;

public interface ThirdPartyServiceInterface {

    ThirdParty saveThirdParty(ThirdParty thirdParty);

}
