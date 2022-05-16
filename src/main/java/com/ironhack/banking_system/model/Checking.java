package com.ironhack.banking_system.model;

import com.ironhack.banking_system.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import static java.util.Currency.*;

@Entity
@Table(name = "checking")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Checking extends Account{

    @Column(name = "secret_key")
    private String secretKey;

    @Column(name = "minimum_balance")
    private Money minimumBalance = new Money(new BigDecimal("250"), getInstance("USD"));

    @Column(name = "monthly_maintenance_fee")
    private Money monthlyMaintenanceFee = new Money(new BigDecimal("12"), getInstance("USD"));

    @Column(name = "creation_date")
    private Date creationDate;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

}
