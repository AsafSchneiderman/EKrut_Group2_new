package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Entities.Message;
import Entities.MessageType;
import Entities.Product;
import Entities.VendingMachine;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

/**
 * The operations worker choose vending machine location for update the stock of his products.
 * update the stock of each vending machine in his region
 * @author Nofar Ben Simon
 *
 */
public class UpdateStockFrameController implements Initializable{

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<Product> tblViewProducts;

    @FXML
    private TableColumn<Product, String> productIDCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, String> priceCol;

    @FXML
    private TableColumn<Product, String> stockQuantityCol;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnUpdateStock;

    @FXML
    private ComboBox<String> cmbBoxVendingMachine;

    @FXML
    private Button btnShowProducts;
    
    @FXML
    private Label lblMsg1;

    @FXML
    private Label lblMsg2;

    @FXML
    private Label lblMsg3;
    
    @FXML
	private Label lblAlert;
	
	private static ArrayList<VendingMachine> vendingMachines = new ArrayList<>(); // list of vending machines in the DB

	private static ArrayList<Product> products = new ArrayList<>(); // list of products in the DB
	
	private static String location = new String();	//the location of the vending machine to stock


    /**
   	 * Goes back to the previous window of OperationsWorkerFrameController
   	 * 
   	 * @param event (Click on Back button)
   	 */
   	@FXML
   	void backToPreviousPage(ActionEvent event) {
   		
   		// get the messages of the region manager
   		ClientMenuController.clientControl
   				.accept(new Message(MessageType.Get_messages, LoginFrameController.user.getUserID()));
   		OperationsWorkerFrameController OperationsWorkerFrame = new OperationsWorkerFrameController();
   		try {
   			OperationsWorkerFrame.start(ClientMenuController.clientStage);
   		} catch (IOException e) {
   			e.printStackTrace();
   		}

   	}

   	/**
   	 * call to read products from DB and show in the table the products of the selected combo box location
   	 * @param event (Click on Show Products button)
   	 */
    @FXML
    void showProducts(ActionEvent event) {
    	location = cmbBoxVendingMachine.getValue();
    	System.out.println(location);   	/////////////////////////////////////////
    	ClientMenuController.clientControl.accept(new Message(MessageType.Show_products, location));
    	lblMsg1.setVisible(true);
		lblMsg2.setVisible(true);
		lblMsg3.setVisible(true);
		tblViewProducts.setVisible(true);
		btnUpdateStock.setVisible(true);

		// initialize the products table from DB
		tblViewProducts.setEditable(true);

	    productIDCol.setCellValueFactory(new PropertyValueFactory<Product, String>("productID"));
	    productNameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
	    priceCol.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
	    stockQuantityCol.setCellValueFactory(new PropertyValueFactory<Product, String>("stockQuantity"));
		
	    ObservableList<Product> tvObservableList = FXCollections.observableArrayList();
	    
	    try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		products = (ArrayList<Product>) ChatClient.msgServer.getMessageData();
		for (Product row : products)
			//if (row.getRegion().equals(LoginFrameController.user.getRegion())) // show the vending machines at his
																				// region
				tvObservableList.add(row);

		tblViewProducts.setItems(tvObservableList);

		// Open the option to update the stock quantity on the table
		stockQuantityCol.setCellFactory(TextFieldTableCell.forTableColumn());
		stockQuantityCol.setOnEditCommit(new EventHandler<CellEditEvent<Product, String>>() {
			// A method that handles the stock quantity update changes in the table
			@Override
			public void handle(CellEditEvent<Product, String> event) {
				lblAlert.setText("");
				lblAlert.setStyle("");
				Product p = event.getRowValue();
				p.setStockQuantity(event.getNewValue());
				for (Product row : products)
					//if (row.getRegion().equals(LoginFrameController.user.getRegion())) // update the vending machines at
																						// his region
						if (p.getProductID().equals(row.getProductID()))
							row.setStockQuantity(p.getStockQuantity());
			}
		});
    }

    /**
	 * update the stock of the vending machines products in the DB
	 * 
	 * @param event (Click on Update Stock button)
	 */
    @FXML
    void updateStock(ActionEvent event) {
    	lblAlert.setText("The stock updated in DB"); // show update Alert
		lblAlert.setStyle("-fx-background-color:#73bce4");
		ClientMenuController.clientControl.accept(new Message(MessageType.updateProductStock, products)); 
		
		 try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		 /*
		//update the restock status to "Done"
		for (VendingMachine row : vendingMachines)
			if(row.getLocation().equals(location) && row.getRestockStatus().equals("WaitToRestock"))
				row.setRestockStatus("Done");
		ClientMenuController.clientControl.accept(new Message(MessageType.update_restockStatus, vendingMachines)); 
		*/
    }
    
    /**
	 * start the UpdateStockFrame
	 * 
	 * @param primaryStage
	 * @throws IOException
	 */
	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Operations Worker >> Update Stock");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/UpdateStockFrame.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);

		// On pressing X (close window) the user logout from system and the client is
		// disconnect from server.
		primaryStage.setOnCloseRequest(e -> {
			ClientMenuController.clientControl.accept(new Message(MessageType.logout, LoginFrameController.user.getUserName()));
			ClientMenuController.clientControl
					.accept(new Message(MessageType.disconnected, LoginFrameController.user.getUserName()));
			// create a PopUp message
			PopUpMessageFrameController popUpMsgController = new PopUpMessageFrameController();
			try {
				popUpMsgController.start(ClientMenuController.clientStage);
				popUpMsgController.closeMsg(3000);

			} catch (IOException e1) {
				e1.printStackTrace();

			}
		});
		primaryStage.show();
	}

	/**
	 * initialize parameters when the frame start
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// initialize the background image
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/UpdateStockFrame.png"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));

		lblMsg1.setVisible(false);
		lblMsg2.setVisible(false);
		lblMsg3.setVisible(false);
		tblViewProducts.setVisible(false);
		btnUpdateStock.setVisible(false);
		
		//combo box
		// show the vending machines
		vendingMachines = (ArrayList<VendingMachine>) ChatClient.msgServer.getMessageData();
		ObservableList<String> list = FXCollections.observableArrayList(); // initialize the comboBox
		for (VendingMachine row : vendingMachines)
			list.add(row.getLocation());

		cmbBoxVendingMachine.setItems(list);
		cmbBoxVendingMachine.setValue(list.get(0));
	}

}
