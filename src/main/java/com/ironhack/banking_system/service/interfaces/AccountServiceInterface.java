package com.ironhack.banking_system.service.interfaces;

import com.ironhack.banking_system.DTO.AccountBalanceOnlyDTO;
import com.ironhack.banking_system.DTO.AccountDTO;
import com.ironhack.banking_system.enums.AccountType;
import com.ironhack.banking_system.model.Account;
import com.ironhack.banking_system.model.Money;

import java.util.Optional;

public interface AccountServiceInterface {

    Account saveAccount(AccountDTO accountDTO);

    public Account getAccount(Long id);

    public Money getAccountBalance(Long accountId);

    public void updateAccountBalance(Long accountId, AccountBalanceOnlyDTO accountBalanceOnlyDTO);

}
