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
			s += a.toString() + "\n";
		}
		return s;
	}

	public void addAccount(Account account) {
		accounts.add(account);
	}

	public boolean withdraw(double amount) {
		for (Account a : accounts) {
			if (amount <= a.getBalance()) {
				a.withdraw(amount);
				return true;
			}
		}
		System.out.println("Cannot withdraw " + amount+ " from any account.");
		return false;
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

	public void updateAccount(String name, double amount)
	{
		for (Account account : accounts) {
			if(account.getName().equals(name)){
				account.deposit(amount);
			}
		}
	}

	public boolean isValidAccount(String name){
		// Method to check a customer has valid accounts open
		boolean isValid = false;
		for (Account account : accounts) {
			if(account.getName().equals(name)){isValid = true;}
		}
		return isValid;
	}

	
	public double getAmount(String name) {
		double amt = 0.00;
		for (Account account : accounts) {
			if(account.getName().equals(name)){
				amt = account.getBalance();
			}
		}
		return amt;
	}
}