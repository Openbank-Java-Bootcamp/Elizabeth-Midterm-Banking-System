package com.ironhack.banking_system.model;

import com.ironhack.banking_system.enums.AccountStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

import static java.util.Currency.*;

@Entity
@Table(name = "savings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Savings extends Account{

    @Column(name = "secret_key")
    private String secretKey;

    //@DecimalMin(value = "100") //will this work???
    @Column(name = "minimum_balance")
    private Money minimumBalance = new Money(new BigDecimal("1000"), getInstance("USD"));

    @Column(name = "creation_date")
    private Date creationDate;

    private AccountStatus status;

    @DecimalMax(value = ".05")
    @DecimalMin(value = "0")
    @Column(name = "interest_rate")
    private BigDecimal interestRate = new BigDecimal("0.0025");
}
