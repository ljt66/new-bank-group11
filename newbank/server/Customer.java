package newbank.server;

import java.util.ArrayList;

public class Customer {
	
	private ArrayList<Account> accounts;
	
	public Customer() {
		accounts = new ArrayList<>();
	}
	
	public String accountsToString() {
		String s = "";
		for(Account a : accounts) {
			s += a.toString();
		}
		return s;
	}

	public void addAccount(Account account) {
		accounts.add(account);		
	}

	public void withdraw(double amount) {
		for (Account a : accounts) {
			if (amount <= a.getBalance()) {
				a.withdraw(amount);
				return;
			}
		}
		System.out.println("Cannot withdraw " + amount+ " from any account.");
	}

	public void deposit(double amount) {
		accounts.get(0).deposit(amount);
		return;
	}

	public boolean isNewAccountNameValid(String name){
		//Method to check whether a customer already has an account name matching a name intended to be used to create a new account
		boolean isValid = true;
		for (Account account : accounts) {
			if(account.getName().equals(name)){isValid = false;}
		}
		return isValid;
	}
}
