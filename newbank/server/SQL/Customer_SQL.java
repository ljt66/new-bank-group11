package newbank.server.SQL;
import newbank.server.*;
import java.sql.*;

public class Customer_SQL
{
	public static int getCustomerID(String customerName)
	{
		int customerID = -1;
		try
		{
			Connection con = DatabaseHelper.createConnection();
			String query = "select c.CustomerID " +
					   " from customers c " +
					   " where c.customerName = ? ";
			PreparedStatement stm = con.prepareStatement(query);
			stm.setString(customerID, query);
			ResultSet rs = stm.executeQuery(query);
			if(rs.next())
			{
				customerID = rs.getInt(1);
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return customerID;
	}
	
	public static Customer getCustomer(String customerName)
	{
		Customer customer = null;
		try
		{
			Connection con = DatabaseHelper.createConnection();
			String query = "select c.CustomerID, " +
						   " a.AccountID, " +
						   " a.AccountName, " +
						   " a.Amount " +
					       " from customers c " +
						   " inner join accounts a on c.CustomerID = a.CustomerID " +
					       " where c.CustomerName = ? ";
			PreparedStatement stm = con.prepareStatement(query);
			stm.setString(1, customerName);
			ResultSet rs = stm.executeQuery();
			while(rs.next())
			{
				if(customer == null)
				{
					customer = new Customer(rs.getInt(1), customerName);
				}
				Account account = new Account(rs.getInt(2), rs.getString(3), rs.getDouble(4));
				customer.addAccount(account);
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return customer;
	}
}
