package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

import Entities.Message;
import Entities.MessageType;
import Entities.OrderToDeliveryDetails;
import Entities.VendingMachine;
import controller.ChatClient;
import Entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

/*
 * DeliveryWorkerFrameController is managing: 
 * 																		1. accepting order to deliver
 * 																		2. send to customer estimated shipping time 
 * 																		3. changing status order
 * */
public class DeliveryWorkerFrameController implements Initializable {

	private static Message msg; // message to send to server
	public static Time time1; //Define time to timer
	public static ArrayList<Button>  btn1 = new ArrayList<>(); //done
	public static ArrayList<Button>  btn2 = new ArrayList<>(); //accept
	public static HashMap<Integer,Integer>  pos = new HashMap<>(); //position of accept button was clicked.
	public static String userNameForLabel;
    @FXML
    private TableColumn<OrderToDeliveryDetails, String> CustomerRecivedOrderCol;

    @FXML
    private TableColumn<OrderToDeliveryDetails, String> addrCol;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnUpdateStatusOrder;

    @FXML
    private TableColumn<OrderToDeliveryDetails, String> dateCol;

    @FXML
    private TableColumn<OrderToDeliveryDetails, String> deliveryRecievedDeliveryCol;

    @FXML
    private TableColumn<OrderToDeliveryDetails, String> orderIdCol;


    @FXML
    private TableView<OrderToDeliveryDetails> tblViewDelivery;

    @FXML
    private Label welcomeWorkerLbl;

    @FXML
    void exit(ActionEvent event) {
    	ClientMenuController.clientStage.setScene(LoginFrameController.home);
    	//Logout
    	msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
		ClientMenuController.clientControl.accept(msg);
    }
    @FXML
    void updateOrderStatusToDelivery(ActionEvent event) {
    	
    }

    public void start(Stage primaryStage) throws IOException{
    	ClientMenuController.clientStage = primaryStage;
    	Parent root = FXMLLoader.load(getClass().getResource("/gui/DeliveryWorkerFrame.fxml"));    	
    	Scene home = new Scene(root);
		primaryStage.setScene(home);
		
		// On pressing X (close window) the user logout from system and the client is
		// disconnect from server.
		primaryStage.setOnCloseRequest(e -> {
				msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
				ClientMenuController.clientControl.accept(msg);
				});
		primaryStage.show();
	}

    /*
     * Changing status of shipped order to done
     *  
     * @param received button was clicked by user
     * 
     * */
    public void pressedDone(ActionEvent event) {
    	//changed deliver screen at button "Done" to setDisable false		
    	msg = new Message(MessageType.setToDone, DeliveryWorkerFrameController.time1.getOrderId());
    	ClientMenuController.clientControl.accept((Object) msg);
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//initialize the Welcome label to welcome and the full name of the user
		welcomeWorkerLbl.setText("Welcome "+LoginFrameController.user.getFirstName()+" "+LoginFrameController.user.getLastName());
		
		tblViewDelivery.setEditable(true);
		orderIdCol.setCellValueFactory(new PropertyValueFactory<OrderToDeliveryDetails,String>("orderId"));
		addrCol.setCellValueFactory(new PropertyValueFactory<OrderToDeliveryDetails,String>("addressToDelivey"));
		dateCol.setCellValueFactory(new PropertyValueFactory<OrderToDeliveryDetails,String>("date"));
		
		//need to create button for delivery received order and customer received order. 
		deliveryRecievedDeliveryCol.setCellValueFactory(new PropertyValueFactory<OrderToDeliveryDetails,String>("btnDeliverAccept"));
		CustomerRecivedOrderCol.setCellValueFactory(new PropertyValueFactory<OrderToDeliveryDetails,String>("btnOrderIsDone"));
		
		//defining in each row in table "Accept" button
		Callback<TableColumn<OrderToDeliveryDetails, String >, TableCell<OrderToDeliveryDetails, String >> cellFactory =
				
				new Callback<TableColumn<OrderToDeliveryDetails, String >, TableCell<OrderToDeliveryDetails, String >>(){

			 @Override
	            public TableCell call(final TableColumn<OrderToDeliveryDetails, String> param) {
	                final TableCell<OrderToDeliveryDetails, String> cell = new TableCell<OrderToDeliveryDetails, String>() {
	                	
	                	int timeEsimateDelivery; //estimate time delivery 
	                    final Button btn = new Button("Accept"); 

	                    @Override
	                    public void updateItem(String item, boolean empty) {
	                        super.updateItem(item, empty);
	                        if (empty) {
	                            setGraphic(null);
	                            setText(null);
	                        } else {
	                            btn.setOnAction(event -> {
	                            	OrderToDeliveryDetails orderDetail = getTableView().getItems().get(getIndex());
	                            	int index = getIndex();//getting index for clicking accept button
	                            	//pos.add(index, 1);
	                            	pos.put(1,index);
	                            	
	                            	btn.setDisable(true);
	                            	//send message to customer with arrival date of purchase.
	                            	timeEsimateDelivery = calculateOrderTime(orderDetail.getAddressToDelivey()); //calculate delivery time 
	                            	int hours =  timeEsimateDelivery / 3600;
	                            	int minutes = (timeEsimateDelivery % 3600) / 60;
	                            	int seconds = timeEsimateDelivery % 60;
	                            	time1 = new Time(hours + ":" + minutes + ":"+ seconds, orderDetail.getOrderId());

	                            	//create timer GUI to client
	                            	Stage newWindow = new Stage();
	                            	newWindow.setTitle("New Scene");
	                            	//Create view from FXML
	                            	FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/DeliveryTimerAndConfirmation.fxml"));
	                            	//Set view in window
	                            	try {
	                            		//Getting userName for order
	                            		msg = new Message(MessageType.getUserToDelivery, (Object)(orderDetail.getOrderId()));
	                            		ClientMenuController.clientControl.accept(msg);
	                            		newWindow.setTitle("Delivery Timer");
										newWindow.setScene(new Scene(loader.load()));
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
	                            	//Launch
	                            	newWindow.show();
	                            
	                            });
	                            btn2.add(btn);
	                            setGraphic(btn);
	                            setText(null);
	                        }
	                    }
	                };
	                return cell;
			 }	
		};
		deliveryRecievedDeliveryCol.setCellFactory(cellFactory);
		
		//defining in each row in table "Done" button
Callback<TableColumn<OrderToDeliveryDetails, String >, TableCell<OrderToDeliveryDetails, String >> cellFactoryReceivedOrder =
				new Callback<TableColumn<OrderToDeliveryDetails, String >, TableCell<OrderToDeliveryDetails, String >>(){

			 @Override
	            public TableCell call(final TableColumn<OrderToDeliveryDetails, String> param) {
	                final TableCell<OrderToDeliveryDetails, String> cell = new TableCell<OrderToDeliveryDetails, String>() {

	                    final Button btn = new Button("Done");
	                   
	                    @Override
	                    public void updateItem(String item, boolean empty) {
	                        super.updateItem(item, empty);
	                        if (empty) {
	                            setGraphic(null);
	                            setText(null);
	                        } else {
	                        	OrderToDeliveryDetails orderDetail = getTableView().getItems().get(getIndex());
	                        	btn.setDisable(true);
	                        	btn.setOnAction(e-> pressedDone(e));
	                        	btn1.add( btn);
	                        	System.out.println("intialize"+btn);
	                          
	                            setGraphic(btn);
	                            setText(null);
	                        }
	                    }
						
	                };
	                return cell;
			 }	
		};
		//defining customer received column 
		CustomerRecivedOrderCol.setCellFactory(cellFactoryReceivedOrder);
		//creating table order to be shipping
		ObservableList<OrderToDeliveryDetails> tvObservableList = FXCollections.observableArrayList();
		callToDb();
		ArrayList<OrderToDeliveryDetails> orders = (ArrayList<OrderToDeliveryDetails>) ChatClient.msgServer.getMessageData();
		for (OrderToDeliveryDetails row : orders) {
			tvObservableList.add(row);
		}
    	tblViewDelivery.setItems(tvObservableList);
		

	}
	
	/**
	 * Calling to DB to get all delivery purchases 
	 * 
	 * @author Aviram Fishman
	 *
	 */
	private void callToDb() {
		msg = new Message(MessageType.GetDeliveryOrder, "");
		ClientMenuController.clientControl.accept(msg);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Calculate time estimation of order deliver
	 * 
	 * @author Aviram Fishman
	 * @param Customer address
	 * @return estimation order time to be received 
	 *
	 */
	private int calculateOrderTime(String addressToDelivey) {
		
		//calculating time of availability of drone and product collection.
		Random rand = new Random();
		int distanceFromWareHouse = 0;
		
		int estimateTimeAvalibalityAndCollectionOrder = rand.nextInt(10);
		
		//calculate time from warehouse to  customer address
		if(addressToDelivey.equals("habrosh")) {
			distanceFromWareHouse = 2;
		}else if(addressToDelivey.equals("haifa"))
			distanceFromWareHouse = 3;
		else
			distanceFromWareHouse = 5; //to all other destination
		
		//return time in seconds
		return distanceFromWareHouse + estimateTimeAvalibalityAndCollectionOrder;
	}

}
