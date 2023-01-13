package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PopUpMessageFrameController implements Initializable {

	@FXML
	private AnchorPane pane;

	@FXML
	private Label lblMsg;

	@FXML
	private Label lblMsg1;

/**
 * start the PopUpMessageFrame
 * 
 * @param primaryStage
 * @throws IOException
 */
public void start(Stage primaryStage) throws IOException {
	ClientMenuController.clientStage = primaryStage;
	primaryStage.setTitle("Ekrut Message");
	Parent root = FXMLLoader.load(getClass().getResource("/gui/PopUpMessageFrame.fxml"));
	Scene home = new Scene(root);
	primaryStage.setScene(home);

	// On pressing X (close window) the user logout from system and the client is
	// disconnect from server.
	/*primaryStage.setOnCloseRequest(e -> {
		ClientMenuController.clientControl.accept(new Message(MessageType.logout, LoginFrameController.user.getUserName()));
		ClientMenuController.clientControl
				.accept(new Message(MessageType.disconnected, LoginFrameController.user.getUserName()));
	});
	*/
	primaryStage.show();
}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
}