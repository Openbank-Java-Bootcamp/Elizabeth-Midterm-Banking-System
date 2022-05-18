package com.ironhack.banking_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.util.Currency.getInstance;


@Data
@NoArgsConstructor
@Entity
@Table(name = "student_checking")
public class StudentChecking extends Checking{


    @Column(name = "monthly_maintenance_fee")
    //private Money monthlyMaintenanceFee = new Money(new BigDecimal("0"), getInstance("USD"));
    private Money monthlyMaintenanceFee = null;


    @Column(name = "minimum_balance")
   // private Money minimumBalance = new Money(new BigDecimal("0"), getInstance("USD"));
    private Money minimumBalance = null;


    //constructor for 1 owner
    public StudentChecking(AccountHolder primaryOwner, Money balance, String secretKey) {
        super(primaryOwner, balance, secretKey);
    }

    //constructor for 2 owners
    public StudentChecking(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, String secretKey) {
        super(primaryOwner, secondaryOwner, balance, secretKey);
    }
}
