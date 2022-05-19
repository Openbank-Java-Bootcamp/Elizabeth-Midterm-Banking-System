package com.ironhack.banking_system.DTO;

import com.ironhack.banking_system.model.Money;
import lombok.Data;

@Data
public class TransferDTO {
    private Long originAccountId;
    private String destinationAccountOwner;
    private Long destinationAccountId;
    private Money funds;
}
