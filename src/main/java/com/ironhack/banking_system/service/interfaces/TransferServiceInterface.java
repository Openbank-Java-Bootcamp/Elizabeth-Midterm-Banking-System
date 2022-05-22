package com.ironhack.banking_system.service.interfaces;

import com.ironhack.banking_system.DTO.TransferDTO;
import com.ironhack.banking_system.DTO.TransferThirdPartyDTO;
import com.ironhack.banking_system.model.InternalTransfer;
import com.ironhack.banking_system.model.ThirdPartyTransfer;


public interface TransferServiceInterface {

    //public InternalTransfer saveInternalTransfer(TransferDTO transferDTO);
    //public InternalTransfer saveInternalTransfer(Long originAccountId, TransferDTO transferDTO);
    public InternalTransfer saveInternalTransfer(TransferDTO transferDTO);

    public ThirdPartyTransfer saveTransferFromThirdParty(String hashedKey, TransferThirdPartyDTO transferThirdPartyDTO);

    public ThirdPartyTransfer saveTransferToThirdParty(String hashedKey, TransferThirdPartyDTO transferThirdPartyDTO);
}
