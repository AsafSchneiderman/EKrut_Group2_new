package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Entities.Message;
import Entities.MessageType;
import Entities.VendingMachine;
import controller.ChatClient;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

/**
 * show the vending machines that wait to restock.
 * 
 * @author Nofar Ben Simon
 *
 */
public class ViewVendingMachinesFrameController implements Initializable {

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
	private Button btnBack;

	@FXML
	private Label lblAlert;

	private static Message msg; // message to send to server

	private static ArrayList<VendingMachine> vendingMachines = new ArrayList<>(); // list of vending machines in the DB

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
	 * start the OperationsWorkerFrame
	 * 
	 * @param primaryStage
	 * @throws IOException
	 */
	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Operations Worker >> Vending Machines to restock");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ViewVendingMachinesFrame.fxml"));
		Scene home = new Scene(root);
		//home.getStylesheets().add(getClass().getResource("tableDesign.css").toExternalForm());
		primaryStage.setScene(home);
		primaryStage.show();
		
		
		
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
		
	}

	/**
	 * initialize parameters when the frame start
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// initialize the background image
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/VendingMachinesFrame.png"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));

		// initialize the vending machines table from DB
		tblViewVendingMachines.setEditable(true);

		regionCol.setCellValueFactory(new PropertyValueFactory<VendingMachine, String>("region"));
		locationCol.setCellValueFactory(new PropertyValueFactory<VendingMachine, String>("location"));
		thresholdLevelCol.setCellValueFactory(new PropertyValueFactory<VendingMachine, String>("thresholdLevel"));
		statusCol.setCellValueFactory(new PropertyValueFactory<VendingMachine, String>("restockStatus"));

		ObservableList<VendingMachine> tvObservableList = FXCollections.observableArrayList();
		vendingMachines = (ArrayList<VendingMachine>) ChatClient.msgServer.getMessageData();
		for (VendingMachine row : vendingMachines) {
			if (row.getRestockStatus().equals("WaitToRestock"))
				tvObservableList.add(row);
		}

		tblViewVendingMachines.setItems(tvObservableList);
	}

}
