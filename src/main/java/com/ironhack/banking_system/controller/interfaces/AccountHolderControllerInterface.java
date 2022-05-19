package com.ironhack.banking_system.controller.interfaces;

import com.ironhack.banking_system.model.AccountHolder;

import java.util.List;

public interface AccountHolderControllerInterface {

    AccountHolder getAccountHolderById(Long id);

    List<AccountHolder> getAccountHolders();

    void saveAccountHolder(AccountHolder accountHolder);



}
