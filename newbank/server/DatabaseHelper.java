package newbank.server;
import java.sql.*;

public class DatabaseHelper
{
	public static Connection createConnection()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/NewBank","root","NewBank");
			return con;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return null;
	}
}
