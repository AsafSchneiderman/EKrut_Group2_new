package gui;
import java.io.IOException;
import java.util.Optional;

import Entities.Message;
import Entities.MessageType;
import controller.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
public class RegistrationRequestDetailsController {
	private static Message msg; 

	 @FXML
	    private AnchorPane pane;

	    @FXML
	    private Label idLbl;

	    @FXML
	    private Label firstNameLbl;

	    @FXML
	    private Label lastNameLbl;

	    @FXML
	    private Label emailLbl;

	    @FXML
	    private Label phoneLbl;

	    @FXML
	    private Label creditCardLbl;

	    @FXML
	    private Label regionLbl;

	    @FXML
	    private Button approveBtn;

	    @FXML
	    private Button deleteBtn;

	    @FXML
	    private Button backBtn;

	    @FXML
	    private Button exitBtn;

	    @FXML
	    void clickOnApprove(ActionEvent event) {

	    }

	    @FXML
	    void clickOnBack(ActionEvent event) {

	    }

	    @FXML
	    void clickOnDelete(ActionEvent event) {

	    }

	    @FXML
	    void clickOnExit(ActionEvent event) {

	    }

		public void start(Stage primaryStage) throws IOException {
			ClientMenuController.clientStage = primaryStage;
			primaryStage.setTitle("Ekrut - Region Manager >> View Request Details");
			Parent root = FXMLLoader.load(getClass().getResource("/gui/RequestDetailsFrame.fxml"));
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
							.accept(new Message(MessageType.update_workerMessagesStatus, LoginFrameController.user.getUserID()));
			}
		}
}
