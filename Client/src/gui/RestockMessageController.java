package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Entities.Message;
import Entities.MessageType;
import Entities.VendingMachine;
import Entities.WorkerMessage;
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
 * When the manager receives a message that the status of the vending machine is
 * 'LowStock', the region manager sends a restock message to the operations
 * worker per vending machine.
 * 
 * @author Nofar Ben Simon
 *
 */
public class RestockMessageController implements Initializable {

	@FXML
	private AnchorPane pane;

	@FXML
	private TableView<VendingMachine> tblViewVendingMachines;

	@FXML
	private TableColumn<VendingMachine, String> regionCol;

	@FXML
	private TableColumn<VendingMachine, String> locationCol;

	@FXML
	private TableColumn<VendingMachine, String> thresholdLevelCol;

	@FXML
	private TableColumn<VendingMachine, String> statusCol;

	@FXML
	private TableColumn<VendingMachine, String> restockCol;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnUpdateRestockStatus;

	@FXML
	private Label lblAlert;

	private static Message msg; // message to send to server

	private static ArrayList<VendingMachine> vendingMachines = new ArrayList<>(); // list of vending machines in the DB

	/**
	 * Goes back to the previous window of RegionManagerFrameController
	 * 
	 * @param event (Click on Back button)
	 */
	@FXML
	void backToPreviousPage(ActionEvent event) {
		
		// close the buttons
		for (VendingMachine row : vendingMachines)
			if(row.getRegion().equals(LoginFrameController.user.getRegion())) //the vending machines at his region 
				row.buttonClose();
		// get the messages of the region manager
		ClientMenuController.clientControl
				.accept(new Message(MessageType.Get_workerMessages, LoginFrameController.user.getUserID()));
		RegionManagerFrameController RegionManagerController = new RegionManagerFrameController();
		try {
			RegionManagerController.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * start the RestockMessageFrame
	 * 
	 * @param primaryStage
	 * @throws IOException
	 */
	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Region Manager >> Menu >> Restock Messages");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/RestockMessage.fxml"));
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
		BackgroundImage image = new BackgroundImage(new Image("images/RestockMessageFrame.png"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));

		// initialize the vending machines table from DB
		tblViewVendingMachines.setEditable(true);
		
		regionCol.setCellValueFactory(new PropertyValueFactory<VendingMachine, String>("region"));
		locationCol.setCellValueFactory(new PropertyValueFactory<VendingMachine, String>("location"));
		thresholdLevelCol.setCellValueFactory(new PropertyValueFactory<VendingMachine, String>("thresholdLevel"));
		statusCol.setCellValueFactory(new PropertyValueFactory<VendingMachine, String>("restockStatus"));
		restockCol.setCellValueFactory(new PropertyValueFactory<VendingMachine, String>("btnRestock")); // 'Restock'

		ObservableList<VendingMachine> tvObservableList = FXCollections.observableArrayList();
		vendingMachines = (ArrayList<VendingMachine>) ChatClient.msgServer.getMessageData();
		for (VendingMachine row : vendingMachines) {
			if (row.getRegion().equals(LoginFrameController.user.getRegion())) { // show the vending machines at his
																					// region
				row.buttonInitialize();
				row.getBtnRestock().setOnAction((ActionEvent event) -> {
					VendingMachine v = new VendingMachine(row.getRegion(), row.getLocation(), row.getThresholdLevel(),
							"WaitToRestock");
					lblAlert.setText("A restock message sent to the worker"); // show update Alert
					lblAlert.setStyle("-fx-background-color:#73bce4");
			
					 ClientMenuController.clientControl.accept(new Message(MessageType.update_restockStatusToWaitToRestock, v));
					row.getBtnRestock().setDisable(true);														
					// find the operation worker
					ClientMenuController.clientControl
							.accept(new Message(MessageType.get_operationWorkerByRegion, LoginFrameController.user.getRegion()));
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// create message to insert to worker messages
					WorkerMessage m = new WorkerMessage(0, (String) ChatClient.msgServer.getMessageData(),"The vending machine in "+row.getLocation() +" wait to restock", "notRead");
					ClientMenuController.clientControl.accept(new Message(MessageType.insert_WorkerMessages, m));
					});
				if (!row.getRestockStatus().equals("LowStock"))
					row.getBtnRestock().setDisable(true);
				tvObservableList.add(row);
			}
		}

		tblViewVendingMachines.setItems(tvObservableList);

	}

}