package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.Message;
import Entities.MessageType;
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

/**
 * This frame is for CEO worker and he manage the work by all regions.
 * 
 * 1.View reports
 * 
 * @author Nofar Ben Simon
 * 
 */

public class CEOFrameController implements Initializable {

	@FXML
	private AnchorPane pane;

	@FXML
	private Label lblWelcome;

	@FXML
	private Button btnExit;

	@FXML
	private Button btnViewReports;

	private static Message msg; // message to send to service
	public static String region = null; // the region of this CEO

	/**
	 * The user exit from the CEO frame and do logout to the user from the DB
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
	 * start the CEOFrame
	 * 
	 * @param primaryStage
	 * @throws IOException
	 */
	public void start(Stage primaryStage) throws IOException {

		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - CEO >> Menu");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/CEOFrame.fxml"));
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
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// initialize the background image
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/CEOFrame.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));

		// initialize the Welcome label to welcome and the full name of the user
		lblWelcome.setText(
				"Welcome " + LoginFrameController.user.getFirstName() + " " + LoginFrameController.user.getLastName());

	}
}
