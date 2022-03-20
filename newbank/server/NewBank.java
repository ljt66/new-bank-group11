package newbank.server;

import java.util.HashMap;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;
	
	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}
	
	private void addTestData() {
		Customer bhagy = new Customer();
		bhagy.addAccount(new Account("Main", 1000.0));
		customers.put("Bhagy", bhagy);
		
		Customer christina = new Customer();
		christina.addAccount(new Account("Savings", 1500.0));
		customers.put("Christina", christina);
		
		Customer john = new Customer();
		john.addAccount(new Account("Checking", 250.0));
		customers.put("John", john);
	}
	
	public static NewBank getBank() {
		return bank;
	}
	
	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		if(customers.containsKey(userName)) {
			return new CustomerID(userName);
		}
		return null;
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request) {
		if(customers.containsKey(customer.getKey())) {
			switch(request) {
			case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
			case "NEWACCOUNT":	 newAccount(customer, request);
			case "MOVE": move(customer, amount, fromAccountName, toAccountName);
			case "EXIT" : return "DISCONNECTING";
			default : return "FAIL";
			}
		}
		return "FAIL";
	}
	
	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}

	private void newAccount(CustomerID customer, String name) {
		Customer c = customers.get(customer.getKey());
		if (c.isNewAccountNameValid(name)) {
			c.addAccount(new Account(name, 0));
			System.out.println("SUCCESS");
		} else {
			System.out.println("FAIL");
		}
	}

	private void move (CustomerID customer, double amount, String fromAccountName, String toAccountName){
		Customer c = customers.get(customer.getKey());
		if (c.isValidAccount(fromAccountName) && c.isValidAccount(toAccountName)){
			if (fromAccountName >= toAccountName){
				c.updateAccount(toAccountName, amount);
				c.updateAccount(fromAccountName, -amount);
				System.out.println("SUCCESS");
			}
			else{
				System.out.println("FAIL");
			}
		}
		else{
			System.out.println("FAIL");
		}

	}

}
