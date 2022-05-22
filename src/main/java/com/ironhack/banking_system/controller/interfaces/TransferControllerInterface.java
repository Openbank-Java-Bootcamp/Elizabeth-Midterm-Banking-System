package com.ironhack.banking_system.controller.interfaces;

import com.ironhack.banking_system.DTO.TransferDTO;
import com.ironhack.banking_system.DTO.TransferThirdPartyDTO;


public interface TransferControllerInterface {

    void saveInternalTransfer(TransferDTO transferDTO);

    void saveTransferFromThirdParty(String hashedKey, TransferThirdPartyDTO transferThirdPartyDTO);

    void saveTransferToThirdParty(String hashedKey, TransferThirdPartyDTO transferThirdPartyDTO);
}
