package com.ironhack.banking_system.model;

import com.ironhack.banking_system.enums.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;



@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "balance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "balance_amount"))
    })
    private Money balance;

    @NotEmpty
    @Column(name = "secret_key")
    private String secretKey;

    @ManyToOne
    @JoinColumn(name = "primary_owner")
    private AccountHolder primaryOwner;

    @ManyToOne
    @JoinColumn(name = "secondary_owner")
    private AccountHolder secondaryOwner;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount"))
    })
    private Money minimumBalance; //does not apply to all account types

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "penalty_fee_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "penalty_fee_amount"))
    })
    private final Money penaltyFee = new Money(new BigDecimal("40"));//this is the same for all account types

    @Column(name = "creation_date")
    private LocalDate creationDate = LocalDate.now(); // will automatically assign today's date

    @Column
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(name = "date_interest_due")
    private LocalDate dateInterestDue;




    //CONSTRUCTORS
    public Account(Money balance, String secretKey, AccountHolder primaryOwner) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.status = AccountStatus.ACTIVE;
    }

    public Account(Money balance, String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.status = AccountStatus.ACTIVE;
    }




    //METHODS

    public void applyPenaltyFeeIfApplicable() {
        BigDecimal balanceAmount = getBalance().getAmount();
        if (minimumBalance != null) {
            if (balanceAmount.compareTo(getMinimumBalance().getAmount()) < 0) {
                BigDecimal penaltyFeeAmount = getPenaltyFee().getAmount();
                setBalance(new Money(balanceAmount.subtract(penaltyFeeAmount)));
            }
        }
    }

    public void applyInterestIfApplicable() {
        if (interestRate != null && interestRate.compareTo(BigDecimal.valueOf(0)) > 0) {
            LocalDate currentDate = LocalDate.now();
            int daysInterestOverdue = (int) currentDate.until(dateInterestDue, ChronoUnit.DAYS);
            if (daysInterestOverdue < 0) {
                BigDecimal balanceAmount = balance.getAmount(); //get current balance
                BigDecimal interestAmount = balanceAmount.multiply(interestRate); //calculate interest to be added
                BigDecimal newBalanceAmount = balanceAmount.add(interestAmount); //calculate new balance
                setBalance(new Money(newBalanceAmount));
                setDateInterestDue(LocalDate.now().plusYears(1));
            }
        }
    }


    public void debitAccount(Money debit) {
        BigDecimal debitAmount = debit.getAmount();
        //apply interest to account if due
        applyInterestIfApplicable();
        //check if balance is sufficient to pay out the debit
        BigDecimal currentBalanceAmount = this.getBalance().getAmount();
        if (currentBalanceAmount.compareTo(debitAmount) <= 0) {
            throw new IllegalArgumentException("Insufficient Funds");
        } else {
            BigDecimal newBalanceAmount = this.getBalance().getAmount().subtract(debitAmount);
            //check if balance will fall below minimumBalance and apply penalty fee if necessary
            if (minimumBalance != null) {
                BigDecimal minimumBalanceAmount = this.getMinimumBalance().getAmount();
                if ((currentBalanceAmount.compareTo(minimumBalanceAmount) >= 0) //if balance currently above minimumBalance but will fall below with this debit
                        && (newBalanceAmount.compareTo(minimumBalanceAmount) < 0)) {
                    BigDecimal penaltyFeeAmount = this.getPenaltyFee().getAmount();
                    newBalanceAmount = newBalanceAmount.subtract(penaltyFeeAmount); //apply penalty fee
                }
            }
            this.setBalance(new Money(newBalanceAmount));
        }
    }



    public void creditAccount(Money funds) {
        BigDecimal creditAmount = funds.getAmount();
        BigDecimal currentBalanceAmount = this.getBalance().getAmount();
        BigDecimal newBalanceAmount = this.getBalance().getAmount().add(creditAmount);
        this.setBalance(new Money(newBalanceAmount));
    }
}
