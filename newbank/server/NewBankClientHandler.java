package newbank.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class NewBankClientHandler extends Thread{

	private NewBank bank;
	private BufferedReader in;
	private PrintWriter out;
	private HashMap<String, Integer> commands;
	private String[] command_examples = {"SHOWMYACCOUNTS", "NEWACCOUNT name", "MOVE 0.00 From To", "PAY Payee 0.00", "EXIT", "PRINTMENU"};


	public NewBankClientHandler(Socket s) throws IOException {
		bank = NewBank.getBank();
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
		commands = new HashMap<>();
		commands.put("SHOWMYACCOUNTS", 1);
		commands.put("NEWACCOUNT", 2);
		commands.put("MOVE", 4);
		commands.put("PAY", 3);
		commands.put("EXIT", 1);
		commands.put("PRINTMENU", 1);
	}

	public void run() {
		// keep getting requests from the client and processing them
		try {
			// ask for user name
			out.println("Enter Username");
			String userName = in.readLine();
			// ask for password
			out.println("Enter Password");
			String password = in.readLine();
			out.println("Checking Details...");
			// authenticate user and get customer ID token from bank for use in subsequent requests
			CustomerID customer = bank.checkLogInDetails(userName, password);
			// if the user is authenticated then get requests from the user and process them
			if(customer != null) {
				out.println("Log In Successful. What do you want to do?");
				while(true) {
					String request = in.readLine();
					String[] req = request.split(" ");
					String responce;
					if (commands.containsKey(req[0]) && commands.get(req[0]) == req.length) {
						System.out.println("Request from " + customer.getKey());
						responce = bank.processRequest(customer, request);
					} else {
						System.out.println("Invalid request from " + customer.getKey());
						responce = "Command was invalid.\nValid commands:";
						for (String s : command_examples) {
							responce += "\n" + s ;
						}
					}
					out.println(responce);
					if (responce.equals("DISCONNECTING")) {
						break;
					}
				}
			}
			else {
				out.println("Log In Failed");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}

}