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
}
