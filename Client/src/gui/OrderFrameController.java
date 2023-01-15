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

public class OrderFrameController implements Initializable {
	Order order;
	Product product;
	public static float totPrice;
	public static ArrayList<Product> productsList = new ArrayList<>();
	public static CustomerFrameController customerFrame;
	public static Stage clientStage;
	public static ConfirmOrderFrameController confirmOrderFrame;
	public static Message msg;
	public static ObservableList<ProductForOrder> tvObservableList = FXCollections.observableArrayList();
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
	int counterForProducts;

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
		lblWelcome.setText("Welcome " + LoginFrameController.user.getFirstName() + " " + LoginFrameController.user.getLastName());
		tblProducts.setEditable(true);
		tblCart.setEditable(true);
		Image cartIcone = new Image("images/addToBasket.png");
		imgForIcon.setImage(cartIcone);
		imgForIcon.setFitWidth(50);
		imgForIcon.setFitHeight(50);

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
			msg = new Message(MessageType.Show_products, OnlineOrderFrameController.machine);
			ClientMenuController.clientControl.accept(msg);
		} else {
			msg = new Message(MessageType.Show_products, ClientMenuController.vendingMachine);
			ClientMenuController.clientControl.accept(msg);
		}

		try {
			Thread.sleep(1000);
			System.out.println(ChatClient.msgServer.getMessageData().toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		productsList = (ArrayList<Product>) ChatClient.msgServer.getMessageData();
		for (Product row : productsList) {
			Image pic = new Image(row.getImgSrc());
			ImageView img = new ImageView();
			img.setImage(pic);
			img.setFitWidth(40);
			img.setFitHeight(40);
			Button addCartBnt = new Button("Add To Cart");
			addCartBnt.setOnAction(e -> {

				if (row.getStockQuantity().equals("0")) {

					System.out.println("alert for add to cart\n");
					stockAlert.setVisible(true);

				} else {
					stockAlert.setVisible(false);
					counterForProducts++;
					ImageView imgForCart = new ImageView();
					imgForCart.setImage(pic);
					imgForCart.setFitWidth(40);
					imgForCart.setFitHeight(40);
					if(!(row.getStockQuantity().equals("infinity")))
					{
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

						} else {
							stockAlert.setVisible(false);
							counterForProducts++;
							String tempQuantityStr = toCart.getQuantity();
							int tempQuantityNum = Integer.parseInt(tempQuantityStr);
							float tempPrice2 = convertStringToFloat(toCart.getPrice());
							tempQuantityNum = tempQuantityNum + 1;
							tempQuantityStr = String.valueOf(tempQuantityNum);
							if(!(row.getStockQuantity().equals("infinity")))
							{
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
							int stockNumTemp1 = Integer.parseInt(stockTempStr1);
							float tempPrice3 = convertStringToFloat(toCart.getPrice());
							if((!OnlineOrderFrameController.machine.equals("warehouse")))
							{
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
							if((!OnlineOrderFrameController.machine.equals("warehouse")))
							{
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
			tvObservableList.add(tempList);
		}

		tblProducts.setItems(tvObservableList);

		Time time = new Time("00:15:00");
		txtTimer.setText(time.getCurrentTime());
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {

			if (time.oneSecondPassed()) {
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

	@FXML
	void cancelOrder(ActionEvent event) {
		
		productsList.removeAll(productsList);
		tvObservableList.removeAll(tvObservableList);
		cartObservableList.removeAll(cartObservableList);
		counterForProducts = 0;
		lblTotalPrice.setText(null);
		tblProducts.setItems(null);
		tblCart.setItems(null);
		txtTimer.setText(null);
		
		ClientMenuController.clientStage.setScene(LoginFrameController.home);
		// Logout
		msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
		ClientMenuController.clientControl.accept(msg);
	}

	/**
	 * 
	 * 
	 * @param event - > costumer want to checkout order The button switch to new
	 *              frame -> invoice frame it sends costumer order to invoice to
	 *              update database
	 */
	@FXML
	void checkOutOrder(ActionEvent event) {
		confirmOrderFrame = new ConfirmOrderFrameController(tvObservableList, cartObservableList, "ortBraudeproducts",
				lblTotalPrice, counterForProducts);
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
