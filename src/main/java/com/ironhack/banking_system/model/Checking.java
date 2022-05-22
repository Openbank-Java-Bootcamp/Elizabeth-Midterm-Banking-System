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


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "monthly_maintenance_fee_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee_amount"))
    })
    private Money monthlyMaintenanceFee = new Money(new BigDecimal("12"));



    //CONSTRUCTORS


    public Checking(Money balance, String secretKey, AccountHolder primaryOwner) {
        super(balance, secretKey, primaryOwner);
        super.setMinimumBalance(new Money(BigDecimal.valueOf(250)));
    }

    public Checking(Money balance, String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, secretKey, primaryOwner, secondaryOwner);
        super.setMinimumBalance(new Money(BigDecimal.valueOf(250)));
    }

    //methods
//    public void applyPenaltyFeeIfApplicable() {
//        BigDecimal balanceAmount = super.getBalance().getAmount();
//        if (balanceAmount.compareTo(super.getMinimumBalance().getAmount()) < 0) {
//            BigDecimal penaltyFeeAmount = super.getPenaltyFee().getAmount();
//            super.setBalance(new Money(balanceAmount.subtract(penaltyFeeAmount)));
//        }
//    }

//    @Override //override as penalty fee added
//    public void debitAccount(Money debit) {
//        BigDecimal debitAmount = debit.getAmount();
//        BigDecimal currentBalanceAmount = this.getBalance().getAmount();
//        if (currentBalanceAmount.compareTo(debitAmount) <= 0) {
//            throw new IllegalArgumentException("Insufficient Funds");
//        } else {
//            BigDecimal newBalanceAmount = this.getBalance().getAmount().subtract(debitAmount);
//
//            BigDecimal minimumBalanceAmount = this.getMinimumBalance().getAmount();
//            if ((currentBalanceAmount.compareTo(minimumBalanceAmount) >= 0)
//                    && (newBalanceAmount.compareTo(minimumBalanceAmount) < 0)) {
//                BigDecimal penaltyFeeAmount = this.getPenaltyFee().getAmount();
//                newBalanceAmount = newBalanceAmount.subtract(penaltyFeeAmount);
//            }
//            this.setBalance(new Money(newBalanceAmount));
//        }
//    }
}
