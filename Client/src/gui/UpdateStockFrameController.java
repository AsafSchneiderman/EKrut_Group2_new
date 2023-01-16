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
	private Label lblAlert;

	private static Message msg; // message to send to server
	
	private static ArrayList<VendingMachine> vendingMachines = new ArrayList<>(); // list of vending machines in the DB

	private static ArrayList<Product> products = new ArrayList<>(); // list of products in the DB


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

    @FXML
    void showProducts(ActionEvent event) {

    }

    @FXML
    void updateStock(ActionEvent event) {

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

		
		cmbBoxVendingMachine.setVisible(true);
		// show the vending machines
		vendingMachines = (ArrayList<VendingMachine>) ChatClient.msgServer.getMessageData();
		ObservableList<String> list = FXCollections.observableArrayList(); // initialize the comboBox
		for (VendingMachine row : vendingMachines)
			list.add(row.getLocation());

		cmbBoxVendingMachine.setItems(list);
		cmbBoxVendingMachine.setValue(list.get(0));
		
		
		
		
		// initialize the vending machines table from DB
		tblViewVendingMachines.setEditable(true);

		regionCol.setCellValueFactory(new PropertyValueFactory<VendingMachine, String>("region"));
		locationCol.setCellValueFactory(new PropertyValueFactory<VendingMachine, String>("location"));
		thresholdLevelCol.setCellValueFactory(new PropertyValueFactory<VendingMachine, String>("thresholdLevel"));

		ObservableList<VendingMachine> tvObservableList = FXCollections.observableArrayList();
		vendingMachines = (ArrayList<VendingMachine>) ChatClient.msgServer.getMessageData();
		for (VendingMachine row : vendingMachines)
			if (row.getRegion().equals(LoginFrameController.user.getRegion())) // show the vending machines at his
																				// region
				tvObservableList.add(row);

		tblViewVendingMachines.setItems(tvObservableList);

		// Open the option to update the threshold level on the table
		thresholdLevelCol.setCellFactory(TextFieldTableCell.forTableColumn());
		thresholdLevelCol.setOnEditCommit(new EventHandler<CellEditEvent<VendingMachine, String>>() {
			// A method that handles the threshold level update changes in the table
			@Override
			public void handle(CellEditEvent<VendingMachine, String> event) {
				lblAlert.setText("");
				lblAlert.setStyle("");
				VendingMachine ven = event.getRowValue();
				ven.setThresholdLevel(event.getNewValue());
				for (VendingMachine row : vendingMachines)
					if (row.getRegion().equals(LoginFrameController.user.getRegion())) // update the vending machines at
																						// his region
						if (ven.getLocation().equals(row.getLocation()))
							row.setThresholdLevel(ven.getThresholdLevel());
			}
		});

	}

}
