package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import gui.ServerUI;


public class mysqlConnection {

	static Connection conn;
	public static void connectionDb(String password) 
	{
		try 
		{
            Class.forName("com.mysql.cj.jdbc.Driver");
            ServerUI.serverGUI.appendToConsole("Driver definition succeed");
        } catch (Exception ex) {
        	/* handle the error*/
        	ServerUI.serverGUI.appendToConsole("Driver definition failed");
        	 }
        
        try 
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/db_ekrut?serverTimezone=IST","root", password);
            ServerUI.serverGUI.appendToConsole("SQL connection succeed");
       
            
     	} catch (SQLException ex) 
     	    {/* handle any errors*/
     		ServerUI.serverGUI.appendToConsole("SQLException: " + ex.getMessage());
     		ServerUI.serverGUI.appendToConsole("SQLState: " + ex.getSQLState());
     		ServerUI.serverGUI.appendToConsole("VendorError: " + ex.getErrorCode());
            }
   	}

}


