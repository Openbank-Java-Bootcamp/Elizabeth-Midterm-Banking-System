package com.ironhack.banking_system.controller.interfaces;

import com.ironhack.banking_system.DTO.AccountBalanceOnlyDTO;
import com.ironhack.banking_system.DTO.AccountDTO;
import com.ironhack.banking_system.model.Account;
import com.ironhack.banking_system.model.Money;



public interface AccountControllerInterface {

    void saveAccount(AccountDTO accountDTO);

    Account getAccount(Long accountId);

    Money getAccountBalance(Long accountId);

    void updateAccountBalance(Long accountId, AccountBalanceOnlyDTO accountBalanceOnlyDTO);



}
