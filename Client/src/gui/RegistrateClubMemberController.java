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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
public class RegistrateClubMemberController implements Initializable{
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
    			JOptionPane.showMessageDialog(null, "Thank you, an email has been sent to the client", "notification",JOptionPane.INFORMATION_MESSAGE);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			lblAlert.setText("No such client");
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// initialize the background image
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/CustomerRegistrationBackground.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));
	}
}
