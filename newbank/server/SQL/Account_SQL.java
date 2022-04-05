package newbank.server.SQL;

import java.sql.*;

import newbank.server.DatabaseHelper;

public class Account_SQL
{
	public static int insertAccount(int customerID, String accountName, double amount)
	{
		int accountID = -1;
		try
		{
			Connection con = DatabaseHelper.createConnection();
			String query = "insert into accounts " +
					   	   " ( " +
					   	   " 	CustomerID,  " +
					   	   "	AccountName, " +
					   	   "	Amount " +
					   	   " ) " +
					   	   " values " +
					   	   " ( " +
					   	   "	?, " +
					   	   "	?, " +
					   	   "	? " +
					   	   " ) ";
			PreparedStatement stm = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setInt(1, customerID);
			stm.setString(2, accountName);
			stm.setDouble(3, amount);
			int affectedRows = stm.executeUpdate();
			if(affectedRows == 0)
			{
				throw new SQLException("Creating account failed.");
			}
			ResultSet generatedKey = stm.getGeneratedKeys();
			if(generatedKey.next())
			{
				accountID = generatedKey.getInt(1);
			}
			else
			{
				throw new SQLException("Creating account failed.");				
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return accountID;
	}
}
