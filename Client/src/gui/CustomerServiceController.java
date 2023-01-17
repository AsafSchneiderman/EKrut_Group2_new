package gui;
import java.io.IOException;


import java.net.URL;
import java.util.Optional;
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


public class CustomerServiceController implements Initializable{
	public static CustomerServiceController customerService;
	public static CustomerRegistrationController coustomerRegistration;
	public static LoginFrameController loginFrame;
	public static RegistrateClubMemberController registrateClubMember;

	@FXML
    private AnchorPane pane;
	
	@FXML
	private Label customerServicelbl;

	@FXML
	private Button registerCustomerBtn;

	@FXML
	private Button registerEmployeeBtn;

	@FXML
	private Button logoutCustomerServiceBtn;
	
	@FXML
    private Button registerClubMemberBtn;
	
	private static Message msg; 

	
	@FXML
	void clickRegisterNewCustomer(ActionEvent event) throws IOException{
		coustomerRegistration= new CustomerRegistrationController();
    	try {
    		coustomerRegistration.start(ClientMenuController.clientStage);
	} catch (IOException e) {
		
		e.printStackTrace();
	    } //send to UI*/
    }

	    @FXML
	    void clickRegisterNewEmployee(ActionEvent event) {
	    	String path="C:\\workersToRegistrate.csv";//please change to file path 
			msg = new Message(MessageType.importWorkersToRegister, path);
			ClientMenuController.clientControl.accept(msg);
			try {
				Thread.sleep(1000);
				System.out.println(ChatClient.msgServer.getMessageData().toString());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		JOptionPane.showMessageDialog(null, "employees were imported", "notification",JOptionPane.INFORMATION_MESSAGE);

	    }
	    
	    @FXML
	    void clickOnLogout(ActionEvent event) {
	    	msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
			ClientMenuController.clientControl.accept(msg);
	    	loginFrame= new LoginFrameController();
	    	try {
	    		loginFrame.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			
			e.printStackTrace();
		    } //send to UI*/
	    }
	    
	    @FXML
	    void clickRegisterClubMember(ActionEvent event) {
	    	registrateClubMember= new RegistrateClubMemberController();
	    	try {
	    		registrateClubMember.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			
			e.printStackTrace();
		    } //send to UI*/	
	    }
	    


		public void start(Stage primaryStage) throws IOException {
			ClientMenuController.clientStage = primaryStage;
		    primaryStage.setTitle("Ekrut - Customer Service");
			Parent root = FXMLLoader.load(getClass().getResource("/gui/CustomerServiceFrame.fxml"));
			Scene home = new Scene(root);
			primaryStage.setScene(home);
			// On pressing X (close window) the client is disconnect from server.
			primaryStage.setOnCloseRequest(e -> {
				ClientMenuController.clientControl.accept(new Message(MessageType.disconnected,""));
			});
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

			primaryStage.show();}
		

		@Override
		public void initialize(URL location, ResourceBundle resources) {

			// initialize the background image
			BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
					false);
			BackgroundImage image = new BackgroundImage(new Image("images/BackgroundFrameCustomerService.png"), BackgroundRepeat.NO_REPEAT,
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
			pane.setBackground(new Background(image));
			
			// data from an external file 
			String path="C:\\customersToRegistrate.csv";//please change to file path 
			msg = new Message(MessageType.importUsersToRegistrate, path);
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
