package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class InsertSubscriberDB {
	public static void SaveDB(ArrayList<String> list){
		try {
			
			PreparedStatement ps = mysqlConnection.conn.prepareStatement("INSERT INTO subscribers values(?, ?, ?, ?, ?, ?, ?)"); // setting input to DB

			//client data - Arranged like in Assignment 2  
			ps.setString(1, list.get(0)); //first name
			ps.setString(2, list.get(1)); //last name
			ps.setString(3, list.get(2)); //id
			ps.setString(4, list.get(3)); //phone number
			ps.setString(5, list.get(4)); //email address
			ps.setString(6, list.get(5)); //credit card number
			ps.setString(7, list.get(6)); //subscriber number
			
			ps.executeUpdate();
			
		} catch (SQLException e) {	e.printStackTrace();}
		 		
	}
	
}
