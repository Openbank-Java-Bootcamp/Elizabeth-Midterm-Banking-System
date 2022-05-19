package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.model.Transaction;
import com.ironhack.banking_system.model.Transfer;
import com.ironhack.banking_system.repository.TransferRepository;
import com.ironhack.banking_system.service.interfaces.TransferServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class TransferService implements TransferServiceInterface {

    @Autowired
    TransferRepository transferRepository;


//    public Transfer saveTransfer(Transfer transfer) {
//        Account originAccount =
//        Transaction.transfer()
//    }
}
