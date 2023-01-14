package gui;

import java.io.IOException;

import Entities.Message;
import Entities.MessageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CustomerFrameController{ 
	public static CustomerFrameController customerFrame;
	public static OnlineOrderFrameController onlineOrderFrame;
	public static OrderFrameController orderFrame;
	public static Stage customerStage;
	public static Message msg;

    @FXML
    private Label lblHelloUser;

    @FXML
    private Button btnExit;

    @FXML
    private Button bntLocalOrder;

    @FXML
    private Button bntPickupOrder;

    @FXML
    void exitProg(ActionEvent event) {

    }
    @FXML
    void pickupOrder(ActionEvent event) {

    }

    @FXML
    void startLocalOrder(ActionEvent event) {

    }
    @FXML
    void exit(ActionEvent event) {

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
				});
		
	}

}
