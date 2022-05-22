package com.ironhack.banking_system.DTO;

import com.ironhack.banking_system.model.Money;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalanceOnlyDTO {

    @NotNull
    private Money balance;

}
