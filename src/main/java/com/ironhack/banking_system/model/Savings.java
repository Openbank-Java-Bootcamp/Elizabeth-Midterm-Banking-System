package com.ironhack.banking_system.model;

import com.ironhack.banking_system.enums.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import static java.util.Currency.*;

@Data
@NoArgsConstructor
@Entity
public class Savings extends Account{

    @NotEmpty
    @Column(name = "secret_key")
    private String secretKey;

    //@DecimalMin(value = "100") //will this work???
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount"))
    })
    private Money minimumBalance = new Money(new BigDecimal("1000"), getInstance("USD"));

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @DecimalMax(value = ".05")
    @DecimalMin(value = "0")
    @Column(name = "interest_rate")
    private BigDecimal interestRate = new BigDecimal("0.0025");

    private LocalDate lastDateInterestApplied;



    //constructor for default minimumBalance and default interestRate, 1 account holder
    public Savings(AccountHolder primaryOwner, Money balance, String secretKey) {
        super(primaryOwner, balance);
        this.secretKey = secretKey;
        this.status = AccountStatus.ACTIVE;
        this.lastDateInterestApplied = LocalDate.now();
    }

    //constructor for default minimumBalance and default interestRate, 2 account holders
    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, String secretKey) {
        super(primaryOwner, secondaryOwner, balance);
        this.secretKey = secretKey;
        this.status = AccountStatus.ACTIVE;
        this.lastDateInterestApplied = LocalDate.now();
    }

    //constructor with default minimumBalance and custom interestRate, 1 account holder
    public Savings(AccountHolder primaryOwner, Money balance, String secretKey, BigDecimal interestRate) {
        super(primaryOwner, balance);
        this.secretKey = secretKey;
        setInterestRate(interestRate);
        this.status = AccountStatus.ACTIVE;
        this.lastDateInterestApplied = LocalDate.now();
    }

    //constructor with default minimumBalance and custom interestRate, 2 account holders
    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, String secretKey, BigDecimal interestRate) {
        super(primaryOwner, secondaryOwner, balance);
        this.secretKey = secretKey;
        setInterestRate(interestRate);
        this.status = AccountStatus.ACTIVE;
        this.lastDateInterestApplied = LocalDate.now();
    }

    //constructor with custom minimumBalance and default interestRate, 1 account holder
    public Savings(AccountHolder primaryOwner, Money balance, String secretKey, Money minimumBalance) {
        super(primaryOwner, balance);
        this.secretKey = secretKey;
        setMinimumBalance(minimumBalance);
        this.status = AccountStatus.ACTIVE;
        this.lastDateInterestApplied = LocalDate.now();
    }

    //constructor with custom minimumBalance and default interestRate, 2 account holders
    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, String secretKey, Money minimumBalance) {
        super(primaryOwner, secondaryOwner, balance);
        this.secretKey = secretKey;
        setMinimumBalance(minimumBalance);
        this.status = AccountStatus.ACTIVE;
        this.lastDateInterestApplied = LocalDate.now();
    }

    //constructor with custom minimumBalance and interestRate, 1 account holder
    public Savings(AccountHolder primaryOwner, Money balance, String secretKey, Money minimumBalance, BigDecimal interestRate) {
        super(primaryOwner, balance);
        this.secretKey = secretKey;
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
        this.status = AccountStatus.ACTIVE;
        this.lastDateInterestApplied = LocalDate.now();
    }


    //constructor with custom minimumBalance and interestRate, 2 account holders
    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, String secretKey, Money minimumBalance, BigDecimal interestRate) {
        super(primaryOwner, secondaryOwner, balance);
        this.secretKey = secretKey;
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
        this.status = AccountStatus.ACTIVE;
        this.lastDateInterestApplied = LocalDate.now();
    }


    //setters for minimumBalance and interestRate

    public void setMinimumBalance(Money minimumBalance) {
        BigDecimal lowerLimit = new BigDecimal("100");
        BigDecimal upperLimit = new BigDecimal("1000");
        BigDecimal minimumBalanceAmount = minimumBalance.getAmount();

        if (minimumBalanceAmount.compareTo(lowerLimit) <= 0) {
            this.minimumBalance = new Money(lowerLimit);
        } else if (minimumBalanceAmount.compareTo(upperLimit) >= 1) {
            this.minimumBalance = new Money(upperLimit);
        } else {
            this.minimumBalance = minimumBalance;
        }
    }

    public void setInterestRate(BigDecimal interestRate) {
        BigDecimal lowerLimit = new BigDecimal("0");
        BigDecimal upperLimit = new BigDecimal(".5");

        if (interestRate.compareTo(lowerLimit) <= 0) {
            this.interestRate = lowerLimit;
        } else if (interestRate.compareTo(upperLimit) >= 1) {
            this.interestRate = upperLimit;
        } else {
            this.interestRate = interestRate;
        }
    }



    //methods

    public void applyPenaltyFeeIfApplicable() {
        BigDecimal balanceAmount = super.getBalance().getAmount();
        if (balanceAmount.compareTo(minimumBalance.getAmount()) < 0) {
            BigDecimal penaltyFeeAmount = super.getPenaltyFee().getAmount();
            super.setBalance(new Money(balanceAmount.subtract(penaltyFeeAmount)));
        }
    }

    public void applyInterestIfApplicable() {
        LocalDate currentDate = LocalDate.now();
        int yearsSinceLastInterestApplied = Period.between(this.getLastDateInterestApplied(), currentDate).getYears();
        //if interest hasn't been applied in over a year
        if (yearsSinceLastInterestApplied >= 1) {
            BigDecimal balanceAmount = super.getBalance().getAmount(); //get current balance
            BigDecimal interestAmount = balanceAmount.multiply(interestRate); //calculate interest to be added
            BigDecimal newBalanceAmount = balanceAmount.add(interestAmount); //calculate new balance
            super.setBalance(new Money(newBalanceAmount));
            lastDateInterestApplied = LocalDate.now();
        }
    }

    @Override //override as penalty fee added
    public void debitAccount(Money debit) {
        BigDecimal debitAmount = debit.getAmount();
        BigDecimal currentBalanceAmount = this.getBalance().getAmount();
        if (currentBalanceAmount.compareTo(debitAmount) <= 0) {
            throw new IllegalArgumentException("Insufficient Funds");
        } else {
            BigDecimal newBalanceAmount = this.getBalance().getAmount().subtract(debitAmount);

            BigDecimal minimumBalanceAmount = this.getMinimumBalance().getAmount();
            if ((currentBalanceAmount.compareTo(minimumBalanceAmount) >= 0)
                    && (newBalanceAmount.compareTo(minimumBalanceAmount) < 0)) {
                BigDecimal penaltyFeeAmount = this.getPenaltyFee().getAmount();
                newBalanceAmount = newBalanceAmount.subtract(penaltyFeeAmount);
            }
            this.setBalance(new Money(newBalanceAmount));
        }
    }


}
