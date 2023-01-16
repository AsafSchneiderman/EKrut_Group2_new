package controller;

import java.io.*;
import java.net.Inet4Address;
import java.text.ParseException;
import java.util.ArrayList;

import db.mysqlConnection;
import ocsf.server.*;
import gui.ServerPortFrameController;
import gui.ServerUI;
import db.ShowSubscriber;
import db.UpdateDB;
import db.Query;
import Entities.*;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Aviram Fishman
 * @author Hanna Kruchenetzky
 * @author Nofar Oshri Bensimon
 * @author Asaf Schneiderman
 * @author marina.shteinfer
 */

public class EchoServer extends AbstractServer {
	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;
	public static int clientNumber = 1;
	private Message resMessage;
	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */
	public EchoServer(int port) {
		super(port);
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	@SuppressWarnings("unchecked")
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {

		if (msg instanceof Message) {
			resMessage = (Message) msg;

			// System.out.println("Message: "+ resMessage.getMessageData().toString() +"
			// --"+ resMessage.getMessageType().toString());
			switch (resMessage.getMessageType()) { // message - type
			/** Server connection section **/
			case login: // the user login to the system and change him to '1' in the DB.
				String[] data = resMessage.getMessageData().toString().split("#");
				try {
					client.sendToClient(new Message(MessageType.login, (Object) (Query.login(data[0], data[1]))));

				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case logout: // the user logout from the system and change him to '0' in the DB.
				Query.logout(resMessage.getMessageData().toString());
				break;
			case disconnected: // disconnected from server
				ServerUI.serverGUI.appendToConsole(client.getName() + " has disconnected");
				break;
			case connected: // connected to server
				client.setName("client #" + clientNumber + " (" + resMessage.getMessageData().toString() + ") ");
				clientNumber++;
				ServerUI.serverGUI.appendToConsole(client.getName() + " connected successfully");
				break;
			/** Region Manager section **/
			case Get_region:
				try {
					client.sendToClient(new Message(MessageType.Get_region,
							(Object) (Query.getRegion((String) resMessage.getMessageData()))));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

			case Get_vendingMachines: // get list of vending machines from DB
				try {
					client.sendToClient(
							new Message(MessageType.Get_vendingMachines, (Object) (Query.getVendingMachines())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case update_thresholdLevel: // update the threshold level of the vending machines in the DB
				Query.UpdateVendingMachineThresholdLevel((ArrayList<VendingMachine>) resMessage.getMessageData());
				break;
			case update_restockStatusToLowStatus: // update the restock status of the vending machines in the DB
														// from Done to LowStock
				Query.UpdateVendingMachineRestockStatus((VendingMachine) resMessage.getMessageData(),
						"Done");
				break;
			case update_restockStatusToWaitToRestock: // update the restock status of the vending machines in the DB
														// from LowStock to WaitToRestock
				Query.UpdateVendingMachineRestockStatus((VendingMachine) resMessage.getMessageData(),
						"LowStock");
				break;
			case update_restockStatusToDone: // update the restock status of the vending machines in the DB
														// from WaitToRestock to Done
				Query.UpdateVendingMachineRestockStatus((VendingMachine) resMessage.getMessageData(),
						"WaitToRestock");
				break;
			case Get_messages: // get the worker messages from DB
				try {
					client.sendToClient(new Message(MessageType.Get_messages,
							(Object) (Query.getWorkerMessages((String) resMessage.getMessageData()))));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case update_messagesStatus: // update the worker messages status in the DB
				Query.updateWorkerMessagesStatus((String) resMessage.getMessageData());
				break;
			case insert_RegionManagerMessages: // insert the message for region manager to the DB
				Query.insertWorkerMessages((SystemMessage) resMessage.getMessageData());
				break;
			case insert_OperationsWorkerMessages: //insert the message for operations worker to the DB
				Query.insertWorkerMessages((SystemMessage) resMessage.getMessageData());
				break;
			case Get_reports:
				try {

					client.sendToClient(new Message(MessageType.Get_reports,
							(Object) (Query.getReports((String) resMessage.getMessageData()))));

				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case Show_products: // to show products in order frame and update stock frame
			{
				ArrayList<Product> pList;
				pList = Query.getProducts((String) resMessage.getMessageData());
				try {
					client.sendToClient(new Message(MessageType.Show_products, (Object) pList));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			case showUsersToRegister: {
				ArrayList<UsersToRegister> uList;
				uList = Query.getUsersToRegister();
				try {
					client.sendToClient(new Message(MessageType.showUsersToRegister, (Object) uList));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			}

			case GetDeliveryOrder:
				try {

					client.sendToClient(
							new Message(MessageType.GetDeliveryOrder, (Object) (Query.viewDeliveryOrders())));

				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

			case setToDone:
				String id = resMessage.getMessageData().toString();
				Query.UpdateOrderDeliveryToDone(id);
				break;

			case showUserDetails:
				UsersToRegister user;
				user = Query.getUserToRegisterDetails((String) resMessage.getMessageData());
				try {
					client.sendToClient(new Message(MessageType.showUserDetails, (Object) user));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case getUserToDelivery:
				String orderId = resMessage.getMessageData().toString();
				try {
					client.sendToClient(new Message(MessageType.getUserToDelivery,
							(Object) (Query.getUserNameToDeliveryOrder(orderId))));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case insertCreditCardAndRegion:
				String dataFromCustomer = resMessage.getMessageData().toString();
				Query.insertCreditCardAndRegion(dataFromCustomer);
				break;

			case Orders_list: {
				ArrayList<Order> pList;
				pList = Query.getOrders();
				try {
					client.sendToClient(new Message(MessageType.Show_products, (Object) pList));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			case updateProductStock: // update the product stock in DB
			{
				ArrayList<Product> pList = ((ArrayList<Product>) resMessage.getMessageData());
				Query.updateProductStock(pList);
				break;
			}
			case addDelivert: {
				OrderToDeliveryDetails delivery = (OrderToDeliveryDetails) resMessage.getMessageData();
				Query.addDelivery(delivery);
				break;
			}
			case addOrder: {
				Order order = (Order) resMessage.getMessageData();
				try {
					Query.addOrder(order);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			case showRegistrationRequests: {
				ArrayList<UsersToRegister> uList;
				uList = Query.getRegistrationRequests(resMessage.getMessageData().toString());
				try {
					client.sendToClient(new Message(MessageType.showUsersToRegister, (Object) uList));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			}

			default:
				break;
			} // end of case
		}

	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		// mysqlConnection.connectionDb(); //connecting to server.
		ServerUI.serverGUI.appendToConsole("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		ServerUI.serverGUI.appendToConsole("Server has stopped listening for connections.");
	}

	// Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the server instance (there is
	 * no UI in this phase).
	 *
	 * @param args[0] The port number to listen on. Defaults to 5555 if no argument
	 *                is entered.
	 */
}
//End of EchoServer class
