package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import Entities.*;
import controller.ChatClient;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * in this frame the customer picks the products that he wants to buy
 * 
 * @author Marina
 * 
 *
 */
public class OrderFrameController implements Initializable {
	Order order;
	Product product;
	public static PromotionSells discount;
	public static ArrayList<PromotionSells> discountList = new ArrayList<>();
	public static ArrayList<ClubMember> clubMemberList = new ArrayList<>();
	public static float totPrice;
	public static ArrayList<Product> productsList = new ArrayList<>();
	public static CustomerFrameController customerFrame;
	public static Stage clientStage;
	public static ConfirmOrderFrameController confirmOrderFrame;
	public static Message msg, msg2, msg3, msg4, msg5;
	public static ObservableList<ProductForOrder> ProductsObservableList = FXCollections.observableArrayList();
	public static ObservableList<OrderProductsForTbl> cartObservableList = FXCollections.observableArrayList();
	// public static ObservableList<String>tempForProducts;

	@FXML
	private AnchorPane pane;
	@FXML
	private TableView<ProductForOrder> tblProducts;

	@FXML
	private TableView<OrderProductsForTbl> tblCart;
	@FXML
	private TableColumn<OrderProductsForTbl, ImageView> imgSelectedProdCol;
	@FXML
	private TableColumn<OrderProductsForTbl, String> colProductInCart;

	@FXML
	private TableColumn<OrderProductsForTbl, String> colQuantityInCart;

	@FXML
	private TableColumn<OrderProductsForTbl, Button> addProdBntCol;

	@FXML
	private TableColumn<OrderProductsForTbl, Button> subProdBntCol;

	@FXML
	private TableColumn<OrderProductsForTbl, String> priceSelProdCol;

	@FXML
	private Button btnCheckOutOrder;

	@FXML
	private Label lblTotalPrice;

	@FXML
	private Button btnCancelOrder;

	@FXML
	private TableColumn<ProductForOrder, String> colNameOfProduct;

	@FXML
	private TableColumn<ProductForOrder, String> colPriceOfProduct;

	@FXML
	private TableColumn<ProductForOrder, ImageView> colProductImg;
	@FXML
	private TableColumn<ProductForOrder, String> bntColAddCart;
	@FXML
	private Label stockAlert;
	@FXML
	private ImageView imgForIcon;
	@FXML
	private Text txtTimer;
	@FXML
	private Label lblWelcome;
	@FXML
	private Label lblDiscount;
	public static int counterForProducts;
	public static String productsID;
	public static String productsPrice;
	public static String productsQuantity;
	public static String machine;
	public static String finalPrice;
	public static String region;
	public static String discountVar = "0";
	private static ArrayList<VendingMachine> vendingMachines = new ArrayList<>(); // list of vending machines in the DB

	/**
	 * 
	 * initialize parameters when the frame start initialize tables and images and
	 * buttons the buttons that responsible for add to cart and add to quantity and
	 * sub from it also initialize timer
	 */

	@SuppressWarnings({ "unchecked" })
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		totPrice = 0;
		stockAlert.setVisible(false);
		counterForProducts = 0;
		// initialize the background image and icon
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/orderFrameBackground.png"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));
		// initialize the Welcome label to welcome and the full name of the user
		lblWelcome.setText(
				"Welcome " + LoginFrameController.user.getFirstName() + " " + LoginFrameController.user.getLastName());
		lblDiscount.setVisible(false);
		tblProducts.setEditable(true);
		tblCart.setEditable(true);
		Image cartIcone = new Image("images/addToBasket.png");
		imgForIcon.setImage(cartIcone);
		imgForIcon.setFitWidth(50);
		imgForIcon.setFitHeight(50);
		// Create message to send to server

		colProductImg.setCellValueFactory(new PropertyValueFactory<ProductForOrder, ImageView>("imgSrc"));
		colNameOfProduct.setCellValueFactory(new PropertyValueFactory<ProductForOrder, String>("productName"));
		colPriceOfProduct.setCellValueFactory(new PropertyValueFactory<ProductForOrder, String>("price"));
		bntColAddCart.setCellValueFactory(new PropertyValueFactory<ProductForOrder, String>("bntToAdd"));

		imgSelectedProdCol.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, ImageView>("imgSrc"));
		colProductInCart.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, String>("productName"));
		colQuantityInCart.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, String>("quantity"));
		addProdBntCol.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, Button>("bntToAdd"));
		subProdBntCol.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, Button>("bntToSub"));
		priceSelProdCol.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, String>("price"));
		if (ClientMenuController.config.equals("OL")) {
			machine = OnlineOrderFrameController.machine;
			msg = new Message(MessageType.Show_products, OnlineOrderFrameController.machine);
			ClientMenuController.clientControl.accept(msg);
		} else {// EK configuration
			machine = ClientMenuController.vendingMachine;
			msg = new Message(MessageType.Show_products, ClientMenuController.vendingMachine);
			ClientMenuController.clientControl.accept(msg);
		}

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		productsList = (ArrayList<Product>) ChatClient.msgServer.getMessageData();
		msg5 = new Message(MessageType.Get_vendingMachines, "");
		ClientMenuController.clientControl.accept(msg5);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		vendingMachines = (ArrayList<VendingMachine>) ChatClient.msgServer.getMessageData();
		for (VendingMachine row : vendingMachines) {
			if (machine.equals(row.getLocation())) {
				region = row.getRegion();
			}
		}

		if (LoginFrameController.user.getRole().equals("ClubMember")) {
			msg2 = new Message(MessageType.getPromtion, "");
			ClientMenuController.clientControl.accept(msg2);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			discountList = (ArrayList<PromotionSells>) ChatClient.msgServer.getMessageData();

			msg3 = new Message(MessageType.showNewClubMebers, "");
			int flagForNoDoubleDis = 1;
			ClientMenuController.clientControl.accept(msg3);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clubMemberList = (ArrayList<ClubMember>) ChatClient.msgServer.getMessageData();
			if (flagForNoDoubleDis == 1) {
				for (int j = 0; j < clubMemberList.size(); j++) {
					if (clubMemberList.get(j).getUserID().equals(LoginFrameController.user.getUserID())) {
						if (clubMemberList.get(j).getIsNew() == 1) {
							discountVar = "20";
							lblDiscount.setVisible(true);
							lblDiscount.setText("You Have %" + discountVar + " Discount!!");
							flagForNoDoubleDis = 0;
							clubMemberList.get(j).setIsNew(0);
							msg4 = new Message(MessageType.changeClubMemberStatus, clubMemberList.get(j));
							ClientMenuController.clientControl.accept(msg4);

						}
					}
				}
			}
			if (flagForNoDoubleDis == 1) {
				for (int i = 0; i < discountList.size(); i++) {
					if (discountList.get(i).getRegion().equals(region)
							&& discountList.get(i).getActivated().equals("activate")) {
						lblDiscount.setVisible(true);
						lblDiscount.setText("You Have %" + discountList.get(i).getPromotion() + " Discount!!");
						discount = discountList.get(i);
						discountVar = discountList.get(i).getPromotion();
					}
				}
			}

		}

		for (Product row : productsList) {
			Image pic = new Image(row.getImgSrc());
			ImageView img = new ImageView();
			img.setImage(pic);
			img.setFitWidth(40);
			img.setFitHeight(40);
			Button addCartBnt = new Button("Add To Cart");
			addCartBnt.setOnAction(e -> {

				if (row.getStockQuantity().equals("0")) {

					stockAlert.setVisible(true);
					stockAlert.setStyle("-fx-background-color:#73bce4");

				} else {
					stockAlert.setVisible(false);
					counterForProducts++;
					ImageView imgForCart = new ImageView();
					imgForCart.setImage(pic);
					imgForCart.setFitWidth(40);
					imgForCart.setFitHeight(40);
					if (!(row.getStockQuantity().equals("infinity"))) {
						String stockTempStr = row.getStockQuantity();
						int stockNumTemp = Integer.parseInt(stockTempStr);
						stockNumTemp = stockNumTemp - 1;
						stockTempStr = String.valueOf(stockNumTemp);
						row.setStockQuantity(stockTempStr);
					}

					Button addQuantity = new Button("+");
					Button subQuantity = new Button("-");
					OrderProductsForTbl toCart = new OrderProductsForTbl(row.getProductName(), row.getPrice(), "1",
							imgForCart, addQuantity, subQuantity);
					float tempPrice = convertStringToFloat(row.getPrice());
					totPrice = totPrice + tempPrice;
					lblTotalPrice.setText(Float.toString(totPrice));
					addQuantity.setOnAction(a -> {

						if (row.getStockQuantity().equals("0")) {

							stockAlert.setVisible(true);
							stockAlert.setStyle("-fx-background-color:#73bce4");

						} else {
							stockAlert.setVisible(false);
							counterForProducts++;
							String tempQuantityStr = toCart.getQuantity();
							int tempQuantityNum = Integer.parseInt(tempQuantityStr);
							float tempPrice2 = convertStringToFloat(toCart.getPrice());
							tempQuantityNum = tempQuantityNum + 1;
							tempQuantityStr = String.valueOf(tempQuantityNum);
							if (!(row.getStockQuantity().equals("infinity"))) {
								String stockTempStr2 = row.getStockQuantity();
								int stockNumTemp2 = Integer.parseInt(stockTempStr2);
								stockNumTemp2 = stockNumTemp2 - 1;
								stockTempStr2 = String.valueOf(stockNumTemp2);
								row.setStockQuantity(stockTempStr2);
							}

							totPrice = totPrice + tempPrice2;
							lblTotalPrice.setText(Float.toString(totPrice));
							toCart.setQuantity(tempQuantityStr);
							tblCart.refresh();

						}

					});

					subQuantity.setOnAction(b -> {

						if (Integer.parseInt(toCart.getQuantity()) == 1) {
							counterForProducts--;
							String stockTempStr1 = row.getStockQuantity();

							float tempPrice3 = convertStringToFloat(toCart.getPrice());
							if (!(machine.equals("warehouse"))) {
								int stockNumTemp1 = Integer.parseInt(stockTempStr1);
								stockNumTemp1 = stockNumTemp1 + 1;
								stockTempStr1 = String.valueOf(stockNumTemp1);
								row.setStockQuantity(stockTempStr1);
							}

							totPrice = totPrice - tempPrice3;
							lblTotalPrice.setText(Float.toString(totPrice));
							cartObservableList.remove(toCart);
						} else {
							counterForProducts--;
							String tempQuantityStr = toCart.getQuantity();
							int tempQuantityNum = Integer.parseInt(tempQuantityStr);
							float tempPrice4 = convertStringToFloat(toCart.getPrice());
							tempQuantityNum = tempQuantityNum - 1;
							tempQuantityStr = String.valueOf(tempQuantityNum);
							if (!(machine.equals("warehouse"))) {
								String stockTempStr2 = row.getStockQuantity();
								int stockNumTemp2 = Integer.parseInt(stockTempStr2);
								stockNumTemp2 = stockNumTemp2 + 1;
								stockTempStr2 = String.valueOf(stockNumTemp2);
								row.setStockQuantity(stockTempStr2);
							}

							totPrice = totPrice - tempPrice4;
							lblTotalPrice.setText(Float.toString(totPrice));
							toCart.setQuantity(tempQuantityStr);
							tblCart.refresh();
						}

					});

					cartObservableList.add(toCart);
				}

			});
			tblCart.setItems(cartObservableList);
			ProductForOrder tempList = new ProductForOrder(row.getProductName(), row.getPrice(), img, addCartBnt);
			ProductsObservableList.add(tempList);
		}

		tblProducts.setItems(ProductsObservableList);

		Time time = new Time("00:15:00");
		txtTimer.setText(time.getCurrentTime());
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {

			if (time.oneSecondPassed()) {

				setZero();
				lblTotalPrice.setText(null);
				tblProducts.setItems(null);
				tblCart.setItems(null);
				txtTimer.setText(null);
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

	/**
	 * cancel the order erased all data from vars and get to login frame
	 * 
	 * @param event - click cancel button
	 */
	@FXML
	void cancelOrder(ActionEvent event) {

		setZero();
		tblProducts.setItems(null);
		tblCart.setItems(null);
		txtTimer.setText(null);
		lblTotalPrice.setText(null);
		ClientMenuController.clientStage.setScene(LoginFrameController.home);
		// Logout
		msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
		ClientMenuController.clientControl.accept(msg);
	}

	/***
	 * if order is canceled for some reason erase data from tables, list etc..
	 */

	public void setZero() {
		productsList.removeAll(productsList);
		ProductsObservableList.removeAll(ProductsObservableList);
		cartObservableList.removeAll(cartObservableList);
		counterForProducts = 0;
	}

	/**
	 * 
	 * open invoice frame and sum the order for the user
	 * 
	 * @param event - click on the button "Get Order"
	 */
	@FXML
	void checkOutOrder(ActionEvent event) {
		tblProducts.setItems(null);
		tblCart.setItems(null);
		txtTimer.setText(null);
		lblTotalPrice.setText(null);
		for (int i = 0; i < cartObservableList.size(); i++) {
			for (int j = 0; j < productsList.size(); j++) {
				
				if (cartObservableList.get(i).getProductName().equals(productsList.get(j).getProductName())) {
					
					if (productsID == null) {

						productsID = productsList.get(j).getProductID();
						productsPrice = productsList.get(j).getPrice();
						productsQuantity = cartObservableList.get(i).getQuantity();

					} else {
						productsID.concat(",");
						productsID.concat(productsList.get(j).getProductID());
						productsPrice.concat(",");
						productsPrice.concat(productsList.get(j).getPrice());
						productsQuantity.concat(",");
						productsQuantity.concat(cartObservableList.get(i).getQuantity());

					}
					break;
				}
			}
		}
		finalPrice = String.valueOf(totPrice);
		confirmOrderFrame = new ConfirmOrderFrameController();
		try {
			confirmOrderFrame.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Function to convert String to Float
	public static float convertStringToFloat(String str) {

		// Convert string to float
		// using valueOf() method
		return Float.valueOf(str);
	}

	/**
	 * start the OrderFrame
	 * 
	 * @param primaryStage
	 * @throws IOException
	 */
	public void start(Stage customerStage) throws IOException {
		ClientMenuController.clientStage = customerStage;
		ClientMenuController.clientStage.setTitle("Ekrut - Costumer >> Make an Order");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/OrderFrame.fxml"));
		Scene home = new Scene(root);
		customerStage.setScene(home);
		// On pressing X (close window) the user logout from system and the client is
		// disconnect from server
		customerStage.setOnCloseRequest(e -> {
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
		customerStage.show();
	}

}
