# Elizabeth-Midterm-Banking-System
Banking System Rest API

## Description
This is a Rest API for a banking system in which different types of users, accounts and transfers can be created and managed.


### Users
Users can be created as Admin or Account Holder, with a name, unique username, and password, with the appropriate role attached.
Additionaly, Account Holders are created with a date of birth, primary address, and optional mailing address.

Admins can create both types of users, as well as create a Third Party. Third Parties have a name and a hashed key.
Account Holders can access only their own account balance. They can also transfer money from any of their accounts to any other account
within the bank, using the other account id and one of the account owners names .
Third Parties can transfer money to or from a bank account using their hashed key, the account id, and the account secret key.


### Accounts
An Admin can choose from three types when creating an new account: Checking, Savings, or Credit Card.
All accounts are created with a balance, secret key, primary owner, and an optional secondary owner.

Checking accounts also have a minimum balance of 250 and a monthly maintenance fee of 12.
If a Checking Account is created for a primary owner who is less than 24, a Student Checking account will automatically be created,
with no minimum balance or monthly maintenance fee.

Savings accounts have a default interest rate of 0.0025 applied annually, but can be created with a custom interest rate of 0.5 or less.
The default minimum balance for Savings accounts is 1000, but can be created with a custom minimum balance between 100 and 1000.

Credit Card accounts have a default credit limit of 100, which can be customized between 100 and 100000. The default annual interest rate
is 0.2 that is applied monthly, and can be customized to be between 0.1 and 0.2.

Any account that has a minimum balance has a penalty fee of 40 which is applied if the account drops below the minimum balance.


## Setup
The two Roles of ROLE_ADMIN and ROLE_USER are included in the CommandLineRunner so that these roles can be added to the users automatically
as they are created.

The Money class currently has the default currency of EUROS, but can be changed.


## Technologies Used
Java/SpringBoot Rest API
Spring Security
MySQL database


## Models

Class Diagram:

![Screen Shot 2022-05-22 at 10 30 42 PM](https://user-images.githubusercontent.com/88110591/169714645-ee7c8b6a-6c68-4352-8eb7-0fdfc1de9a8b.png)

database schema

![Screen Shot 2022-05-22 at 10 33 03 PM](https://user-images.githubusercontent.com/88110591/169714707-8efd58ba-18dd-4d6e-9205-0e33500244c3.png)


## Server routes table
![Screen Shot 2022-05-22 at 9 48 38 PM](https://user-images.githubusercontent.com/88110591/169713255-0612d957-9953-4bb0-9000-1a51232ad491.png)


## Future Work
In the future, I would like to add a Fraud Detection feature.


## Resources
Http status codes: https://www.restapitutorial.com/httpstatuscodes.html

Integration testing: https://www.baeldung.com/integration-testing-in-spring

Ironhack Opencamp instructors
