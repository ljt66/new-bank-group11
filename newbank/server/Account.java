package newbank.server;
import java.time.*;

public class Account{
    /**
         * This is the constructor for an account and this expects the following arguments:
         * @param accountName This is the name of the account holder as a String
         * @param accountType This is the type of account expected from the set enum AccountType. This can only be one of the following "checking", "savings" or "fixed deposit".
         * @param openingBalance This is the initial deposit amount.
         * @param accountOpenDate This is the date the account was created. LocalDate object expects the format "YYYY-MM-DD".
         */
   protected int accountID;
    protected LocalDate accountOpenDate; 
    protected String accountName;
    protected double openingBalance;
  
    public static enum AccountType {
        current, savings, fixedDeposit
    };
    protected AccountType accountType;


    public Account(int accountID, String accountName, double openingBalance) {
        /**
         * This is the constructor for an account and this expects the following arguments:
         * @param accountName This is the name of the account holder as a String
         * @param accountType This is the type of account expected from the set enum AccountType. This can only be one of the following "checking", "savings" or "fixed deposit".
         * @param openingBalance This is the initial deposit amount.
         * @param accountOpenDate This is the date the account was created. LocalDate object expects the format "YYYY-MM-DD".
         */
        this.accountID = accountID;
        this.accountName = accountName;
        this.openingBalance = openingBalance;
        this.accountOpenDate = LocalDate.now();
    }

    public Account(int accountID,  String accountName, AccountType accountType ,double openingBalance) {
        /**
         * This is the constructor for an account and this expects the following arguments:
         * @param accountName This is the name of the account holder as a String
         * @param accountType This is the type of account expected from the set enum AccountType. This can only be one of the following "checking", "savings" or "fixed deposit".
         * @param openingBalance This is the initial deposit amount.
         * @param accountOpenDate This is the date the account was created. LocalDate object expects the format "YYYY-MM-DD".
         */
        this.accountID = accountID;
        this.accountName = accountName;
        this.openingBalance = openingBalance;
        this.accountType = accountType;
        this.accountOpenDate = LocalDate.now();
    }
    public int getAccountID()
	{
		return this.accountID;
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