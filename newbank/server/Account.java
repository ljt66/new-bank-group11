package newbank.server;

public class Account {

	private int accountID;
	private String accountName;
	private double openingBalance;

	public Account(int accountID, String accountName, double openingBalance) {
		this.accountID = accountID;
		this.accountName = accountName;
		this.openingBalance = openingBalance;
	}
	
	public int getAccountID()
	{
		return this.accountID;
	}

	public String getName(){
		return accountName;
	}

	public double getBalance(){
		return openingBalance;
	}

	public void withdraw(double amount){
		this.openingBalance -= amount;
	}

	public void deposit(double amount){
		this.openingBalance += amount;
	}

	public String toString() {
		return (accountName + ": " + openingBalance);
	}
}