package com.ironhack.banking_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

import static java.util.Currency.getInstance;

@Entity
@Table(name = "student_checking")
public class StudentChecking extends Checking{

    @Column(name = "monthly_maintenance_fee")//or change to null???
    private Money monthlyMaintenanceFee = new Money(new BigDecimal("0"), getInstance("USD"));

    @Column(name = "minimum_balance")//or change to null???
    private Money minimumBalance = new Money(new BigDecimal("0"), getInstance("USD"));
}
