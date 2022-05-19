package com.ironhack.banking_system.model;

import com.ironhack.banking_system.enums.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import static java.util.Currency.*;

@Entity
@Data
@NoArgsConstructor
public class Checking extends Account{

    @NotEmpty
    @Column(name = "secret_key")
    private String secretKey;

    @Embedded
    //@Column(name = "minimum_balance") //final as this does not change
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount"))
    })
    private Money minimumBalance = new Money(new BigDecimal("250"), getInstance("USD"));

    @Embedded
    //@Column(name = "monthly_maintenance_fee") //final as this does not change
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "monthly_maintenance_fee_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee_amount"))
    })
    private Money monthlyMaintenanceFee = new Money(new BigDecimal("12"), getInstance("USD"));

    @Column
    @Enumerated(EnumType.STRING)
    private AccountStatus status;


    //constructor for 1 owner


    public Checking(AccountHolder primaryOwner, Money balance, String secretKey) {
        super(primaryOwner, balance);
        this.secretKey = secretKey;
        this.status = AccountStatus.ACTIVE;
    }

    //constructor for 2 owners
    public Checking(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, String secretKey) {
        super(primaryOwner, secondaryOwner, balance);
        this.secretKey = secretKey;
        this.status = AccountStatus.ACTIVE;
    }

    //methods
    public void applyPenaltyFeeIfApplicable() {
        BigDecimal balanceAmount = super.getBalance().getAmount();
        if (balanceAmount.compareTo(minimumBalance.getAmount()) < 0) {
            BigDecimal penaltyFeeAmount = super.getPenaltyFee().getAmount();
            super.setBalance(new Money(balanceAmount.subtract(penaltyFeeAmount)));
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
