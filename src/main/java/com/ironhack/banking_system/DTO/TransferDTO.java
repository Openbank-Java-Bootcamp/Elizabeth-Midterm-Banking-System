package com.ironhack.banking_system.DTO;

import com.ironhack.banking_system.model.Money;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferDTO {
    private Money funds;
    private String destinationAccountOwner;
    private Long destinationAccountId;
}
