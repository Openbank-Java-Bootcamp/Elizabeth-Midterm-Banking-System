package com.ironhack.banking_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import static java.util.Currency.*;

@Entity
@Table(name = "credit_card")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard extends Account{

    @Column(name = "credit_limit")
    private Money creditLimit = new Money(new BigDecimal("100"), getInstance("USD"));

    @Column(name = "interest_rate")
    private BigDecimal interestRate = new BigDecimal(".2");
}
