package com.ironhack.banking_system.DTO;

import com.ironhack.banking_system.model.Money;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferThirdPartyDTO {

    @NotNull
    private Money funds;
    @NotNull
    private Long accountId;
    @NotNull
    private String accountSecretKey;

}
