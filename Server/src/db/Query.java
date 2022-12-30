package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Entities.*;

public class Query {

	/**
	 * @param userName of the client
	 * @param password of the client
	 * @return: Checks if the user exists in the DB: *if true - then it checks if
	 *          the user is logged in: **if true - then returns an error message.
	 *          **If false - update isLoggedIn to 1 in DB and returns the user's
	 *          details. *If not, then it returns an error message.
	 */
	public static String login(String userName, String password) {
		StringBuilder result = new StringBuilder();
		String id = null;
		PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.prepareStatement("SELECT * FROM users WHERE userName = ? AND password = ?");
				stmt.setString(1, userName);
				stmt.setString(2, password);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					// the user exists but already logged in
					if ((rs.getString("isLoggedIn")).equals("1")) {
						return "Already_logged_in";
					}
					// get user details
					id = rs.getString(1);
					result.append(rs.getString(1)); // id
					result.append("#");
					result.append(rs.getString(2)); // firstName
					result.append("#");
					result.append(rs.getString(3)); // lastName
					result.append("#");
					result.append(rs.getString(4)); // userName
					result.append("#");
					result.append(rs.getString(5)); // password
					result.append("#");
					result.append(rs.getString(6)); // role
					result.append("#");
					result.append(rs.getString(7)); // email
					result.append("#");
					result.append(rs.getString(8)); // phoneNumber
					result.append("#");
					result.append(rs.getString(9)); // isLoggedIn
				}

				rs.close();
				// empty result
				if (result.length() == 0)
					return "Wrong_Input";
				// Update isLoggedIn='1' to the user in DB
				stmt = mysqlConnection.conn.prepareStatement("UPDATE users SET isLoggedIn='1' where id=?");
				stmt.setString(1, id);
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	/**
	 * logout the user and update in the DB
	 * 
	 * @param userName of the user
	 */
	public static void logout(String userName) {
		PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {

				stmt = mysqlConnection.conn.prepareStatement("UPDATE users SET isLoggedIn='0' where userName=?");

				stmt.setString(1, userName);
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get the vending machines data from DB
	 * 
	 * @return ArrayList of vending machines from the DB
	 */
	public static ArrayList<VendingMachine> getVendingMachines() {
		ArrayList<VendingMachine> vendingmachines = new ArrayList<>();

		Statement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM vendingmachines");
				while (rs.next()) {
					VendingMachine v = new VendingMachine(rs.getString("region"), rs.getString("location"), rs.getString("thresholdLevel"));
					vendingmachines.add(v);
				}
				rs.close();
			} else {
				System.out.println("Conn is null");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return vendingmachines;
	}

	/**
	 * update the threshold Level of the vending machine in location in the DB.
	 * 
	 * @param thresholdLevel to update
	 * @param location of the vending machine
	 */
	public static void UpdateVendingMachineThresholdLevel(String thresholdLevel, String location) {
		PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn
						.prepareStatement("UPDATE vendingmachines SET thresholdLevel = ? where location = ?");
				stmt.setString(1, thresholdLevel);
				stmt.setString(2, location);
				stmt.executeUpdate();
			} else {
				System.out.println("Conn is null");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
