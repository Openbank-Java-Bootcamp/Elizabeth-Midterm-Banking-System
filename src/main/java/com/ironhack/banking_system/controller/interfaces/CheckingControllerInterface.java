package com.ironhack.banking_system.controller.interfaces;

import com.ironhack.banking_system.model.Checking;
import com.ironhack.banking_system.model.Money;

public interface CheckingControllerInterface {

    void saveChecking(Checking checking);

    Money getCheckingBalance(Long id);
}
