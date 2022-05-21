package com.ironhack.banking_system.controller.interfaces;

import com.ironhack.banking_system.model.ThirdParty;
import org.springframework.web.bind.annotation.RequestBody;

public interface ThirdPartyControllerInterface {

    public void saveThirdParty(ThirdParty thirdParty);

}
