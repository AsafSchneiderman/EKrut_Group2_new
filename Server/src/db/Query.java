package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Entities.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.text.SimpleDateFormat;  
import java.util.Date;  

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
					//result.append("#");
					//result.append(rs.getString(11)); //region
					//result.append("#");
					//result.append(rs.getString(12)); //creditCard
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
	 * @param vendingMachine to update his Restock Status
	 */
	public static void UpdateVendingMachineRestockStatus(VendingMachine vendingMachine) {
		PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {
				
					stmt = mysqlConnection.conn
							.prepareStatement("UPDATE vendingmachines SET restockStatus = ? where location = ? and restockStatus = ?");
					
					stmt.setString(1, vendingMachine.getRestockStatus());
					stmt.setString(2, vendingMachine.getLocation());
					stmt.setString(3, "LowStock");
					stmt.executeUpdate();
			} else {
				System.out.println("Conn is null");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param input contains a format of "type#month#year#region" 
	 * @return ArrayList of reports from the DB based on the input
	 */
	public static ArrayList<Report> getReports(String input) {
		String[] inputArray = input.split("\\#");
		String type = inputArray[0];
		String month = inputArray[1];
		String year = inputArray[2];
		String region = inputArray[3];
		ArrayList<VendingMachine> vendingMachines = getVendingMachines();
		
		List<String> reportTypes;
		if (type.equals("Show all report types"))
			reportTypes = Arrays.asList("Order", "Stock_Status", "Client_Activity");
		else reportTypes = Arrays.asList(type);
		
		List<String> months;
		if (month.equals("All months"))
			months = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
		else months = Arrays.asList(month);
							
		List<String> years;
		if (year.equals("All years"))
			years = Arrays.asList("2022", "2023");
		else years = Arrays.asList(year);
		
		List<String> regions;
		if (region.equals("All")) {
			regions = new ArrayList<>();
			for (VendingMachine v : vendingMachines)
				if (!regions.contains(v.getRegion()))
					regions.add(v.getRegion());
		}
		else regions = Arrays.asList(region);

		ArrayList<Report> reports = new ArrayList<>();
		
		for (String currentType : reportTypes) {
			for (String currentRegion : regions) {
				for (String currentYear : years) {
					for (String currentMonth : months) {
						Report report = null;
						ReportType reportType = ReportType.valueOf(currentType);
						switch(reportType) {
						case Order:
							ArrayList<Order> orders = getOrdersByDateAndRegion(currentMonth, currentYear, currentRegion);
							report = new OrdersReport(currentMonth, currentYear,currentRegion, orders);
							break;
						case Stock_Status:
							ArrayList<ArrayList<Product>> stocks = new ArrayList<>();
							ArrayList<VendingMachine> vendingMachinesInRegion = new ArrayList<>();
							for (VendingMachine v : vendingMachines) {
								if (v.getRegion().equals(currentRegion)) {
									ArrayList<Product> productsStock = getProductStockByDateAndMachine(currentMonth, currentYear, v.getLocation().toLowerCase());
									if (null != productsStock) {
										vendingMachinesInRegion.add(v);
										stocks.add(productsStock);
									}
								}
							}
							report = new StockStatusReport(currentMonth, currentYear,currentRegion, vendingMachinesInRegion, stocks);
							break;
						case Client_Activity:
							ArrayList<HashMap<Integer,Integer>> clientsActivityPerMachine = new ArrayList<>();
							for (VendingMachine v : vendingMachines) {
								if(v.getRegion().equals(currentRegion)) {
									HashMap<Integer, Integer> clientsPerOrderAmount = getClientActivityByDateAndMachine(currentMonth, currentYear, v);
									clientsActivityPerMachine.add(clientsPerOrderAmount);
								}
							}
							report = new ClientActivityReport(currentMonth, currentYear,currentRegion, clientsActivityPerMachine);
							break;
							
						default:
							break;
						}
						reports.add(report);
					}
				}
			}
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
	
				Order o = new Order(rs.getString("machineLocation"), rs.getString("orderDate"),
						rs.getString("status"), rs.getString("customerID"), rs.getFloat("totPrice"),
						rs.getString("type"), rs.getInt("productsQuantity"));
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
	 * @return ArrayList of product stocks
	 */
	private static ArrayList<Product> getProductStockByDateAndMachine(String month, String year, String currentMachine) {
		ArrayList<Product> productsStock = new ArrayList<>();
		try {
			Statement stmt = mysqlConnection.conn.createStatement();
			Statement stmt2 = mysqlConnection.conn.createStatement();
			String currentStock = currentMachine + "products";
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM db_ekrut.orders "
					+ "WHERE (orderDate BETWEEN \'"+year+"-"+month+"-01\' AND CURDATE() ) AND (machineLocation = \""+currentMachine+"\")");
			
			int[] removedStocks = new int[30];
			for (int i = 0; i < 30; i++)
				removedStocks[i] = 0;
			
			while (rs.next()) {
				String[] productsIDs = rs.getString("productsIDs").split("\\,");
				String[] productsQuantities = rs.getString("QuantityPerProduct").split("\\,");
				for (int i = 0; i < productsIDs.length; i++) {
					int productID = Integer.parseInt(productsIDs[i]);
					int productQuantity = Integer.parseInt(productsQuantities[i]);
					removedStocks[productID] = removedStocks[productID] + productQuantity;
				}
			}
			rs.close();
			ResultSet rs2 = stmt2.executeQuery("SELECT * FROM " + currentStock);
			while (rs2.next()) {
				int ID = Integer.parseInt(rs2.getString("productID"));
				int quantity = rs2.getInt("stockQuantity") + removedStocks[ID];
				Product product = new Product(rs2.getString("productID"),
						rs2.getString("productName"), rs2.getString("price"), quantity + "", null);
				productsStock.add(product);
			}
			rs2.close();

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
	 * @return HashMap of clients per order amount
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

	public static ArrayList<Product> getProducts(String location) {
		Product product;
		ArrayList<Product> listOfProducts = new ArrayList<>();
		Statement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM "+ location+"products");
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
	public static ArrayList<Order> getOrders()
	{
		Order order;
		ArrayList<Order> ordersList = new ArrayList<>();
		Statement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM orders");
				while (rs.next()) {

					order = new Order(Integer.parseInt(rs.getString("orderNum")), rs.getString("vendingMachineLocation"), rs.getString("orderDate"),
							rs.getString("orderStatus"), rs.getString("customerID"), convertStringToFloat(rs.getString("totalPrice")),rs.getString("orderType"),Integer.parseInt(rs.getString("quantityOfProducts")));
					ordersList.add(order);
				}
				rs.close();
			} else {
				System.out.println("Conn is null");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ordersList;
	}
	
	// Function to convert String to Float
    public static float convertStringToFloat(String str)
    {
  
        // Convert string to float
        // using valueOf() method
        return Float.valueOf(str);
    }
    
    public static void updateProductStock(ArrayList<Product> productList)
    {
    	PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {
				for (Product row : productList) {
					stmt = mysqlConnection.conn
							.prepareStatement("UPDATE ? SET stockQuantity = ? where location = ?");
					stmt.setString(1,row.getMachineName() );
					stmt.setString(2, row.getStockQuantity());
					stmt.setString(3, row.getProductName());
					stmt.executeUpdate();
				}
			} else {
				System.out.println("Conn is null");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    public static void addDelivery(OrderToDeliveryDetails delivery)
    {
    	//INSERT INTO table_name (column1, column2, column3,etc) VALUES (value1, value2, value3, etc);
    	PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {
				
					stmt = mysqlConnection.conn
							.prepareStatement("INSERT ordertodelivery (orderId, address, date, accept, done) VALUES (?, ?, ?, ?, ?)");
					stmt.setString(1,delivery.getOrderId());
					stmt.setString(2,delivery.getAddressToDelivey());
					stmt.setString(3,delivery.getDate());
					stmt.setString(4, delivery.getAccept());
					stmt.setString(5, delivery.getDone());
					stmt.executeUpdate();
				
			} else {
				System.out.println("Conn is null");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    public static void addOrder(Order order)
    {
    	//INSERT INTO table_name (column1, column2, column3,etc) VALUES (value1, value2, value3, etc);
    	PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {
				
					stmt = mysqlConnection.conn
							.prepareStatement("INSERT orders (orderNum, orderDate, status, customerID, machineLocation, totPrice, type, productsIDs, productsPrice, QuantityPerProduct, paymentType)"
									+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
					stmt.setString(1,Integer.toString(order.getOrderNum()));
					Date date=new SimpleDateFormat("dd/MM/yyyy").parse(order.getOrderDate())
					stmt.setDate(2, (java.sql.Date) date);
					stmt.setString(3,order.getOrderStatus());
					stmt.setString(4, order.getCustomerID());
					stmt.setString(5, order.getVendingMachineLocation());
					stmt.setString(6, Float.toString(order.getTotalPrice()));
					stmt.setString(7, order.getOrderType());
					stmt.setString(8, order.getProducts());
					stmt.setString(9, order.getProductsPrice());
					stmt.setString(10, order.getQuantityPerProducts());
					stmt.setString(11, Integer.toString(order.getQuantityOfProducts()));
					stmt.setString(12,null);
					stmt.executeUpdate();
				
			} else {
				System.out.println("Conn is null");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	
	/**
	 * get notRead messages by the worker id
	 * 
	 * @param workerID - the userID of the worker
	 * @return message - the messages of the worker
	 */
	public static String getWorkerMessages(String workerID) {
		ArrayList<String> messages= new ArrayList<>();
		PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.prepareStatement("SELECT message FROM workermessages WHERE workerID = ? and msgStatus=?");
				stmt.setString(1, workerID);
				stmt.setString(2, "notRead");
				ResultSet rs = stmt.executeQuery();
				while (rs.next())
					messages.add(rs.getString("message"));
				rs.close();
			}
			 else {
					System.out.println("Conn is null");
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String msg="";
		for (String row : messages)
			msg += row.toString()+"\n";
		return msg;
	}
	
	/**
	 * update the notRead messages of the worker id to read
	 * 
	 * @param workerID - the userID of the worker
	 */
	public static void updateWorkerMessagesStatus(String workerID) {  
		PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {
				
				stmt = mysqlConnection.conn.prepareStatement("UPDATE workermessages SET msgStatus = ? where workerID = ? and msgStatus = ?");
				stmt.setString(1, "read");
				stmt.setString(2, workerID);
				stmt.setString(3, "notRead");
				stmt.executeUpdate();
			}
			 else {
					System.out.println("Conn is null");
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
		/**
		 * insert new messages to worker messages in DB
		 * 
		 * @param  msg - the messages of the worker
		 */
		public static void insertWorkerMessages(String msg) {
			PreparedStatement stmt;
			int id=0;
			try {
				if (mysqlConnection.conn != null) {
					stmt = mysqlConnection.conn.prepareStatement("SELECT COUNT(*) FROM workermessages");
					//stmt.setString(1, workerID);
					//stmt.setString(2, "notRead");
					ResultSet rs = stmt.executeQuery();
					if (rs.next())
						id = rs.getInt(1);
///////////////////////////////////
					stmt = mysqlConnection.conn.prepareStatement("INSERT INTO workermessages(id, workerID, message, msgStatus) VALUES (?, ?, ?, ?)");
					stmt.setLong(1, ++id);
					stmt.setString(2, "5");
					stmt.setString(3, msg);
					stmt.setString(4, "notRead");
					stmt.executeQuery();
				}
				 else {
						System.out.println("Conn is null");
				 }
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	/**
	 * view delivery order data from DB
	 * 
	 * @return ArrayList of orders from the DB
	 */
	public static ArrayList<OrderToDeliveryDetails>viewDeliveryOrders(){
		ArrayList<OrderToDeliveryDetails> orders = new ArrayList<>();

		Statement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM ordertodelivery");
				while (rs.next()) {
					OrderToDeliveryDetails v = new OrderToDeliveryDetails(rs.getString("orderId"), rs.getString("address"), rs.getString("date"), rs.getString("accept"), rs.getString("done"));
					orders.add(v);
				}
				rs.close();
			} else {
				System.out.println("Conn is null");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return orders;
	}
	/**
	 * Delivery is done. Set "Done" in the DB.
	 * 
	 * @param order id
	 */
	public static void UpdateOrderDeliveryToDone(String id) {
		PreparedStatement stmt;
		System.out.println("UpdateOrderDeliveryToDone"+": im here");
		try {
			if (mysqlConnection.conn != null) {
				 {
				stmt = mysqlConnection.conn.prepareStatement("UPDATE ordertodelivery SET done = 'Done'  where orderId = ?");
				stmt.setString(1, id);
				
				
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
	 * Get userName for delivery order
	 * 
	 *  @param orderId
	 *  @return String with userName 
	 * */
	public static String getUserNameToDeliveryOrder(String orderId){
		
		String userName ="";
		PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {
				 {
				stmt = mysqlConnection.conn.prepareStatement
						("SELECT distinct userName FROM  db_ekrut.orders as o,db_ekrut.users as u where o.orderNum = ? and o.customerID = u.id");
				stmt.setString(1, orderId);
				
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					userName = rs.getString("userName");
				}

				}
			} else {
				System.out.println("Conn is null");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userName; //user name is primary key so there will be just one String.
	}
	
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
				ResultSet rs = stmt.executeQuery("SELECT * FROM userstosignup WHERE creditCard IS NULL");
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

	
	public static UsersToRegister getUserToRegisterDetails(String id){
		UsersToRegister user = null;
		Statement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM userstosignup WHERE id='"+id+"'");
				if (rs.next())
					user = new UsersToRegister(rs.getString("id"), rs.getString("firstName"),
						rs.getString("lastName"), rs.getString("email"), rs.getString("phone"));
				rs.close();
			} else {
				System.out.println("Conn is null");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public static void insertCreditCardAndRegion(String data) {
		PreparedStatement stmt;
		String splitData[]= new String[3];
		splitData=data.split("#");
		System.out.println("UpdateUserToSignUpDetails");
		try {
			if (mysqlConnection.conn != null) {
				 {
				stmt = mysqlConnection.conn.prepareStatement("UPDATE userstosignup SET creditCard = '"+splitData[1]+"', region='"+splitData[2]+"' where id = ?");
				stmt.setString(1, splitData[0]);
				
				
				stmt.executeUpdate();
				}
			} else {
				System.out.println("Conn is null");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static ArrayList<UsersToRegister> getRegistrationRequests(String region) {
		UsersToRegister usersToRegister;
		ArrayList<UsersToRegister> listOfUsersToRegister = new ArrayList<>();
		Statement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM userstosignup WHERE region='"+region+"'");
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

	
}
