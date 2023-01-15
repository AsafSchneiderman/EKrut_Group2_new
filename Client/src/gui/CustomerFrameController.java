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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

public class CustomerFrameController implements Initializable {
	public static CustomerFrameController customerFrame;
	public static OnlineOrderFrameController onlineOrderFrame;
	public static OrderFrameController orderFrame;
	public static Stage customerStage;
	public static Message msg;
	@FXML
	private AnchorPane pane;

	@FXML
	private Label lblHelloUser;

	@FXML
	private Button btnExit;

	@FXML
	private Button bntLocalOrder;

	@FXML
	private Button bntPickupOrder;
	@FXML
	private Label lblWelcome;
    @FXML
    private ImageView imgIcon;

	@FXML
	void pickupOrder(ActionEvent event) {

		pickupFrameController pickup = new pickupFrameController();
		try {
			pickup.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void startLocalOrder(ActionEvent event) {

		OrderFrameController order = new OrderFrameController();
		try {
			order.start(ClientMenuController.clientStage);
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
	void exitProg(ActionEvent event) {
		ClientMenuController.clientStage.setScene(LoginFrameController.home);
		// Logout
		msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
		ClientMenuController.clientControl.accept(msg);

	}

	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Customer -> Welcome to machine");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/CustomerFrame.fxml"));
		Scene home = new Scene(root);
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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// initialize the background image and icon
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/LocalOrderFrame.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));
		// initialize the Welcome label to welcome and the full name of the user
		lblWelcome.setText(
				"Welcome " + LoginFrameController.user.getFirstName() + " " + LoginFrameController.user.getLastName());
		Image Icone = new Image("images/dish.png");
		imgIcon.setImage(Icone);
		imgIcon.setFitWidth(70);
		imgIcon.setFitHeight(70);
	}

}
