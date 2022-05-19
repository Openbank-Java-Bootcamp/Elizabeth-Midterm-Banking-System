package com.ironhack.banking_system.DTO;

import com.ironhack.banking_system.model.Money;
import lombok.Data;

@Data
public class CheckingDTO {
    private Long primaryOwnerId;
    private Long secondaryOwnerId;
    private Money balance;
    private String secretKey;
}
