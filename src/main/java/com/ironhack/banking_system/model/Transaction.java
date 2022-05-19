package com.ironhack.banking_system.model;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@NoArgsConstructor
public class Transaction {

    //overloading transfer method for all types of account combos
    static void transfer(Savings originAccount, Savings destinationAccount, Money funds) {
        originAccount.debitAccount(funds);
        destinationAccount.creditAccount(funds);
    }
    static void transfer(Savings originAccount, Checking destinationAccount, Money funds) {
        originAccount.debitAccount(funds);
        destinationAccount.creditAccount(funds);
    }
    static void transfer(Savings originAccount, StudentChecking destinationAccount, Money funds) {
        originAccount.debitAccount(funds);
        destinationAccount.creditAccount(funds);
    }
    static void transfer(Savings originAccount, CreditCard destinationAccount, Money funds) {
        originAccount.debitAccount(funds);
        destinationAccount.creditAccount(funds);
    }
    static void transfer(Checking originAccount, Savings destinationAccount, Money funds) {
        originAccount.debitAccount(funds);
        destinationAccount.creditAccount(funds);
    }
    static void transfer(Checking originAccount, Checking destinationAccount, Money funds) {
        originAccount.debitAccount(funds);
        destinationAccount.creditAccount(funds);
    }
    static void transfer(Checking originAccount, StudentChecking destinationAccount, Money funds) {
        originAccount.debitAccount(funds);
        destinationAccount.creditAccount(funds);
    }
    static void transfer(Checking originAccount, CreditCard destinationAccount, Money funds) {
        originAccount.debitAccount(funds);
        destinationAccount.creditAccount(funds);
    }
    static void transfer(StudentChecking originAccount, Savings destinationAccount, Money funds) {
        originAccount.debitAccount(funds);
        destinationAccount.creditAccount(funds);
    }
    static void transfer(StudentChecking originAccount, Checking destinationAccount, Money funds) {
        originAccount.debitAccount(funds);
        destinationAccount.creditAccount(funds);
    }
    static void transfer(StudentChecking originAccount, StudentChecking destinationAccount, Money funds) {
        originAccount.debitAccount(funds);
        destinationAccount.creditAccount(funds);
    }
    static void transfer(StudentChecking originAccount, CreditCard destinationAccount, Money funds) {
        originAccount.debitAccount(funds);
        destinationAccount.creditAccount(funds);
    }
    static void transfer(CreditCard originAccount, Savings destinationAccount, Money funds) {
        originAccount.debitAccount(funds);
        destinationAccount.creditAccount(funds);
    }
    static void transfer(CreditCard originAccount, Checking destinationAccount, Money funds) {
        originAccount.debitAccount(funds);
        destinationAccount.creditAccount(funds);
    }
    static void transfer(CreditCard originAccount, StudentChecking destinationAccount, Money funds) {
        originAccount.debitAccount(funds);
        destinationAccount.creditAccount(funds);
    }
    static void transfer(CreditCard originAccount, CreditCard destinationAccount, Money funds) {
        originAccount.debitAccount(funds);
        destinationAccount.creditAccount(funds);
    }

    //overloading thirdParty credit transfer methods for all types of accounts
    static void thirdPartyCredit(ThirdParty thirdParty, Savings destinationAccount, Money funds) {
        destinationAccount.creditAccount(funds);
    }
    static void thirdPartyCredit(ThirdParty thirdParty, Checking destinationAccount, Money funds) {
        destinationAccount.creditAccount(funds);
    }
    static void thirdPartyCredit(ThirdParty thirdParty, StudentChecking destinationAccount, Money funds) {
        destinationAccount.creditAccount(funds);
    }
    static void thirdPartyCredit(ThirdParty thirdParty, CreditCard destinationAccount, Money funds) {
        destinationAccount.creditAccount(funds);
    }


    //overloading thirdParty debit transfer methods for all types of accounts
    static void thirdPartyDebit(ThirdParty thirdParty, Savings originAccount, Money funds) {
        originAccount.debitAccount(funds);
    }
    static void thirdPartyDebit(ThirdParty thirdParty, Checking originAccount, Money funds) {
        originAccount.debitAccount(funds);
    }
    static void thirdPartyDebit(ThirdParty thirdParty, StudentChecking originAccount, Money funds) {
        originAccount.debitAccount(funds);
    }
    static void thirdPartyDebit(ThirdParty thirdParty, CreditCard originAccount, Money funds) {
        originAccount.debitAccount(funds);
    }
}
