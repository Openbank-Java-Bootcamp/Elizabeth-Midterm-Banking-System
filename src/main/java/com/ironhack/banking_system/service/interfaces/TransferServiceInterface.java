package com.ironhack.banking_system.service.interfaces;

import com.ironhack.banking_system.DTO.TransferDTO;
import com.ironhack.banking_system.model.Transfer;

public interface TransferServiceInterface {

    public Transfer saveTransfer(TransferDTO transferDTO);
}
