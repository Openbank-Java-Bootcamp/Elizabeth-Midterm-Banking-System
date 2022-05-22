package com.ironhack.banking_system.controller.interfaces;

import com.ironhack.banking_system.DTO.AccountBalanceOnlyDTO;
import com.ironhack.banking_system.DTO.AccountDTO;
import com.ironhack.banking_system.enums.AccountType;
import com.ironhack.banking_system.model.Account;
import com.ironhack.banking_system.model.Money;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


public interface AccountControllerInterface {

    public void saveAccount(AccountDTO accountDTO);

    public Account getAccount(Long accountId);

    public Money getAccountBalance(Long accountId);

    public void updateAccountBalance(Long accountId, AccountBalanceOnlyDTO accountBalanceOnlyDTO);



}
