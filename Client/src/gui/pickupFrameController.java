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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class pickupFrameController {
	
	public static Message msg;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button btnEnd;

    @FXML
    void end(ActionEvent event) {

    }
    
	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
    	primaryStage.setTitle("Ekrut - Customer -> Pickup Order");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/pickupFrame.fxml"));
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
