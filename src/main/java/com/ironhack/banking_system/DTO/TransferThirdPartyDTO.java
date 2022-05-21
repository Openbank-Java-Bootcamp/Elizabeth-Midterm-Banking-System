package com.ironhack.banking_system.DTO;

import com.ironhack.banking_system.model.Money;
import lombok.Data;

@Data
public class TransferThirdPartyDTO {
    private Money funds;
    private Long accountId;
    private String accountSecretKey;

}
