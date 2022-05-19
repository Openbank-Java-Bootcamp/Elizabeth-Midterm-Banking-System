package com.ironhack.banking_system.service.interfaces;

import com.ironhack.banking_system.model.Transfer;
import org.springframework.web.bind.annotation.RequestBody;

public interface TransferServiceInterface {

    public Transfer saveTransfer(Transfer transfer);
}
