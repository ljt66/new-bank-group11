package newbank.server;

import java.util.HashMap;
import java.time.*;


import newbank.server.SQL.Account_SQL;
import newbank.server.SQL.Customer_SQL;

public class NewBank {

	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;

	public static final String adminUsername = "ADMIN";
	public static final String adminPassword = "password";

	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}

	private void addTestData() {

		Customer bhagy = new Customer();
		bhagy.addAccount(new Account(1, "Main", 1000.0));
		customers.put("Bhagy", bhagy);

		Customer christina = new Customer();
		christina.addAccount(new Account(2, "Savings", 1500.0));
		customers.put("Christina", christina);

		Customer john = new Customer();
		john.addAccount(new Account(3, "Checking", 250.0));

	}

	public CustomerID addCustomer(String name) {
		if(customers.containsKey(name)) {
			return null;
		}
		else {
			Customer customer = new Customer();
			customers.put(name, customer);
			return new CustomerID(name);
		}
	}

	public String registerNewCustomer(String name, String password, String account) {
		CustomerID customer = addCustomer(name);
		if (customer == null) {
			return "FAIL : CANNOT ADD CUSTOMER";
		}
		else {
			return newAccount(customer, name);
		}
	}

	public String listCustomers() {
		return String.join(" ", customers.keySet());
	}

	public static NewBank getBank() {
		return bank;
	}

	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		if(userName.equals(NewBank.adminUsername) && password.equals(NewBank.adminPassword)){
			return new CustomerID(adminUsername, true);
		}

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
				case "MOVE": return move(customer, req[1], req[2], req[3]);
				case "PAY": return payAccount(customer, req);
				case "PRINTMENU": return printMenu();
				case "EXIT" : return "DISCONNECTING";
				default : return "FAIL";
			}
		}
		return "FAIL";
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processAdminRequest(CustomerID customer, String request) {
		String[] req = request.split(" ");
		if(customer.isAdmin()) {
			switch(req[0]) {
				case "NEWCUSTOMER" : return registerNewCustomer(req[1], req[2], req[3]);
				case "SHOWCUSTOMERS" : return listCustomers();
				case "PRINTMENU" : return printAdminMenu();
				case "EXIT" : return "DISCONNECTING";
				default : return "FAIL : INVALID COMMAND";
			}
		}
		return "FAIL : NOT AN ADMIN";
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
		Customer c = Customer_SQL.getCustomer(customer.getKey());
		if (c.isNewAccountNameValid(name)) {
			int accountID = Account_SQL.insertAccount(c.getCustomerID(), name, 0);
			if(accountID != -1)
			{
				c.addAccount(new Account(accountID, name, 0));
				return "SUCCESS";
			}

		}
			return "FAIL";
	}

	private String move (CustomerID customer, String moveAmount, String fromAccountName, String toAccountName){
		Customer c = customers.get(customer.getKey());
		double amount = Double.parseDouble(moveAmount);
		if (c.isValidAccount(fromAccountName) && c.isValidAccount(toAccountName)){
			if (c.getAmount(toAccountName) >= c.getAmount(toAccountName)){
				c.updateAccount(toAccountName, amount);
				c.updateAccount(fromAccountName, -amount);
				return "SUCCESS";
			}
			else{
				return "FAIL";
			}
		}
		else{
			return "FAIL";
		}
	}

	private String printMenu(){
		String menu = "List of available commands:"
				+ "\nSHOWMYACCOUNTS: display customer's accounts."
				+ "\nNEWACCOUNT: add a new account to existing customer's accounts."
				+ "\nMOVE: move requested amount between customer's accounts."
				+ "\nPAY: pay requested amount to another customer's account."
				+ "\nEXIT: log off from customer's account."
				+ "\nPRINTMENU: review commands again.";
		return menu;
	}

	private String printAdminMenu(){
		String menu = "List of available commands:"
				+ "\nEXIT: log off from admin interface."
				+ "\nPRINTMENU: review commands again.";
		return menu;
	}

}