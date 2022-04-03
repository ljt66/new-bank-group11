package newbank.server;
import java.time.*;

abstract class Account implements IFeesAndInterest{
    /**
         * This is the constructor for an account and this expects the following arguments:
         * @param accountName This is the name of the account holder as a String
         * @param accountType This is the type of account expected from the set enum AccountType. This can only be one of the following "checking", "savings" or "fixed deposit".
         * @param openingBalance This is the initial deposit amount.
         * @param accountOpenDate This is the date the account was created. LocalDate object expects the format "YYYY-MM-DD".
         */
   
    protected LocalDate accountOpenDate; 
    protected String accountName;
    protected double openingBalance;
  
    public static enum AccountType {
        current, savings, fixedDeposit
    };
    protected AccountType accountType;

    public Account(String accountName, AccountType accountType, double openingBalance, LocalDate accountOpenDate) {
        /**
         * This is the constructor for an account and this expects the following arguments:
         * @param accountName This is the name of the account holder as a String
         * @param accountType This is the type of account expected from the set enum AccountType. This can only be one of the following "checking", "savings" or "fixed deposit".
         * @param openingBalance This is the initial deposit amount.
         * @param accountOpenDate This is the date the account was created. LocalDate object expects the format "YYYY-MM-DD".
         */

        this.accountName = accountName;
        this.openingBalance = openingBalance;
        this.accountType = accountType;
        this.accountOpenDate = accountOpenDate;
    }

    public String getName() {
        return accountName;
    }

    public double getBalance() {
        return openingBalance;
    }

    public void withdraw(double amount) {
        this.openingBalance -= amount;
    }

    public void deposit(double amount) {
        this.openingBalance += amount;
    }

   

    public String toString() {
        return (accountName + ": " + openingBalance);
    }
}