package gui;
import java.io.IOException;

import Entities.Message;
import Entities.MessageType;
import controller.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
public class RegistrateClubMemberController {
	private static Message msg; // message to send to server

	@FXML
    private AnchorPane pane;

    @FXML
    private TextField idTxt;

    @FXML
    private Button clubMemberBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Label lblAlert;

    @FXML
    void clickBecomeClubMemebr(ActionEvent event) {
    	String id=idTxt.getText();
    	if(id.trim().isEmpty())
    		lblAlert.setText("Fill in ID");
    	else {
    		msg = new Message(MessageType.registrateClubMember, id);
    		ClientMenuController.clientControl.accept(msg);
    		try {
    			Thread.sleep(1000);
    			System.out.println(ChatClient.msgServer.getMessageData().toString());
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    }

    @FXML
    void clickOnBack(ActionEvent event) {

    }

	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
	    primaryStage.setTitle("Ekrut - Registrate Customer as Club Member");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/RegistrateClubMemberForm.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		// On pressing X (close window) the client is disconnect from server.
		primaryStage.setOnCloseRequest(e -> {
			ClientMenuController.clientControl.accept(new Message(MessageType.disconnected,""));
		});
		primaryStage.show();
		
		
	}
}
