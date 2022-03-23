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

	public void addCustomer(String name) {
		Customer customer = new Customer();
		customers.put(name, customer);
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
		String[] req = request.split(" ");
		if(customers.containsKey(customer.getKey())) {
			switch(req[0]) {
			case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
			case "NEWACCOUNT":	return newAccount(customer, req[1]);
			case "PAY": return payAccount(customer, req);
			case "EXIT" : return "DISCONNECTING";
			default : return "FAIL";
			}
		}
		return "FAIL";
	}
	
	//pays ammount requested from customer's first account with the correct balance
	//to payee's first account
	private String payAccount(CustomerID customer, String[] request) {
		double amount = Double.parseDouble(request[2]);
		Customer c = customers.get(customer.getKey());
		//potential to ask customer using accountsToString method, what account they wish to pay from
		if (customers.containsKey(request[1])) {
			Customer payee = customers.get(request[1]);
			if (c.withdraw(amount)) {
				payee.deposit(amount);
				return "SUCCESS";
			};
		}
		return "FAIL";
	}

	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}

	private String newAccount(CustomerID customer, String name) {
		Customer c = customers.get(customer.getKey());
		if (c.isNewAccountNameValid(name)) {
			c.addAccount(new Account(name, 0));
			return "SUCCESS";
		} else {
			return "FAIL";
		}
	}

}
