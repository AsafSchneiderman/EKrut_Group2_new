package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import Entities.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Query {

	/**
	 * 
	 * @return
	 */
	public static ArrayList<UsersToRegister> getUsersToRegister() {
		UsersToRegister usersToRegister;
		ArrayList<UsersToRegister> listOfUsersToRegister = new ArrayList<>();
		Statement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM userstosignup");
				while (rs.next()) {

					usersToRegister = new UsersToRegister(rs.getString("id"), rs.getString("firstName"),
							rs.getString("lastName"), rs.getString("email"), rs.getString("phone"));
					listOfUsersToRegister.add(usersToRegister);
				}
				rs.close();
			} else {
				System.out.println("Conn is null");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listOfUsersToRegister;
	}

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
		String userID = null;
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
					userID = rs.getString(1);
					result.append(rs.getString(1)); // userID
					result.append("#");
					result.append(rs.getString(2)); // id
					result.append("#");
					result.append(rs.getString(3)); // firstName
					result.append("#");
					result.append(rs.getString(4)); // lastName
					result.append("#");
					result.append(rs.getString(5)); // userName
					result.append("#");
					result.append(rs.getString(6)); // password
					result.append("#");
					result.append(rs.getString(7)); // role
					result.append("#");
					result.append(rs.getString(8)); // email
					result.append("#");
					result.append(rs.getString(9)); // phoneNumber
					result.append("#");
					result.append(rs.getString(10)); // isLoggedIn
				}
				rs.close();
				// empty result
				if (result.length() == 0)
					return "Wrong_Input";
				// Update isLoggedIn='1' to the user in DB
				stmt = mysqlConnection.conn.prepareStatement("UPDATE users SET isLoggedIn='1' where userID=?");
				stmt.setString(1, userID);
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
		ArrayList<VendingMachine> vendingMachines = new ArrayList<>();

		Statement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM vendingmachines");
				while (rs.next()) {
					VendingMachine v = new VendingMachine(rs.getString("region"), rs.getString("location"),
							rs.getString("thresholdLevel"), rs.getString("restockStatus"));
					vendingMachines.add(v);
				}
				rs.close();
			} else {
				System.out.println("Conn is null");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return vendingMachines;
	}

	/**
	 * update the threshold Level of the vending machine in location in the DB.
	 * 
	 * @param vendingmachines list to update them threshold level
	 */
	public static void UpdateVendingMachineThresholdLevel(ArrayList<VendingMachine> vendingMachines) {
		PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {
				for (VendingMachine row : vendingMachines) {
					stmt = mysqlConnection.conn
							.prepareStatement("UPDATE vendingmachines SET thresholdLevel = ? where location = ?");
					stmt.setString(1, row.getThresholdLevel());
					stmt.setString(2, row.getLocation());
					stmt.executeUpdate();
				}
			} else {
				System.out.println("Conn is null");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * update the Re-stock Status of the vending machine in location in the DB.
	 * 
	 * @param vendingmachines list to update them threshold level
	 */
	public static void UpdateVendingMachineRestockStatus(ArrayList<VendingMachine> vendingMachines) {
		PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {
				for (VendingMachine row : vendingMachines) {
					stmt = mysqlConnection.conn
							.prepareStatement("UPDATE vendingmachines SET restockStatus = ? where location = ?");
					stmt.setString(1, row.getRestockStatus());
					stmt.setString(2, row.getLocation());
					stmt.executeUpdate();
				}
			} else {
				System.out.println("Conn is null");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get the reports data from DB
	 * 
	 * @return ArrayList of reports from the DB
	 */
	public static ArrayList<Report> getReports(String input) {
		String[] inputArray = input.split("\\#");
		ReportType reportType;
		String month = inputArray[1];
		String year = inputArray[2];
		String region = inputArray[3];
		ArrayList<Report> reports = new ArrayList<>();
		Statement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs;
				if (inputArray[0].equals("Show all report types"))
					rs = stmt.executeQuery("SELECT * FROM reports");
				else 
					rs = stmt.executeQuery("SELECT * FROM reports WHERE report_type = \""+ inputArray[0] + "\"");
							
				ArrayList<VendingMachine> vendingMachines = getVendingMachines();
				
				while (rs.next()) {
					Report r = null;
					reportType = ReportType.valueOf(rs.getString("report_type"));
					switch(reportType) {
					case Order:
						ArrayList<Order> orders = getOrdersByDateAndRegion(month, year, region);
						r = new OrdersReport(month, year, orders);
						break;
					case Stock_Status:
						ArrayList<ArrayList<Product>> stocks = new ArrayList<>();
						ArrayList<VendingMachine> vendingMachinesInRegion = new ArrayList<>();
						for (VendingMachine v : vendingMachines) {
							if (v.getRegion().equals(region)) {
								String currentMachine = v.getLocation().toLowerCase();
								ArrayList<Product> productsStock = getProductStockByDateAndMachine(month, year, currentMachine);
								vendingMachinesInRegion.add(v);
								stocks.add(productsStock);
							}
						}
						r = new StockStatusReport(month, year, vendingMachinesInRegion, stocks);
						break;
					case Client_Activity:
						ArrayList<HashMap<Integer,Integer>> clientsActivityPerMachine=new ArrayList<>();
						
						for (VendingMachine v : vendingMachines)
						{
							if(v.getRegion().equals(region))
							{
								HashMap<Integer, Integer> clientsPerOrderAmount = getClientActivityByDateAndMachine(month, year, v);
								clientsActivityPerMachine.add(clientsPerOrderAmount);
							}
						}
						r = new ClientActivityReport(month, year, clientsActivityPerMachine);
						break;
						
					default:
						break;
					}
					reports.add(r);
				}
				rs.close();	
			} 
			else System.out.println("Conn is null");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reports;
	}

	/**
	 * @param month month of order
	 * @param year year of order
	 * @param region of order
	 * @return ArrayList of orders
	 */
	private static ArrayList<Order> getOrdersByDateAndRegion(String month, String year, String region){
		ArrayList<Order> orders = new ArrayList<>();
		try {
			Statement stmt = mysqlConnection.conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT orders.*, vendingmachines.region from orders"
					+ " inner join vendingmachines on orders.machineLocation=vendingmachines.location"
					+ " WHERE MONTH(orderDate)=" + month + " AND YEAR(orderDate)=" + year + " AND region=\"" + region + "\"");
			while (rs.next()) {
	
				Order o = new Order(rs.getString("machineLocation"), rs.getString("date"),
						rs.getString("status"), rs.getString("customerID"), rs.getFloat("totPrice"),
						rs.getString("type"), rs.getInt("productsQauntity"));
				orders.add(o);
	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	/**
	 * 
	 * @param month month of stock status
	 * @param year year of stock status
	 * @param currentMachine the machine associated with the stock status
	 * @return
	 */
	private static ArrayList<Product> getProductStockByDateAndMachine(String month, String year, String currentMachine) {
		ArrayList<Product> productsStock = new ArrayList<>();
		try {
			Statement stmt = mysqlConnection.conn.createStatement();
			String currentStock = currentMachine + "products";
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + currentStock);	
			ResultSet rs2 = stmt.executeQuery("SELECT * FROM db_ekrut.orders WHERE (orderDate BETWEEN \' "
							+ year + "-" + month + "-01\' AND CURDATE() ) AND ( machineLocation = " + currentMachine + ")");
			while (rs.next()) {
				String[] productsIDs = rs2.getString("products").split("\\,");
				String[] productsQuantities = rs2.getString("QuantityPerProduct").split("\\,");
				int[] removedStocks = new int[30];
				for (int i = 0; i < 30; i++)
					removedStocks[i] = 0;
	
				for (int i = 0; i < rs2.getInt("productsQuantity"); i++) {
					int productID = Integer.parseInt(productsIDs[i]);
					int productQuantity = Integer.parseInt(productsQuantities[i]);
					removedStocks[productID] = removedStocks[productID] + productQuantity;
	
				}
				int ID = Integer.parseInt(rs.getString("productID"));
				int quantity = rs.getInt("stockQuantity") + removedStocks[ID];
				Product product = new Product(rs.getString("productName"),
						rs.getString("productID"), rs.getString("price"), quantity + "", null);
				productsStock.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productsStock;
	}

	
	/**
	 * 
	 * @param month month of checked client activity
	 * @param year year of checked client activity
	 * @param v the vending machine of the client activity
	 * @return
	 */
	private static HashMap<Integer, Integer> getClientActivityByDateAndMachine(String month, String year, VendingMachine v) {
		HashMap<Integer,Integer> clientsPerOrderAmount= new HashMap<>();
		try {
			Statement stmt = mysqlConnection.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT OrdersPerClient.OrderAmount, COUNT(*) AS ClientAmount FROM (SELECT customerID, COUNT(*) AS OrderAmount FROM orders"
					+" inner join vendingmachines on orders.machineLocation=vendingmachines.location"
					+" WHERE MONTH(orderDate)= "+ month +" AND YEAR(orderDate)= "+year+" AND machineLocation= \""+v.getLocation()+"\""
					+" GROUP BY customerID) AS OrdersPerClient"
					+" GROUP BY OrdersPerClient.OrderAmount"
					+" ORDER BY OrdersPerClient.OrderAmount");
			while(rs.next())
				clientsPerOrderAmount.put(rs.getInt("OrderAmount"), rs.getInt("ClientAmount"));
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return clientsPerOrderAmount;
	}

	public static ArrayList<Product> getProducts() {
		Product product;
		ArrayList<Product> listOfProducts = new ArrayList<>();
		Statement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM ortBraudeproducts");
				while (rs.next()) {

					product = new Product(rs.getString("productID"), rs.getString("productName"), rs.getString("price"),
							rs.getString("stockQuantity"), rs.getString("imgSrc"));
					listOfProducts.add(product);
				}
				rs.close();
			} else {
				System.out.println("Conn is null");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listOfProducts;
	}

	/**
	 * get region name by the manager user id
	 * 
	 * @param userID
	 * @return region name
	 */
	public static String getRegion(String userID) {
		PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.prepareStatement("SELECT region FROM regionmanager WHERE userID = ?");
				stmt.setString(1, userID);
				ResultSet rs = stmt.executeQuery();
				if (rs.next())
					return rs.getString("region");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "noRegion";
	}

	
}
