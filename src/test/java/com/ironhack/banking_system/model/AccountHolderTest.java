package com.ironhack.banking_system.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AccountHolderTest {

    private AccountHolder accountHolder1;
    private AccountHolder accountHolder2;
    private AccountHolder accountHolder3;


    @BeforeEach
    void setUp() {
        accountHolder1 = new AccountHolder(
                new Name("Marjorie", "Stewart-Baxter"),
                "MJB1972",
                "catlady7",
                LocalDate.of(1972, 04,01),
                new Address("c/ Alameda 46", "28012", "Madrid", "Spain")
        );
        accountHolder2 = new AccountHolder(
                        new Name("Reginald", "Dawes"),
                        "ReggieD",
                        "DrRegger",
                        //new Date(1998, 12,31),
                        LocalDate.of(1998, 12, 31),
                        new Address("c/ Atocha 216", "28039", "Madrid", "Spain")
                );
        accountHolder3 = new AccountHolder();
    }

    @Test
    void calculateAge_ValidData_Works() {
        assertEquals(50, accountHolder1.calculateAge());
        assertEquals(23, accountHolder2.calculateAge());
    }

    @Test
    void calculateAge_NullData_Throws() {
        assertThrows(NullPointerException.class, ()-> accountHolder3.calculateAge());
    }


}