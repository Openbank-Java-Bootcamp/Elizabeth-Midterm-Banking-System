package com.ironhack.banking_system.service.interfaces;

import com.ironhack.banking_system.model.Checking;
import com.ironhack.banking_system.model.Money;

public interface CheckingServiceInterface {

    Checking saveChecking(Checking checking);

    Money getCheckingBalance(Long id);
}
