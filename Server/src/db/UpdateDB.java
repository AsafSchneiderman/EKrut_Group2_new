package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import db.mysqlConnection;
import entity.Subscriber;

public class UpdateDB {
	public static void UpdateSubscriberId(String subNum, String idSub) {
		PreparedStatement stmt;
		String query = "";
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.prepareStatement("UPDATE subscribers SET subscriber_num = ? where id = ?");
				stmt.setString(1, subNum);
				stmt.setString(2, idSub);
		 		stmt.executeUpdate();
			} else {
				System.out.println("Conn is null");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void UpdateSubscriberCreditCard(String creditCardNumber, String idSub ) {
		PreparedStatement stmt;
		String query = "";
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.prepareStatement("UPDATE subscribers SET credit_card = ?  where id = ?");
				//stmt = mysqlConnection.conn.prepareStatement(SELECT * FROM db_ekrut.subscribers);
				stmt.setString(1, creditCardNumber);
				stmt.setString(2, idSub);
		 		stmt.executeUpdate();

			} else {
				System.out.println("Conn is null");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
