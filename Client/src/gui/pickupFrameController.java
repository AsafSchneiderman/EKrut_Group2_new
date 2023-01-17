package gui;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Entities.Message;
import Entities.MessageType;
import Entities.PromotionSells;
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
/**
 * 
 * @author Marina
 *
 */
public class pickupFrameController implements Initializable {

	public static Message msg, msg1, msg2;

	@FXML
	private AnchorPane pane;

	@FXML
	private Button btnEnd;
    @FXML
    private Button bntCheckOrder;

    @FXML
    private TextField txtOrderCode;

    @FXML
    private Label lblAlert;

	/**
	 * The user exit from the region manager frame and do logout to the user from
	 * the DB
	 * 
	 * @param event (Click on Exit button)
	 */
	@FXML
	void end(ActionEvent event) {

		ClientMenuController.clientStage.setScene(LoginFrameController.home);
		// Logout
		msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
		ClientMenuController.clientControl.accept(msg);

	}
	/**
	 * check if pickup order exists
	 * @param event - click on take order button
	 */
    @FXML
    void checkOrder(ActionEvent event) {
    	int orderNum;
    	orderNum = Integer.parseInt(txtOrderCode.getText());
    	msg1 = new Message(MessageType.getPickupOrder,orderNum );
    	ClientMenuController.clientControl.accept(msg1);
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String res = (String) ChatClient.msgServer.getMessageData();
    	if(res.equals("pickupOrder"))
    	{
    		endFrameController ending = new endFrameController();
    		msg2 = new Message(MessageType.updatePickupStatus,orderNum );
    		ClientMenuController.clientControl.accept(msg2);
    		try {
    			Thread.sleep(1000);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		try {
    			ending.start(ClientMenuController.clientStage);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		
    	}
    	else
    	{
    		lblAlert.setVisible(true);
    	}

    }

    /**
	 * start the pickupFrame
	 * 
	 * @param primaryStage
	 * @throws IOException
	 */
	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Customer >> Pickup Order");
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

	    lblAlert.setVisible(false);;
		// initialize the background image and icon
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/PickUpOrderFrame.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));

	}

}
