package db;

import java.io.BufferedReader;

import java.io.FileReader;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date;  
import static java.lang.Integer.parseInt;

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
					result.append("#");
					result.append(rs.getString(11)); //region
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
	 * update the Re-stock Status of the vending machine in location in the DB to LowStock.
	 * 
	 * @param vendingMachine to update his Restock Status
	 * @param oldStatus - the old status to update
	 */
	public static void UpdateVendingMachineRestockStatus(VendingMachine vendingMachine,String oldStatus) {
		PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {
				
					stmt = mysqlConnection.conn
							.prepareStatement("UPDATE vendingmachines SET restockStatus = ? where location = ? and restockStatus = ?");
					stmt.setString(1, vendingMachine.getRestockStatus());
					stmt.setString(2, vendingMachine.getLocation());
					stmt.setString(3, oldStatus);
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
	
				Order o = new Order(rs.getInt("orderNum"),rs.getString("machineLocation"), rs.getString("orderDate"),
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

	/**
	 * get the products in location from DB
	 * @param location - the location of the vending machine
	 * @return list of products
	 */
	public static ArrayList<Product> getProducts(String location) {
		Product product;
		ArrayList<Product> listOfProducts = new ArrayList<>();
		Statement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM "+ location.toLowerCase() +"products");
				while (rs.next()) {

					product = new Product(rs.getString("productID"), rs.getString("productName"), rs.getString("price"),
							rs.getString("stockQuantity"), rs.getString("imgSrc"));
					product.setMachineName(location);
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
	 * gets order list from table
	 * @return ArrayList of existing Orders
	 */
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

					order = new Order(Integer.parseInt(rs.getString("orderNum")), rs.getString("machineLocation"), rs.getString("orderDate"),
							rs.getString("status"), rs.getString("customerID"), convertStringToFloat(rs.getString("totPrice")),rs.getString("type"),Integer.parseInt(rs.getString("productsQuantity")));
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
    
    /**
     * update the stock quantity in the products tables in DB
     * @param productList - the products after update
     */
    public static void updateProductStock(ArrayList<Product> productList)
    {
    	PreparedStatement stmt;
    	String location = (productList.get(0).getMachineName()).toLowerCase();
    	try {
			if (mysqlConnection.conn != null) {
				for (Product row : productList) {
					stmt = mysqlConnection.conn
							.prepareStatement("UPDATE " + location + "products SET stockQuantity = ? where productID = ?");
					stmt.setString(1, row.getStockQuantity());
					stmt.setString(2, row.getProductID());
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
     *  adds new delivery order to delivery table the delivery is new and needs to be prepared
     * @param delivery - new delivery order to deliver
     */
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
    

    /**
	 * add new order to DB
	 * @param order - new order that made by costumer
	 * @throws ParseException 
	 * 
	 */
    public static void addOrder(Order order) throws ParseException
    {
    	System.out.println(order);
    	PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {
				
					stmt = mysqlConnection.conn
							.prepareStatement("INSERT orders (orderNum, orderDate, status, customerID, machineLocation, totPrice, type, productsIDs, productsPrice, productsQuantity,QuantityPerProduct, paymentType)"
									+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
					stmt.setString(1,Integer.toString(order.getOrderNum()));
					//Date date1=new SimpleDateFormat("dd-MM-yyyy").parse(order.getOrderDate());  
					stmt.setString(2, order.getOrderDate());
					stmt.setString(3,order.getOrderStatus());
					stmt.setString(4, order.getCustomerID());
					stmt.setString(5, order.getVendingMachineLocation());
					stmt.setString(6, Float.toString(order.getTotalPrice()));
					stmt.setString(7, order.getOrderType());
					stmt.setString(8, order.getProducts());
					stmt.setString(9, order.getProductsPrice());
					stmt.setString(10, Integer.toString(order.getQuantityOfProducts()));
					stmt.setString(11, order.getQuantityPerProducts());
					
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
     * gets credit card number from user table
     * @return credit card number
     */
    public static String getCreditCard(String id)
    {
    	String card = "";
    	PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.prepareStatement("SELECT creditCardNum FROM users where userID = ?");
				stmt.setString(1,id);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {

					card = rs.getString("creditCardNum");
				}
				rs.close();
			} else {
				System.out.println("Conn is null");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return card;
    }
    /**
     * updates payment time of user to an order (now or later)
     * @param payTime - now or delayed
     */
    public static void updatePayment(Order order)
    {
    	PreparedStatement stmt;
    	String payTime = order.getPaymentType();
    	try {
			if (mysqlConnection.conn != null) {
				
					stmt = mysqlConnection.conn
							.prepareStatement("UPDATE orders  SET paymentType = ? where orderNum = ?");
					stmt.setString(1, payTime);
					stmt.setInt(2, order.getOrderNum());
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
     * changes pickup order status if it is collected 
     * @param order  - order that collected from pickup
     */
    public static void updatePickup(int num)
    {
    	PreparedStatement stmt;
    	try {
			if (mysqlConnection.conn != null) {
				
					stmt = mysqlConnection.conn
							.prepareStatement("UPDATE orders  SET status = ? where orderNum = ?");
					stmt.setString(1, "collected");
					stmt.setInt(2, num);
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
     * check if user pickup order exits in DB and not collected
     * @param orderNum - checks pickup order by order Number
     * @return if pickup order exists
     */
    public static String getPickup(int orderNum)
    {
    	PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.prepareStatement("SELECT * FROM orders WHERE orderNum = ? and status = 'placed' and type = 'pickup'");
				stmt.setInt(1, orderNum);
				
				ResultSet rs = stmt.executeQuery();
				if (rs.next())
					return "pickupOrder";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "noPickupOrder";
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
	 * get userID of the worker by the region and role
	 * 
	 * @param region of the worker
	 * @param role of the worker
	 * @return userID of the worker
	 */
	public static String getWorkerIDByRegion(String region,String role) {
		PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.prepareStatement("SELECT userID FROM users WHERE region = ? and role = ?");
				stmt.setString(1, region);
				stmt.setString(2, role);
				ResultSet rs = stmt.executeQuery();
				if (rs.next())
					return rs.getString("userID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "noUserID";
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
		public static void insertWorkerMessages(WorkerMessage msg) {	////////////////////////////////////////////////
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
					rs.close();
					stmt = mysqlConnection.conn.prepareStatement("INSERT workermessages (id, workerID, message, msgStatus) VALUES (?, ?, ?, ?)");
					stmt.setInt(1, ++id);
					stmt.setString(2, msg.getWorkerID());
					stmt.setString(3, msg.getMessage());
					stmt.setString(4, msg.getMsgStatus());
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
	 * Delivery is accept one delivery pressed. Set "accept" in the DB.
	 * 
	 * @param order id
	 */
	public static void UpdateOrderDeliveryToAccept(String id) {
		PreparedStatement stmt;
		
		try {
			if (mysqlConnection.conn != null) {
				 {
				stmt = mysqlConnection.conn.prepareStatement("UPDATE ordertodelivery SET accept = 'accept'  where orderId = ?");
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
	 * @author Aviram fishman
	 * @return
	 */
	public static ArrayList<PromotionSells> viewPromotion(){
		ArrayList<PromotionSells> Promotions = new ArrayList<>();

		Statement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM promotions");
				while (rs.next()) {
					PromotionSells v = new PromotionSells(rs.getString("discountAmount"), rs.getString("activate"),rs.getString("region")); //change///
					Promotions.add(v);
				}
				rs.close();
			} else {
				System.out.println("Conn is null");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Promotions;
	}

	/**
	 * @author Aviram fishman
	 * @return
	 */
	public static ArrayList<String> getRegion(){
		ArrayList<String> region = new ArrayList<>();

		Statement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM region");
				while (rs.next()) {
					region.add(rs.getString("region"));
				}
				rs.close();
			} else {
				System.out.println("Conn is null");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return region;
	}
	
	/**
	 * @author Aviram Fishman
	 * */
	public static void activatePremition(String promotionKey, String setRegion) {
	PreparedStatement stmt;
		
	System.out.println("ActivatePremition: "+ setRegion + " "+ promotionKey );
		try {
			if (mysqlConnection.conn != null) {
				 {
				stmt = mysqlConnection.conn.prepareStatement("UPDATE promotions SET activate = 'activate' ,region = ?  where discountAmount = ?");
				stmt.setString(1, setRegion);
				stmt.setString(2, promotionKey);
				
				stmt.executeUpdate();
				}
			} else {
				System.out.println("Conn is null");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		
	}
		ArrayList<PromotionSells> str = viewPromotion();
		for(PromotionSells s : str) {
			System.out.println(s.getRegion());
		}
		
	}
	
	/**
	 * @author Aviram Fishman
	 * */
	public static void deActivatePremition(String promotionKey, String setRegion) {
	PreparedStatement stmt;
	
	System.out.println("deActivatePremition: "+ setRegion + " "+ promotionKey );
		try {
			if (mysqlConnection.conn != null) {
				 {
				stmt = mysqlConnection.conn.prepareStatement("UPDATE promotions SET activate = 'notActivate' ,region = ?  where discountAmount = ?");
				stmt.setString(1, setRegion);
				stmt.setString(2, promotionKey);
				
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
						("SELECT distinct userName FROM  db_ekrut.orders as o,db_ekrut.users as u where o.orderNum = ? and o.customerID = u.userID");
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
	 * @return a list from userstosignup according to the query
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

	/**
	 * 
	 * @param id
	 * @return user details from userstosignup table
	 */
	public static UsersToRegister getUserToRegisterDetails(String id){
		UsersToRegister user = null;
		Statement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM userstosignup WHERE id='"+id+"'");
				if (rs.next()) {
					user = new UsersToRegister(rs.getString("id"), rs.getString("firstName"),
						rs.getString("lastName"), rs.getString("email"), rs.getString("phone"));
				user.setCreditCard(rs.getString("creditCard"));
				user.setRegion(rs.getString("region"));
				rs.close();
			} else {
				System.out.println("Conn is null");
			}

		} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public static void insertCreditCardAndRegion(String data) {
		PreparedStatement stmt;
		String splitData[]= new String[3];
		splitData=data.split("#");
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
		PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {
				
				stmt = mysqlConnection.conn.prepareStatement("SELECT * FROM userstosignup WHERE region= ?");
				stmt.setString(1, region);
				ResultSet rs = stmt.executeQuery();
				
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
	 * 
	 * @param path
	 * @throws IOException
	 * imports a file to the userdtosignup table in the database
	 */
	
	public static void fileImportToCustomerRegistration(String path) throws IOException {
		PreparedStatement stmt;
		int batchSize=20;
		String sql="INSERT INTO userstosignup(id,firstName,lastName,email,phone) VALUES (?,?,?,?,?)";
		try {
			if (mysqlConnection.conn != null) {
				
				stmt = mysqlConnection.conn.prepareStatement(sql);
				System.out.println("prepared statement");//*************
				BufferedReader lineReader=new BufferedReader(new FileReader(path));
				String lineText=null;
				int count=0;
				lineReader.readLine();
				while((lineText=lineReader.readLine()) != null) {
					String[] data=lineText.split(",");
					String id=data[0];
					String firstName=data[1];
					String lastName=data[2];
					String email=data[3];
					String phone=data[4];
					stmt.setInt(1, parseInt(id));
					stmt.setString(2, firstName);
					stmt.setString(3, lastName);
					stmt.setString(4, email);
					stmt.setString(5, phone);
					stmt.addBatch();
					if(count%batchSize==0)
						stmt.executeBatch();
				}
				lineReader.close();
				stmt.executeBatch();
				System.out.println("data inserted");
			}
		}
			catch (SQLException e) {
				e.printStackTrace();
			}
		
	

	}
	/**@param id of client
	 * changes the client to club member if he exists **/
	
	
	public static boolean ChangeRoleToClubMember(String id) {
		PreparedStatement stmt;
		try {
			if (mysqlConnection.conn != null) {
				 {
				stmt = mysqlConnection.conn.prepareStatement("UPDATE users SET role = 'ClubMember' where (id = ? AND role='Customer')");
				stmt.setString(1, id);
				
				
				stmt.executeUpdate();
				return true;
				}
			} else {
				System.out.println("Conn is null");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void fileImportToWorkersRegistration(String path) throws IOException {
		PreparedStatement stmt;
		int batchSize=20;
		String sql="INSERT INTO users(userID,id,firstName,lastName,userName,password,role,email,phoneNumber,isLoggedIn,region) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.prepareStatement(sql);
				BufferedReader lineReader=new BufferedReader(new FileReader(path));
				String lineText=null;
				int count=0;
				lineReader.readLine();
				while((lineText=lineReader.readLine()) != null) {
					String[] data=lineText.split(",");
					String id=data[0];
					String firstName=data[1];
					String lastName=data[2];
					String role=data[3];
					String email=data[4];
					String phoneNumber=data[5];
					String region=data[6];
					
					stmt.setInt(1, countRowsinUsersTable()+1);
					stmt.setInt(2, parseInt(id));
					stmt.setString(3, firstName);
					stmt.setString(4, lastName);
					stmt.setString(5, firstName+countRowsinUsersTable());
					stmt.setString(6, "123456");
					stmt.setString(7, role);
					stmt.setString(8, email);
					stmt.setString(9, phoneNumber);
					stmt.setInt(10, 0);
					stmt.setString(11, region);
					stmt.addBatch();
					if(count%batchSize==0)
						stmt.executeBatch();
					
				}
				lineReader.close();
				stmt.executeBatch();
				
			}
		}
			catch (SQLException e) {
				e.printStackTrace();
			}
		
	

	}

	public static int countRowsinUsersTable() {
		PreparedStatement stmt;
		int count=0;	
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.prepareStatement("SELECT COUNT(*) FROM users");
				
				ResultSet rs = stmt.executeQuery();
				if (rs.next())
					count = rs.getInt(1);
				rs.close();

			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	/**
     * get new club members from DB to give them new club member discount
     * @return clubMemberList - returns List of new club members
     */
    public static ArrayList<ClubMember> getNewClubMembers()
    {
    	ClubMember clubMember;
    	ArrayList<ClubMember> clubMemberList = new ArrayList<>();
    	Statement stmt;
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM newclubmembers");
				while (rs.next()) {

					clubMember = new ClubMember(rs.getString("userID"), rs.getInt("isNew"));
					clubMemberList.add(clubMember);
				}
				rs.close();
			} else {
				System.out.println("Conn is null");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return clubMemberList;
    	
    }
    /**
     * changes club member that used his discount status 
     * @param clubMember  - club member that used his discount
     */
    public static void updateClubMemberStatus(ClubMember clubMember)
    {
    	PreparedStatement stmt;
    	try {
			if (mysqlConnection.conn != null) {
				
					stmt = mysqlConnection.conn
							.prepareStatement("UPDATE newclubmembers  SET isNew = ? where userID = ?");
					stmt.setInt(1, clubMember.getIsNew());
					stmt.setString(2, clubMember.getUserID());
					stmt.executeUpdate();
				}
			 else {
				System.out.println("Conn is null");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public static void insertIntoUsers(UsersToRegister user) {
		PreparedStatement stmt;
		String sql="INSERT INTO users(userID,id,firstName,lastName,userName,password,role,email,phoneNumber,isLoggedIn,region, creditCard) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.prepareStatement(sql);
				stmt.setInt(1, countRowsinUsersTable()+1);
				stmt.setInt(2, parseInt(user.getId()));
				stmt.setString(3, user.getFirstName());
				stmt.setString(4, user.getLastName());
				stmt.setString(5, user.getFirstName()+countRowsinUsersTable());
				stmt.setString(6, "123456");
				stmt.setString(7, "Customer");
				stmt.setString(8, user.getEmail());
				stmt.setString(9, user.getPhone());
				stmt.setInt(10, 0);
				stmt.setString(11, user.getRegion());
				stmt.setString(12, user.getCreditCard());

				
				stmt.addBatch();
				stmt.executeBatch();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

		
  }



