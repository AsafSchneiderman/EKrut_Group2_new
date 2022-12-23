package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import db.mysqlConnection;
import entity.Subscriber;

public class ShowSubscriber {
	
	public static ArrayList<String> getSubscribers() {
		ArrayList<String> subscribers = new ArrayList<>();
		
		Statement stmt;
		String query = "";
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM subscribers");
				while (rs.next()) {
					subscribers.add(rs.getString("first_name") +","+rs.getString("last_name")+","+rs.getString("id") + ","+rs.getString("phone_number") +","+rs.getString("email") +  
							","+rs.getString("credit_card")+","+rs.getString("subscriber_num"));
					
					}
				rs.close();
			} else {
				System.out.println("Conn is null");
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return subscribers;
	}
}
