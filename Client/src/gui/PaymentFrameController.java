package gui;

import java.awt.Label;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

public class PaymentFrameController implements Initializable {
	public static Message msg;
    @FXML
    private Label lblWelcome;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button bntLatePay;

    @FXML
    private Button bntNowPay;

    @FXML
    private TextField txtCreditNum;

    @FXML
    private TextField txtMonth;

    @FXML
    private TextField txtCvv;

    @FXML
    private TextField txtYear;

    @FXML
    private ImageView imgPic;

    @FXML
    void PayLater(ActionEvent event) {
    	
    	msg = new Message(,"delay");
    	ClientMenuController.clientControl.accept(msg);

    }

    @FXML
    void PayNow(ActionEvent event) {
    	msg = new Message(,"now");
    	ClientMenuController.clientControl.accept(msg);
    	txtCreditNum.setText(LoginFrameController.user.get);

    }
    
	public void start(Stage customerStage) throws IOException {
		ClientMenuController.clientStage = customerStage;
		//Parent root = FXMLLoader.load(getClass().getResource("/gui/ConfirmOrderFrame.fxml"));
		Parent root = FXMLLoader.load(getClass().getResource("/gui/paymentFrame.fxml"));
		ClientMenuController.clientStage.setTitle("Ekrut - Costumer >> Payment");
		Scene home = new Scene(root);
		customerStage.setScene(home);
		// On pressing X (close window) the user logout from system and the client is
		// disconnect from server.
		customerStage.setOnCloseRequest(e -> {
		msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
		ClientMenuController.clientControl.accept(msg);
		ClientMenuController.clientControl
		.accept(new Message(MessageType.disconnected, LoginFrameController.user.getUserName()));
		});
 
		customerStage.show(); 
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// initialize the background image and pic
				BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
						false);
				BackgroundImage image = new BackgroundImage(new Image("images/paymentBackground.png"),
						BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
				pane.setBackground(new Background(image));
				lblWelcome.setText("Welcome " + LoginFrameController.user.getFirstName() + " " + LoginFrameController.user.getLastName());
				if(LoginFrameController.user.getRole().equals("costumer"))
				{
					bntLatePay.setVisible(false);
				}
		
		
	}

}

