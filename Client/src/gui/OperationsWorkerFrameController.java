package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

/**
 * This frame is for Operations Worker:
 * 1. View vending machines details
 * 2. Update vending machines stock
 * @author Nofar Ben Simon
 *
 */
public class OperationsWorkerFrameController implements Initializable{

    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblWelcome;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnUpdateStock;

    @FXML
    private Button btnViewVendingMachines;
    
    private static Message msg; // message to send to service

    /**
     * open View Vending Machines Frame
     * @param event (Click on View Vending Machines Details button)
     */
    @FXML
    void ViewVendingMachinesDetails(ActionEvent event) {

    	ViewVendingMachinesFrameController ViewVendingMachinesFrame = new ViewVendingMachinesFrameController();
		try {
			// Create message to send to server
			msg = new Message(MessageType.Get_vendingMachines, "");
			ClientMenuController.clientControl.accept(msg);
			ViewVendingMachinesFrame.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	/**
	 * The user exit from the region manager frame and do logout to the user from
	 * the DB
	 * 
	 * @param event (Click on Exit button)
	 */
	@FXML
	void exit(ActionEvent event) {

		ClientMenuController.clientStage.setScene(LoginFrameController.home);
		// Logout
		msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
		ClientMenuController.clientControl.accept(msg);

	}

    @FXML
    void updateStocks(ActionEvent event) {

    }
    
	/**
	 * PopUp the messages of the region manager from the DB
	 */
	public void popUpMessages() {
		// popup messages from the DB
		String message = (String) ChatClient.msgServer.getMessageData();
		if (!message.equals("")) {

			Alert a = new Alert(AlertType.INFORMATION);

			// set title
			a.setTitle("EKRUT Messages");
			// set header text
			a.setHeaderText("You have new messages");

			// set content text
			a.setContentText(message);

			// show the dialog
			Optional<ButtonType> result = a.showAndWait();
			if (result.get() == ButtonType.OK)
				// update the messages status of the region manager to read
				ClientMenuController.clientControl
						.accept(new Message(MessageType.update_messagesStatus, LoginFrameController.user.getUserID()));
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
		primaryStage.setTitle("Ekrut - Operations Worker >> Menu");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/OperationsWorkerFrame.fxml"));
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
		this.popUpMessages(); // show new messages
	}

	/**
	 * initialize parameters when the frame start
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// initialize the background image
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/OperationsWorkerFrame.png"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));

		// initialize the Welcome label to welcome and the full name of the user
		lblWelcome.setText(
				"Welcome " + LoginFrameController.user.getFirstName() + " " + LoginFrameController.user.getLastName());
	}

}
