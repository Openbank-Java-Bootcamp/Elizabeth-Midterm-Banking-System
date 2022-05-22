package com.ironhack.banking_system.controller.impl;

import com.ironhack.banking_system.DTO.TransferDTO;
import com.ironhack.banking_system.DTO.TransferThirdPartyDTO;
import com.ironhack.banking_system.controller.interfaces.TransferControllerInterface;
import com.ironhack.banking_system.model.Transfer;
import com.ironhack.banking_system.service.interfaces.TransferServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class TransferController implements TransferControllerInterface {

    @Autowired
    TransferServiceInterface transferService;


    @PostMapping("/transfers")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveInternalTransfer(@RequestBody @Valid TransferDTO transferDTO) {
        transferService.saveInternalTransfer(transferDTO);
    }

    @PostMapping("/fromthirdparty/{hashedKey}")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTransferFromThirdParty(@PathVariable String hashedKey, @RequestBody @Valid TransferThirdPartyDTO transferThirdPartyDTO) {
        transferService.saveTransferFromThirdParty(hashedKey, transferThirdPartyDTO);
    }

    @PostMapping("/tothirdparty/{hashedKey}")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTransferToThirdParty(@PathVariable String hashedKey, @RequestBody @Valid TransferThirdPartyDTO transferThirdPartyDTO) {
        transferService.saveTransferToThirdParty(hashedKey, transferThirdPartyDTO);
    }
}
