package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Entities.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	 * update the Restock Status of the vending machine in location in the DB.
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
	 * get the reports data from DB
	 * 
	 * @return ArrayList of reports from the DB
	 */
	public static ArrayList<Report> getReports() {
		ArrayList<Report> reports = new ArrayList<>();

		Statement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM reports");
				while (rs.next()) {
					Report r = null;
					if (rs.getString("report_type") == "order") {
						ArrayList<Order> orders = new ArrayList<>();

						ResultSet rs2 = stmt.executeQuery("SELECT * FROM db_ekrut.orders WHERE (orderDate BETWEEN \'"
								+ rs.getString("year") + "-" + rs.getString("month") + "-01\' AND CURDATE() )");

						while (rs2.next()) {

							Order o = new Order(rs2.getString("machineLocation"), rs2.getString("date"),
									rs2.getString("status"), rs2.getString("customerID"), rs2.getFloat("totPrice"),
									rs2.getString("type"), rs2.getInt("productsQauntity"));
							orders.add(o);

						}
						r = new OrdersReport(rs.getString("month"), rs.getString("year"), orders);
					} else if (rs.getString("report_type") == "stockStatus") {

						ArrayList<ArrayList<Product>> stocks = new ArrayList<>();
						ArrayList<VendingMachine> vendingMachines = getVendingMachines();
						for (VendingMachine v : vendingMachines) {
							String currentMachine=v.getLocation().toLowerCase();
							String currentStock = currentMachine + "products";
							ResultSet rs2 = stmt.executeQuery("SELECT * FROM " + currentStock);
							ArrayList<Product> productsStock = new ArrayList<>();
							
							ResultSet rs3 = stmt.executeQuery("SELECT * FROM db_ekrut.orders WHERE (orderDate BETWEEN \' "+ rs.getString("year") + "-" + rs.getString("month") +"-01\' AND CURDATE() ) AND ( machineLocation = "+currentMachine+")");
							while (rs2.next()) {
								String[] productsIDs = rs3.getString("products").split("\\,");
								String[] productsQuantities = rs3.getString("QuantityPerProduct").split("\\,");
								int[] removedStocks = new int[30];
								for(int i=0;i<30;i++)
									removedStocks[i]=0;
							
								for(int i=0;i<rs3.getInt("productsQuantity");i++)
								{
									int productID= Integer.parseInt(productsIDs[i]);
									int productQuantity= Integer.parseInt(productsQuantities[i]);
									removedStocks[productID]=removedStocks[productID]+productQuantity;
									
								}
								int ID= Integer.parseInt(rs2.getString("productID"));
								int quantity= rs2.getInt("stockQuantity")+ removedStocks[ID];
								Product product = new Product(rs2.getString("productName"), rs2.getString("productID"), rs2.getFloat("price"), quantity, null);
								productsStock.add(product);
							}
							
							stocks.add(productsStock);
						}
						r = new StockStatusReport(rs.getString("month"), rs.getString("year"), vendingMachines, stocks);
					} else if (rs.getString("report_type") == "clientActivity") {
						// TODO (add clientActivityReport table to db_ekrut)
						r = new ClientActivityReport(rs.getString("month"), rs.getString("year"));
					}
					reports.add(r);
				}
				rs.close();
			} else {
				System.out.println("Conn is null");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reports;
	}
	
	public static ArrayList<Product> getProducts(String location) {
		Product product;
		ArrayList<Product>listOfProducts = new ArrayList<>();
		Statement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM" + location.toLowerCase()+ "products");
				while (rs.next()) {
					Image pic = new Image(rs.getString("imgSrc"));
					 ImageView img = new ImageView();
					img.setImage(pic);
					
					product = new Product(rs.getString("productID") ,rs.getString("productName"),rs.getString("price") ,rs.getString("stockQuantity") ,img);
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
	 * @param userID
	 * @return region name
	 */
	public static String getRegion(String userID) {
		String regionName = null;
		Statement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT region FROM regionmanager WHERE userID ="+userID);
				regionName = rs.getString("region");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return regionName;
	}

	/**
	 * get vending machine locations by name of region
	 * @param regionName the name of the region
	 * @return vending machine locations
	 */
	public static ArrayList<String> getLocations(String regionName) {
		ArrayList<String> locations = new ArrayList<>();
		Statement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT location FROM vendingmachines WHERE region =\""+regionName+"\"");
				while(rs.next()) {
					locations.add(rs.getString("location"));
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return locations;
	}
}
