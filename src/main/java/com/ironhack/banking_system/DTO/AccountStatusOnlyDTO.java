package com.ironhack.banking_system.DTO;

import com.ironhack.banking_system.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatusOnlyDTO {

    private AccountStatus status;

}