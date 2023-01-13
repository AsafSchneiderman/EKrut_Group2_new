package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import Entities.*;
import controller.ChatClient;
import entity.OrderToDeliveryDetails;
import entity.Subscriber;
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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class ThresholdLevelFrameController implements Initializable {

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
	private Button btnBack;

	@FXML
	private Button btnUpdateThresholdLevel;

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

		RegionManagerFrameController RegionManagerController = new RegionManagerFrameController();
		try {
			RegionManagerController.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * update the threshold level of the vending machines in the DB
	 * 
	 * @param event (Click on Update Threshold Level button)
	 */
	@FXML
	void updateThresholdLevel(ActionEvent event) {

		lblAlert.setText("Threshold level updated in DB"); // show update Alert
		lblAlert.setStyle("-fx-background-color:white");
		msg = new Message(MessageType.update_thresholdLevel, vendingMachines);
		ClientMenuController.clientControl.accept(msg);
	}

	/**
	 * start the ThresholdLevelFrame
	 * 
	 * @param primaryStage
	 * @throws IOException
	 */
	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Region Manager >> Threshold Level");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ThresholdLevelFrame.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);

		// On pressing X (close window) the user logout from system and the client is
		// disconnect from server.
		primaryStage.setOnCloseRequest(e -> {
			msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
			ClientMenuController.clientControl.accept(msg);
			ClientMenuController.clientControl
					.accept(new Message(MessageType.disconnected, LoginFrameController.user.getUserName()));
		});
		primaryStage.show();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// initialize the background image
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/ThresholdLevelFrame.png"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));

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
