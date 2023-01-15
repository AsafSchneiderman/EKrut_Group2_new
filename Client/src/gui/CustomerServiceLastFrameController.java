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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
public class CustomerServiceLastFrameController implements Initializable {
	public static Message msg;
	 @FXML
	    private AnchorPane pane;
	
	 @FXML
	    private Button backToMainBtn;

	    @FXML
	    private Button logoutBtn;

	    @FXML
	    void clickBackToMainPage(ActionEvent event) {

	    }

	    @FXML
	    void clickOnLogout(ActionEvent event) {

	    }

		public void start(Stage primaryStage) throws IOException {
			ClientMenuController.clientStage = primaryStage;
	    	primaryStage.setTitle("Ekrut - Customer Registration");
			Parent root = FXMLLoader.load(getClass().getResource("/gui/CustomerServiceLastFrame.fxml"));
			Scene home = new Scene(root);
			primaryStage.setScene(home);
			primaryStage.show(); 
			
		}
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			msg=new Message(MessageType.insertCreditCardAndRegion,ShowCustomerToRegistrateController.sendData);
			ClientMenuController.clientControl.accept(msg);
			try {
				Thread.sleep(1000);
				System.out.println(ChatClient.msgServer.getMessageData().toString());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// initialize the background image
						BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
								false);
						BackgroundImage image = new BackgroundImage(new Image("images/BackgroundFrameCustomerService.png"), BackgroundRepeat.NO_REPEAT,
								BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
						pane.setBackground(new Background(image));
			

		}

}
