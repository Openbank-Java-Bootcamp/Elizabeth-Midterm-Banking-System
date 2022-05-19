package com.ironhack.banking_system.service.interfaces;

import com.ironhack.banking_system.model.AccountHolder;

import java.util.List;

public interface AccountHolderServiceInterface {

    AccountHolder getAccountHolderById(Long id);

    AccountHolder saveAccountHolder(AccountHolder accountHolder);

    List<AccountHolder> getAccountHolders();
}
