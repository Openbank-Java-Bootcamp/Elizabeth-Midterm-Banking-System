package com.ironhack.banking_system.DTO;

import com.ironhack.banking_system.model.Money;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AccountBalanceOnlyDTO {

    private Money balance;

}
