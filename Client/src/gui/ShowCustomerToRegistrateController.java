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
	private Region regions[]= {Region.North,Region.South,Region.UAE};
	public static Message msg;
	private String id;
	public static UsersToRegister user;
	
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
    private ChoiceBox<Region> regionChoiceBox;

    @FXML
    private Button sendForApprovalBtn;

    @FXML
    private Button backBtn;
    
    @FXML
    private Label lblAlert;

    @FXML
    void clickBack(ActionEvent event) {

    }

    @FXML
    void clickSendForApproval(ActionEvent event) {
    	String creditCardNum=creditCardTxt.getText();
    	Region customersRegion=regionChoiceBox.getValue();
    	if(creditCardNum.trim().isEmpty() || customersRegion.toString().trim().isEmpty())
    		lblAlert.setText("please fill all fields");
    	for(int i=0;i<creditCardNum.length();i++) {
    		if(!creditCardNum.matches("[0-9]+"))
    			lblAlert.setText("credit card number is wrong");
    	}
    		
    }

	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Customer");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/showCustomerToRegisterForm.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		primaryStage.show();
		
	}
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
