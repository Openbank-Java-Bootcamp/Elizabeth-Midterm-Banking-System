package com.ironhack.banking_system.service.interfaces;

import com.ironhack.banking_system.DTO.TransferDTO;
import com.ironhack.banking_system.DTO.TransferThirdPartyDTO;
import com.ironhack.banking_system.model.InternalTransfer;
import com.ironhack.banking_system.model.ThirdPartyTransfer;


public interface TransferServiceInterface {

    InternalTransfer saveInternalTransfer(TransferDTO transferDTO);

    ThirdPartyTransfer saveTransferFromThirdParty(String hashedKey, TransferThirdPartyDTO transferThirdPartyDTO);

    ThirdPartyTransfer saveTransferToThirdParty(String hashedKey, TransferThirdPartyDTO transferThirdPartyDTO);
}
