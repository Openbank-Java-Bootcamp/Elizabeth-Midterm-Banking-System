package com.ironhack.banking_system.DTO;

import com.ironhack.banking_system.enums.AccountType;
import com.ironhack.banking_system.model.Money;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class AccountDTO {

    @NotNull
    private AccountType accountType;

    @NotNull
    private Money balance;

    @NotNull
    private String secretKey;

    @NotNull
    private Long primaryOwnerId = null;

    private Long secondaryOwnerId = null;
    private Money minimumBalance = null;
    private BigDecimal interestRate = null;
    private Money creditLimit = null;

    public AccountDTO(AccountType accountType, Money balance, String secretKey, Long primaryOwnerId) {
        this.accountType = accountType;
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwnerId = primaryOwnerId;
    }

    public AccountDTO(AccountType accountType, Money balance, String secretKey, Long primaryOwnerId, Long secondaryOwnerId) {
        this.accountType = accountType;
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwnerId = primaryOwnerId;
        this.secondaryOwnerId = secondaryOwnerId;
    }

    public AccountDTO(AccountType accountType, Money balance, String secretKey, Long primaryOwnerId, Money minimumBalance) {
        this.accountType = accountType;
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwnerId = primaryOwnerId;
        this.minimumBalance = minimumBalance;
    }

    public AccountDTO(AccountType accountType, Money balance, String secretKey, Long primaryOwnerId, Long secondaryOwnerId, Money minimumBalance) {
        this.accountType = accountType;
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwnerId = primaryOwnerId;
        this.secondaryOwnerId = secondaryOwnerId;
        this.minimumBalance = minimumBalance;
    }

    public AccountDTO(AccountType accountType, Money balance, String secretKey, Long primaryOwnerId, Money minimumBalance, BigDecimal interestRate) {
        this.accountType = accountType;
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwnerId = primaryOwnerId;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }

    public AccountDTO(AccountType accountType, Money balance, String secretKey, Long primaryOwnerId, Long secondaryOwnerId, Money minimumBalance, BigDecimal interestRate) {
        this.accountType = accountType;
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwnerId = primaryOwnerId;
        this.secondaryOwnerId = secondaryOwnerId;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }

    public AccountDTO(AccountType accountType, Money balance, String secretKey, Long primaryOwnerId, BigDecimal interestRate) {
        this.accountType = accountType;
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwnerId = primaryOwnerId;
        this.interestRate = interestRate;
    }

    public AccountDTO(AccountType accountType, Money balance, String secretKey, Long primaryOwnerId, Long secondaryOwnerId, BigDecimal interestRate) {
        this.accountType = accountType;
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwnerId = primaryOwnerId;
        this.secondaryOwnerId = secondaryOwnerId;
        this.interestRate = interestRate;
    }

    public AccountDTO(AccountType accountType, Money balance, String secretKey, Long primaryOwnerId, BigDecimal interestRate, Money creditLimit) {
        this.accountType = accountType;
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwnerId = primaryOwnerId;
        this.interestRate = interestRate;
        this.creditLimit = creditLimit;
    }

    public AccountDTO(AccountType accountType, Money balance, String secretKey, Long primaryOwnerId, Long secondaryOwnerId, BigDecimal interestRate, Money creditLimit) {
        this.accountType = accountType;
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwnerId = primaryOwnerId;
        this.secondaryOwnerId = secondaryOwnerId;
        this.interestRate = interestRate;
        this.creditLimit = creditLimit;
    }

}
