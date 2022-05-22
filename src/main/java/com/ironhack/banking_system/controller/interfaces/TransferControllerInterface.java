package com.ironhack.banking_system.controller.interfaces;

import com.ironhack.banking_system.DTO.TransferDTO;
import com.ironhack.banking_system.DTO.TransferThirdPartyDTO;
import com.ironhack.banking_system.model.Transfer;
import org.springframework.web.bind.annotation.RequestBody;

public interface TransferControllerInterface {

    public void saveInternalTransfer(TransferDTO transferDTO);
    //public void saveInternalTransfer(Long originAccountId, TransferDTO transferDTO);


    public void saveTransferFromThirdParty(String hashedKey, TransferThirdPartyDTO transferThirdPartyDTO);

    public void saveTransferToThirdParty(String hashedKey, TransferThirdPartyDTO transferThirdPartyDTO);
}
