package com.ironhack.banking_system.controller.interfaces;

import com.ironhack.banking_system.DTO.AccountBalanceOnlyDTO;
import com.ironhack.banking_system.DTO.AccountStatusOnlyDTO;
import com.ironhack.banking_system.model.Savings;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface SavingsControllerInterface {

    public Savings getSavings(Long id);

    public void saveSavings(Savings savings);

    public void partialUpdateBalance(Long id, AccountBalanceOnlyDTO partialAccount);

    public void partialUpdateStatus(Long id,AccountStatusOnlyDTO partialAccount);
}
