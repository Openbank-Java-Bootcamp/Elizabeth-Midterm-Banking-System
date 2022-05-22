package com.ironhack.banking_system.DTO;

import com.ironhack.banking_system.model.Money;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferDTO {
    @NotNull
    private Long originAccountId;
    @NotNull
    private Money funds;
    @NotNull
    private String destinationAccountOwner;
    @NotNull
    private Long destinationAccountId;
}
