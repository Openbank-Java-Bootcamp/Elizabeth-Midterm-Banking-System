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
//@Table(name = "student_checking")
public class StudentChecking extends Checking{



    //CONSTRUCTORS

    public StudentChecking(Money balance, String secretKey, AccountHolder primaryOwner) {
        super(balance, secretKey, primaryOwner);
        super.setMinimumBalance(null);
        super.setMonthlyMaintenanceFee(null);
    }

    public StudentChecking(Money balance, String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, secretKey, primaryOwner, secondaryOwner);
        super.setMinimumBalance(null);
        super.setMonthlyMaintenanceFee(null);
    }
}
