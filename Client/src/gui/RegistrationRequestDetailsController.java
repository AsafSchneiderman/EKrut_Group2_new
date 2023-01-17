package gui;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Entities.Message;
import Entities.MessageType;
import Entities.UsersToRegister;
import controller.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class RegistrationRequestDetailsController implements Initializable {
	private static Message msg; 
	private String id;
	public static UsersToRegister user;
	public static RegistrationRequestsForRegionManagerController requestsTable;


	 
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
	    void clickOnApprove(ActionEvent event) {
	    	msg=new Message(MessageType.insertIntoUsers,user);
			ClientMenuController.clientControl.accept(msg);
			try {
				Thread.sleep(1000);
				System.out.println(ChatClient.msgServer.getMessageData());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "user was imported", "notification",JOptionPane.INFORMATION_MESSAGE);
			
			msg=new Message(MessageType.deleteRow,user.getId());
			ClientMenuController.clientControl.accept(msg);
			try {
				Thread.sleep(1000);
				System.out.println(ChatClient.msgServer.getMessageData());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			requestsTable=new RegistrationRequestsForRegionManagerController();
			try {
				requestsTable.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			
			e.printStackTrace();
		    } //send to UI*/
	    }

	    @FXML
	    void clickOnBack(ActionEvent event) {
	    	requestsTable=new RegistrationRequestsForRegionManagerController();
			try {
				requestsTable.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			
			e.printStackTrace();
		    } //send to UI*/
	    }

	    @FXML
	    void clickOnDelete(ActionEvent event) {
	    	msg=new Message(MessageType.deleteRow,user.getId());
			ClientMenuController.clientControl.accept(msg);
			try {
				Thread.sleep(1000);
				System.out.println(ChatClient.msgServer.getMessageData());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "user was deleted", "notification",JOptionPane.INFORMATION_MESSAGE);

			requestsTable=new RegistrationRequestsForRegionManagerController();
			try {
				requestsTable.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			
			e.printStackTrace();
		    } //send to UI*/

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
			
		}
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// initialize the background image
			BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
					false);
			BackgroundImage image = new BackgroundImage(new Image("images/CustomerRegistrationBackground.png"), BackgroundRepeat.NO_REPEAT,
			BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
			pane.setBackground(new Background(image));
            //show user details
			id=RegistrationRequestsForRegionManagerController.userList.get(CustomerRegistrationController.userNum).getId();//get this specific user id
			msg=new Message(MessageType.showUserDetails,id);
			ClientMenuController.clientControl.accept(msg);
			try {
				Thread.sleep(1000);
				user=(UsersToRegister) ChatClient.msgServer.getMessageData();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			idLbl.setText(id);
			System.out.println(user.getCreditCard()+ "From controller");
			firstNameLbl.setText(user.getFirstName());
			lastNameLbl.setText(user.getLastName());
			emailLbl.setText(user.getEmail());
			phoneLbl.setText(user.getPhone());
			creditCardLbl.setText(user.getCreditCard());
			regionLbl.setText(user.getRegion());
			
			
			
		}
		
		
		
}
