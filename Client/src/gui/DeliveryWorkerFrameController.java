package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

import Entities.*;

import controller.ChatClient;

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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
	public static Time time1; // Define time to timer
	public static ArrayList<Button> btn1 = new ArrayList<>(); // done
	public static ArrayList<Button> btn2 = new ArrayList<>(); // accept
	public static HashMap<Integer, Integer> pos = new HashMap<>(); // position of accept button was clicked.
	public static String userNameForLabel;
	static Stage newWindow;

	@FXML
	private TableColumn<OrderToDeliveryDetails, String> CustomerRecivedOrderCol;

	@FXML
	private AnchorPane pane;

	@FXML
	private TableColumn<OrderToDeliveryDetails, String> addrCol;

	@FXML
	private Button btnExit;

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
		btn1 = new ArrayList<>();
		btn2 = new ArrayList<>();
		pos = new HashMap<>();

		// back to menu
		DeliveryWorkerMenuController deliver = new DeliveryWorkerMenuController();
		try {
			deliver.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Delivery Worker >> Menu >> Delivery");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/DeliveryWorkerFrame.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);

		// On pressing X (close window) the user logout from system and the client is
				// disconnect from server.
				primaryStage.setOnCloseRequest(e -> {
					msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
					ClientMenuController.clientControl.accept(msg);
					ClientMenuController.clientControl
							.accept(new Message(MessageType.disconnected, LoginFrameController.user.getUserName()));
					// create a PopUp message
					PopUpMessageFrameController popUpMsgController = new PopUpMessageFrameController();

					try {
						popUpMsgController.start(ClientMenuController.clientStage);
						popUpMsgController.closeMsg(3000);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				});
		primaryStage.show();
	}

	/*
	 * Changing status of shipped order to done
	 * 
	 * @param received button was clicked by user
	 * 
	 */
	public void pressedDone(ActionEvent event, Button btn) {

		// changed deliver screen at button "Done" to setDisable false
		
		btn.setDisable(true);
		msg = new Message(MessageType.setToDone, DeliveryWorkerFrameController.time1.getOrderId());
		ClientMenuController.clientControl.accept((Object) msg);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// initialize the background image
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/DeliveryWorkerFrame.png"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));
		// initialize the Welcome label to welcome and the full name of the user
		welcomeWorkerLbl.setText(
				"Welcome " + LoginFrameController.user.getFirstName() + " " + LoginFrameController.user.getLastName());

		tblViewDelivery.setEditable(true);
		orderIdCol.setCellValueFactory(new PropertyValueFactory<OrderToDeliveryDetails, String>("orderId"));
		addrCol.setCellValueFactory(new PropertyValueFactory<OrderToDeliveryDetails, String>("addressToDelivey"));
		dateCol.setCellValueFactory(new PropertyValueFactory<OrderToDeliveryDetails, String>("date"));

		// Create button for delivery received order and customer received
		// order.
		deliveryRecievedDeliveryCol
				.setCellValueFactory(new PropertyValueFactory<OrderToDeliveryDetails, String>("btnDeliverAccept"));
		CustomerRecivedOrderCol
				.setCellValueFactory(new PropertyValueFactory<OrderToDeliveryDetails, String>("btnOrderIsDone"));

		// defining in each row in table "Accept" button
		Callback<TableColumn<OrderToDeliveryDetails, String>, TableCell<OrderToDeliveryDetails, String>> cellFactory =

				new Callback<TableColumn<OrderToDeliveryDetails, String>, TableCell<OrderToDeliveryDetails, String>>() {

					@Override
					public TableCell call(final TableColumn<OrderToDeliveryDetails, String> param) {
						final TableCell<OrderToDeliveryDetails, String> cell = new TableCell<OrderToDeliveryDetails, String>() {

							int timeEsimateDelivery; // estimate time delivery
							final Button btn = new Button("Accept");

							@Override
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
									setText(null);
								} else {
									OrderToDeliveryDetails orderDetail = getTableView().getItems().get(getIndex());
									if (orderDetail.getAccept().equals("accept"))
										btn.setDisable(true);
									else
										btn.setDisable(false);

									btn.setOnAction(event -> {
										int index = getIndex();// getting index for clicking accept button

										pos.put(1, index);

										// send message to customer with arrival date of purchase.
										timeEsimateDelivery = calculateOrderTime(orderDetail.getAddressToDelivey()); // calculate
																														// delivery
																														// time
										int hours = timeEsimateDelivery / 3600;
										int minutes = (timeEsimateDelivery % 3600) / 60;
										int seconds = timeEsimateDelivery % 60;

										time1 = new Time(hours + ":" + minutes + ":" + seconds,
												orderDetail.getOrderId());

										// create timer GUI to client
										newWindow = new Stage();
										newWindow.setTitle("New Scene");
										// Create view from FXML
										FXMLLoader loader = new FXMLLoader(
												getClass().getResource("/gui/DeliveryTimerAndConfirmation.fxml"));
										// Set view in window
										try {
											// changed in DB accept value and Getting userName for order.
											msg = new Message(MessageType.getUserToDeliveryAndChangeAccept,
													(Object) (orderDetail.getOrderId()));
											ClientMenuController.clientControl.accept(msg);
											newWindow.setTitle("Delivery Timer");
											newWindow.setScene(new Scene(loader.load()));
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										btn.setDisable(true);
										// Launch
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

		// defining in each row in table "Done" button
		Callback<TableColumn<OrderToDeliveryDetails, String>, TableCell<OrderToDeliveryDetails, String>> cellFactoryReceivedOrder = new Callback<TableColumn<OrderToDeliveryDetails, String>, TableCell<OrderToDeliveryDetails, String>>() {

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
							if (orderDetail.getDone().equals("notDone") && orderDetail.getAccept().equals("accept")) {
								btn.setDisable(false);
							} else
								btn.setDisable(true);
							btn.setOnAction(e -> {
								if (orderDetail.getDone().equals("notDone")) {
									DeliveryWorkerFrameController.time1 = new Time("00:00:00",
											orderDetail.getOrderId());
								}
								pressedDone(e, btn);
							});
							btn1.add(btn);

							setGraphic(btn);
							setText(null);
						}
					}

				};
				return cell;
			}
		};
		// defining customer received column
		CustomerRecivedOrderCol.setCellFactory(cellFactoryReceivedOrder);
		// creating table order to be shipping
		ObservableList<OrderToDeliveryDetails> tvObservableList = FXCollections.observableArrayList();
		callToDb();
		ArrayList<OrderToDeliveryDetails> orders = (ArrayList<OrderToDeliveryDetails>) ChatClient.msgServer
				.getMessageData();
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

		int distanceFromWareHouse = 5;
		int DroneAvailability = 0; // will used in the second phase
		int estimateTimeAvalibalityAndCollectionOrder = 2;

		// return time in seconds
		return distanceFromWareHouse + DroneAvailability + estimateTimeAvalibalityAndCollectionOrder;
	}

}
