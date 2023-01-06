package gui;

import java.beans.Statement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.management.Query;

import Entities.Message;
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

public class CustomerRegistrationController implements Initializable{
	public static Message message;
//	public static CustomerRegistrationController customerRegistration;
	public static CustomerServiceLastFrameController lastFrame;
	public static CustomerServiceController customerService;
	private static Message msg; // message to send to server

	@FXML
    private AnchorPane pane;

	@FXML
	private Label phoneNumberCustomerRegistrationLbl;

	@FXML
	private TextField firstNameCustomerRegistrationTxt;

	@FXML
	private TextField lastNameCustomerRegistrationTxt;

	@FXML
	private TextField idCustomerRegistrationTxt;

	@FXML
	private TextField emailCustomerRegistrationTxt;

	@FXML
	private TextField phoneCustomerRegistrationTxt;

	@FXML
	private Button sendCustomerRegistrationButton;

	@FXML
	private Button backCustomRegistrationBtn;

	@FXML
	private Label creditCardCustomerRegistrationLbl;

	@FXML
	private TextField creditCardCustomerRegistrationTxt;

	@FXML
	private Label lblAlert;

	@FXML
	void sendForApproval(ActionEvent event) {
		StringBuilder sendForApproval=new StringBuilder();
		String firstName = firstNameCustomerRegistrationTxt.getText();
		String lastName = lastNameCustomerRegistrationTxt.getText();
		String id = idCustomerRegistrationTxt.getText();
		String email = emailCustomerRegistrationTxt.getText();
		String phone = phoneCustomerRegistrationTxt.getText();
		String creditCard = creditCardCustomerRegistrationTxt.getText();
		if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || id.trim().isEmpty()) {
			if (email.trim().isEmpty() || phone.trim().isEmpty() || creditCard.trim().isEmpty())
				lblAlert.setText("Please fill all of the fields. thank you!");
		} else {
			boolean doesCustomerExist=Query.checkExsitingCustomer(id);//*****************???
			if(doesCustomerExist==true)
					lblAlert.setText("user already has an acount!");
				else {//build the string to send for the approval
					sendForApproval.append(id);  
					sendForApproval.append("#");
					sendForApproval.append(firstName); 
					sendForApproval.append("#");
					sendForApproval.append(lastName); 
					sendForApproval.append("#");
					sendForApproval.append(email);
					sendForApproval.append("#");
					sendForApproval.append(phone); 
					sendForApproval.append("#");
					sendForApproval.append(creditCard); 
					lastFrame = new CustomerServiceLastFrameController();
					try {
				    lastFrame.start(ClientMenuController.clientStage);}
					catch(IOException e) {

						e.printStackTrace();
					} // send to UI*/
					} 
				}
		}

	

	@FXML
	void clickOnBack(ActionEvent event) {
		customerService = new CustomerServiceController();
		try {
			customerService.start(ClientMenuController.clientStage);
		} catch (IOException e) {

			e.printStackTrace();
		} // send to UI*/
	}

	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Customer");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/CustomerRegistrationForm.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
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

