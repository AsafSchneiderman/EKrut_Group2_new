package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.Message;
import Entities.MessageType;
import controller.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

public class RegionManagerFrameController implements Initializable {

	@FXML
	private AnchorPane pane;

	@FXML
	private Label lblWelcome;

	@FXML
	private Button btnUpdateThresholdLevel;

	@FXML
	private Button btnExit;

	@FXML
	private Button btnRestockMessage;

	@FXML
	private Button btnViewReports;

	private static Message msg; // message to send to service
	public static String region = null; // the region of this region manager

	/**
	 * open Restock Message Frame
	 * 
	 * @param event (Click on Update Threshold Level button)
	 */
	@FXML
	void restockMessageToWorker(ActionEvent event) {

		RestockMessageController restockMessageController = new RestockMessageController();
		try {
			// Create message to send to server
			msg = new Message(MessageType.Get_vendingMachines, "");
			ClientMenuController.clientControl.accept(msg);
			restockMessageController.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * The user exit from the system and the system do logout to the user from the
	 * DB
	 * 
	 * @param event (Click on Exit button)
	 */
	@FXML
	void exit(ActionEvent event) {

		// Create message
		msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
		ClientMenuController.clientControl.accept(msg);
		ClientMenuController.clientStage.close();

	}

	/**
	 * open Threshold Level Frame
	 * 
	 * @param event (Click on Update Threshold Level button)
	 */
	@FXML
	void updateThresholdLevel(ActionEvent event) {
		ThresholdLevelFrameController ThresholdLevelController = new ThresholdLevelFrameController();
		try {
			// Create message to send to server
			msg = new Message(MessageType.Get_vendingMachines, "");
			ClientMenuController.clientControl.accept(msg);
			ThresholdLevelController.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** Open report search frame **/
	@FXML
	void viewReports(ActionEvent event) {
		ReportSearchFrameController reportSearchFrameController = new ReportSearchFrameController();
		try {
			reportSearchFrameController.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * start the RegionManagerFrame
	 * 
	 * @param primaryStage
	 * @throws IOException
	 */
	public void start(Stage primaryStage) throws IOException {

		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Region Manager >> Menu");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/RegionManagerFrame.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);

		// On pressing X (close window) the user logout from system and the client is
		// disconnect from server.
		primaryStage.setOnCloseRequest(e -> {
			msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
			ClientMenuController.clientControl.accept(msg);
			// ClientMenuController.clientControl.accept(new
			// Message(MessageType.disconnected,LoginFrameController.user.getUserName()));
		});

		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// initialize the background image
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/RegionManagerFrame.png"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));

		// initialize the Welcome label to welcome and the full name of the user
		lblWelcome.setText(
				"Welcome " + LoginFrameController.user.getFirstName() + " " + LoginFrameController.user.getLastName());

		//get the region of the region manager
		//ClientMenuController.clientControl.accept(new Message(MessageType.Get_region, LoginFrameController.user.getUserID()));
		//try {
		//	Thread.sleep(500);
		//} catch (InterruptedException e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		//region = (String) ChatClient.msgServer.getMessageData();

	}

}
