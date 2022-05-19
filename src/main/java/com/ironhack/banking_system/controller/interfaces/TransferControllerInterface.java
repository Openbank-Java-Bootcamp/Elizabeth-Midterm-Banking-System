package com.ironhack.banking_system.controller.interfaces;

import com.ironhack.banking_system.model.Transfer;
import org.springframework.web.bind.annotation.RequestBody;

public interface TransferControllerInterface {

    public void saveTransfer(Transfer transfer);
}
