package gui;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Entities.ButtonForUsersToSignup;
import Entities.Message;
import Entities.MessageType;
import Entities.Region;
import Entities.UsersToRegister;
import Entities.WorkerMessage;
import controller.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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

public class ShowCustomerToRegistrateController implements Initializable{
	private String regions[]= {"TelAviv","Karmiel","Haifa"};
	public static Message msg;
	private String id;
	public static UsersToRegister user;
	public static String sendData;
	public static CustomerServiceLastFrameController lastFrame;
	public static CustomerRegistrationController customerRegistration;
	
	 
	@FXML
    private AnchorPane pane;

    @FXML
    private Label nameLbl;

    @FXML
    private Label idLbl;

    @FXML
    private Label lastNameLbl;

    @FXML
    private Label emailLbl;

    @FXML
    private Label phoneLbl;

    @FXML
    private TextField creditCardTxt;

    @FXML
    private ChoiceBox<String> regionChoiceBox;

    @FXML
    private Button sendForApprovalBtn;

    @FXML
    private Button backBtn;
    
    @FXML
    private Label lblAlert;

    /**
     * 
     * @param event click on back button
     * goes back to the customer registration frame
     */
    @FXML
    void clickBack(ActionEvent event) {
    	customerRegistration = new CustomerRegistrationController();
		try {
			customerRegistration.start(ClientMenuController.clientStage);
		} catch (IOException e) {

			e.printStackTrace();
		}//  send to UI*/

    }

    /**
     * 
     * @param event click on approve 
     * sends the user for approval to the region manager
     */
    @FXML
    void clickSendForApproval(ActionEvent event) {
    	String creditCardNum=creditCardTxt.getText();
    	String customersRegion=regionChoiceBox.getValue();
    	if(creditCardNum.trim().isEmpty() || customersRegion.toString().trim().isEmpty())
    		lblAlert.setText("please fill all fields");
    	else {
    		if(!creditCardNum.matches("[0-9]+"))
    			lblAlert.setText("credit card number is wrong");
    		else {
    			//notificationToCEO++;
    			id=CustomerRegistrationController.userList.get(CustomerRegistrationController.userNum).getId();//get this specific user id
    			sendData=id+"#"+creditCardNum+"#"+customersRegion;
    			// find the region manager
				ClientMenuController.clientControl
						.accept(new Message(MessageType.get_regionManagerByRegion, customersRegion));
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// create message to insert to worker messages
				WorkerMessage m = new WorkerMessage(0, (String) ChatClient.msgServer.getMessageData(),
						"user id "+id+" is waiting for approval", "notRead");
				ClientMenuController.clientControl.accept(new Message(MessageType.insert_WorkerMessages, m));

    			lastFrame= new CustomerServiceLastFrameController();
    	    	try {
    	    		lastFrame.start(ClientMenuController.clientStage);
    		} catch (IOException e) {
    			
    			e.printStackTrace();
    		    } //send to UI*/
    		}

    			
    		}
    	
    		
    }

    /**
     * 
     * @param primaryStage
     * @throws IOException
     */
	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Customer");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/showCustomerToRegisterForm.fxml"));
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

		primaryStage.show();
		}
				
		
	/**
	 * initialize the background and the label
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		regionChoiceBox.getItems().addAll(regions);
		// initialize the background image
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/CustomerRegistrationBackground.png"), BackgroundRepeat.NO_REPEAT,
		BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));
		id=CustomerRegistrationController.userList.get(CustomerRegistrationController.userNum).getId();//get this specific user id
		msg=new Message(MessageType.showUserDetails,id);
		ClientMenuController.clientControl.accept(msg);
		try {
			Thread.sleep(1000);
			System.out.println(ChatClient.msgServer.getMessageData().toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user = (UsersToRegister) ChatClient.msgServer.getMessageData();
		idLbl.setText(id);
		nameLbl.setText(user.getFirstName());
		lastNameLbl.setText(user.getLastName());
		emailLbl.setText(user.getEmail());
		phoneLbl.setText(user.getPhone());
	}
	
}
