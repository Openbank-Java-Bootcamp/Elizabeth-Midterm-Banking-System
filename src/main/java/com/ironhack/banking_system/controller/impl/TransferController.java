package com.ironhack.banking_system.controller.impl;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTransfer(@RequestBody Transfer transfer) {

    }
}
