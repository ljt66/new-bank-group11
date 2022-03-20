package newbank.server;

public class Account {
	
	private String accountName;
	private double openingBalance;

	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
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
