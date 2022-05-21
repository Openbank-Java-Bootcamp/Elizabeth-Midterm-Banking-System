package com.ironhack.banking_system.controller.impl;

import com.ironhack.banking_system.DTO.TransferDTO;
import com.ironhack.banking_system.DTO.TransferThirdPartyDTO;
import com.ironhack.banking_system.controller.interfaces.TransferControllerInterface;
import com.ironhack.banking_system.model.Transfer;
import com.ironhack.banking_system.service.interfaces.TransferServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank/transfers")
public class TransferController implements TransferControllerInterface {

    @Autowired
    TransferServiceInterface transferService;

//    @PostMapping("/internaltransfers/{accountid}")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void saveTransfer(@PathVariable(name = "accountid") Long originAccountId, @RequestBody TransferDTO transferDTO) {
//        transferService.saveTransfer(originAccountId, transferDTO);
//    }
//
//    @PostMapping("/fromthirdparty/{hashedKey}")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void saveTransferFromThirdParty(@PathVariable String hashedKey, @RequestBody TransferThirdPartyDTO transferThirdPartyDTO) {
//        transferService.saveTransferFromThirdParty(hashedKey, transferThirdPartyDTO);
//    }
//
//    @PostMapping("/tothirdparty/{hashedKey}")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void saveTransferToThirdParty(@PathVariable String hashedKey, @RequestBody TransferThirdPartyDTO transferThirdPartyDTO) {
//        transferService.saveTransferToThirdParty(hashedKey, transferThirdPartyDTO);
//    }
}
