package gui;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import controller.ChatClient;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import Entities.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConfirmOrderFrameController implements Initializable {
	public static OrderFrameController toZero = new OrderFrameController();
	public static Stage clientStage;
	public static Message msg, msg2, msg3, msg4;
	public static Order order, order2;
	public static OrderToDeliveryDetails delivery;
	public static ArrayList<Order> orderList = new ArrayList<>();;
	@FXML
	private AnchorPane pane;

	@FXML
	private Button btnConfirmOrder;

	@FXML
	private Button btnCancelOrder;

	@FXML
	private TableView<OrderProductsForTbl> tlbInvoice;

	@FXML
	private TableColumn<OrderProductsForTbl, ImageView> colImgProd;

	@FXML
	private TableColumn<OrderProductsForTbl, String> colQuantityProd;

	@FXML
	private TableColumn<OrderProductsForTbl, String> colProdName;

	@FXML
	private TableColumn<OrderProductsForTbl, String> colProdPrice;

	@FXML
	private ImageView imgIcone;

	@FXML
	private Label lblTotalPrice;
	private ObservableList<ProductForOrder> tvObservableList;
	private ObservableList<OrderProductsForTbl> cartObservableList;
	private String location;
	private int counterForProducts;
	//float totPrice;
	java.sql.Date date, date2;
	SimpleDateFormat formatter;
	String strDate, strDate2;
	private static ArrayList<VendingMachine> vendingMachines = new ArrayList<>(); // list of vending machines in the DB

	@FXML
	private Text txtTimer;


	@FXML
	void cancelOrder(ActionEvent event) {

		/*OrderFrameController.productsList.removeAll(OrderFrameController.productsList);
		OrderFrameController.tvObservableList.removeAll(tvObservableList);
		OrderFrameController.cartObservableList.removeAll(cartObservableList);
		OrderFrameController.counterForProducts = 0;*/
		//OrderFrameController toZero = new OrderFrameController();
		toZero.setLabels();

		ClientMenuController.clientStage.setScene(LoginFrameController.home);
		// Logout
		msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
		ClientMenuController.clientControl.accept(msg);

	}

	@FXML
	void confirmOrder(ActionEvent event) {
		msg = new Message(MessageType.Orders_list, ""); // sends request from server to get all orders
		ClientMenuController.clientControl.accept(msg);
		try {
			Thread.sleep(1000); // wait for answer from server
			System.out.println(ChatClient.msgServer.getMessageData().toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		orderList = (ArrayList<Order>) ChatClient.msgServer.getMessageData(); // gets data from server - all existing orders
		int orderNum; // all orders data needed to create new order number
		int[] arrOrderNums = new int[orderList.size()];
		for (int i = 0; i < orderList.size(); i++) { // gets all orders numbers to array
			arrOrderNums[i] = orderList.get(i).getOrderNum();

		}
		Arrays.sort(arrOrderNums); // sorts the number from small to big
		orderNum = arrOrderNums[arrOrderNums.length - 1] + 1; // give new number to new order by chronological order
		for (int j = 0; j < OrderFrameController.productsList.size(); j++) { // sets machine name
			OrderFrameController.productsList.get(j).setMachineName(OrderFrameController.machine);
		}

		if (LoginFrameController.user.getRole().equals("ClubMember")) // if the costumer is club member
		{
			if ((this.location.equals("warehouse"))) // if the order is delivery
			{
				// getting current date
				long millis = System.currentTimeMillis();
				date = new java.sql.Date(millis);
				formatter = new SimpleDateFormat("MM-dd-yyyy");
				strDate = formatter.format(date);
				
				// create new order entity to send
				order = new Order(orderNum, OrderFrameController.machine, strDate, "placed", LoginFrameController.user.getUserID(),
						OrderFrameController.totPrice, "delivery", OrderFrameController.counterForProducts);
				order.setProducts(OrderFrameController.productsID);
				order.setQuantityPerProducts(OrderFrameController.productsQuantity);
				order.setProductsPrice(OrderFrameController.productsPrice);
				msg3 = new Message(MessageType.addOrder, order); // sends message to server
				ClientMenuController.clientControl.accept(msg3);
				// getting current date
				long millis2 = System.currentTimeMillis();
				date2 = new java.sql.Date(millis2);
				formatter = new SimpleDateFormat("MM.dd.yyyy");
				strDate2 = formatter.format(date2);
				// creating new delivery entity to send to DB to orders to deliver table
				delivery = new OrderToDeliveryDetails(Integer.toString(orderNum), OnlineOrderFrameController.city,
						strDate2, "notAccept", "notDone");
				msg4 = new Message(MessageType.addDelivert, delivery); // sends message to server
				ClientMenuController.clientControl.accept(msg4);

			} else // pickup order or local order
			{
				// getting current date
				long millis = System.currentTimeMillis();
				date = new java.sql.Date(millis);
				formatter = new SimpleDateFormat("MM-dd-yyyy");
				strDate = formatter.format(date);
				String type; // if the order is from machine so the system pick if the order is pickup or local
				if (ClientMenuController.config.equals("OL")) {
					type = "pickup";
				} else { // EK configuration
					type = "local";
				}
				// gets machine list
				vendingMachines = (ArrayList<VendingMachine>) ChatClient.msgServer.getMessageData();
				for (int i = 0; i < vendingMachines.size(); i++) { // checks if because of the order some of the products reached to threshold level
					if (vendingMachines.get(i).getLocation().equals(this.location)) {
						for (int j = 0; j < OrderFrameController.productsList.size(); j++) {
							int thresholdLevel = Integer.parseInt(vendingMachines.get(i).getThresholdLevel());
							int stockQuantity = Integer
									.parseInt(OrderFrameController.productsList.get(j).getStockQuantity());
							if (stockQuantity <= thresholdLevel) {

								break;
							}
						}
						break;
					}
				}
				order = new Order(orderNum, OrderFrameController.machine, strDate, "placed", LoginFrameController.user.getUserID(),
						OrderFrameController.totPrice, type, OrderFrameController.counterForProducts);
				order.setProducts(OrderFrameController.productsID);
				order.setQuantityPerProducts(OrderFrameController.productsQuantity);
				order.setProductsPrice(OrderFrameController.productsPrice);
				msg2 = new Message(MessageType.updateProductStock, OrderFrameController.productsList);
				ClientMenuController.clientControl.accept(msg2);
				msg3 = new Message(MessageType.addOrder, order);
				ClientMenuController.clientControl.accept(msg3);

			}
		} else { // if the costumer is not club member
			if ((this.location.equals("warehouse"))) { // if the order is delivery
				long millis = System.currentTimeMillis();
				// creating a new object of the class Date
				date = new java.sql.Date(millis);
				formatter = new SimpleDateFormat("MM-dd-yyyy");
				strDate = formatter.format(date);

				order = new Order(orderNum, OrderFrameController.machine, strDate, "placed", LoginFrameController.user.getUserID(),
						OrderFrameController.totPrice, "delivery", OrderFrameController.counterForProducts);
				order.setProducts(OrderFrameController.productsID);
				order.setQuantityPerProducts(OrderFrameController.productsQuantity);
				order.setProductsPrice(OrderFrameController.productsPrice);
				msg3 = new Message(MessageType.addOrder, order);
				ClientMenuController.clientControl.accept(msg3);
				long millis2 = System.currentTimeMillis();
				date2 = new java.sql.Date(millis2);
				formatter = new SimpleDateFormat("MM.dd.yyyy");
				strDate2 = formatter.format(date2);
				delivery = new OrderToDeliveryDetails(Integer.toString(orderNum), OnlineOrderFrameController.city,
						strDate2, "notAccept", "notDone");
				msg4 = new Message(MessageType.addDelivert, delivery);
				ClientMenuController.clientControl.accept(msg4);
			} else { // if order is from machine
				long millis = System.currentTimeMillis();
				date = new java.sql.Date(millis);
				formatter = new SimpleDateFormat("MM-dd-yyyy");
				strDate = formatter.format(date);
				String type;
				if (ClientMenuController.config.equals("OL")) { // if the order is pickup online
					type = "pickup";
				} else { // if EK configuration
					type = "local";
				}
				order = new Order(orderNum, OrderFrameController.machine, strDate, "placed", LoginFrameController.user.getUserID(),
						OrderFrameController.totPrice, type, OrderFrameController.counterForProducts);
				order.setProducts(OrderFrameController.productsID);
				order.setQuantityPerProducts(OrderFrameController.productsQuantity);
				order.setProductsPrice(OrderFrameController.productsPrice);
				vendingMachines = (ArrayList<VendingMachine>) ChatClient.msgServer.getMessageData();
				for (int i = 0; i < vendingMachines.size(); i++) {
					if (vendingMachines.get(i).getLocation().equals(this.location)) {
						for (int j = 0; j < OrderFrameController.productsList.size(); j++) {
							int thresholdLevel = Integer.parseInt(vendingMachines.get(i).getThresholdLevel());
							int stockQuantity = Integer
									.parseInt(OrderFrameController.productsList.get(j).getStockQuantity());
							if (stockQuantity <= thresholdLevel) {

								break;
							}
						}
						break;
					}
				}
				msg2 = new Message(MessageType.updateProductStock, OrderFrameController.productsList);

				ClientMenuController.clientControl.accept(msg2);

				msg3 = new Message(MessageType.addOrder, order);
				ClientMenuController.clientControl.accept(msg3);

			}
		}
		
		PaymentFrameController pay = new PaymentFrameController();
		try {
			pay.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void start(Stage customerStage) throws IOException {
		ClientMenuController.clientStage = customerStage;
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ConfirmOrderFrame.fxml"));
		ClientMenuController.clientStage.setTitle("Ekrut - Costumer >> Confirm Order");
		Scene home = new Scene(root);
		customerStage.setScene(home);
		// On pressing X (close window) the user logout from system and the client is
		// disconnect from server.
		customerStage.setOnCloseRequest(e -> {
			msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
			ClientMenuController.clientControl.accept(msg);
			ClientMenuController.clientControl
					.accept(new Message(MessageType.disconnected, LoginFrameController.user.getUserName()));
		});

		customerStage.show();
	}

	// Function to convert String to Float
	public static float convertStringToFloat(String str) {

		// Convert string to float
		// using valueOf() method
		return Float.valueOf(str);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// initialize the background image and Icon
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/InvoiceBackgroundFrame.png"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));

		tlbInvoice.setEditable(true);
		Image cartIcone = new Image("images/addToBasket.png");
		imgIcone.setImage(cartIcone);
		imgIcone.setFitWidth(50);
		imgIcone.setFitHeight(50);

		colImgProd.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, ImageView>("imgSrc"));
		colProdName.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, String>("productName"));
		colQuantityProd.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, String>("quantity"));
		colProdPrice.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, String>("price"));
		/*
		 * for(OrderProductsForTbl row : cartObservableList) {
		 * 
		 * //Image pic = new Image(row.getImgSrc()); ImageView img = new ImageView();
		 * img = row.getImgSrc(); img.setFitWidth(50); img.setFitHeight(50);
		 * 
		 * OrderProductsForTbl tempList = new
		 * OrderProductsForTbl(row.getProductName(),row.getPrice() ,row.getQuantity(),
		 * img, row.getBntToAdd(), row.getBntToSub()); }
		 */

		// TODO Auto-generated method stub
		tlbInvoice.setItems(OrderFrameController.cartObservableList);

		Time time = new Time("00:15:00");
		txtTimer.setText(time.getCurrentTime());
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {

			if (time.oneSecondPassed()) {
				
				toZero.setLabels();
				ClientMenuController.clientStage.setScene(LoginFrameController.home);
				// Logout
				msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
				ClientMenuController.clientControl.accept(msg);
			}
			txtTimer.setText(time.getCurrentTime());
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

}
