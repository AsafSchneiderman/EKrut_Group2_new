package gui;
import java.io.IOException;

import Entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class CustomerRegistrationController {
	public static Message message;

	 @FXML
	 private Label customerRegistrationLbl;

	 @FXML
	 private Label firstNameCustomerRegistrationLbl;

	 @FXML
	 private Label lastNameCustomerRegistrationLbl;

	 @FXML
	 private Label idCustomerRegistrationLbl;

	 @FXML
	 private Label emailCustomerRegistrationLbl;

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
		 String firstName=firstNameCustomerRegistrationTxt.getText();
		 String lastName=lastNameCustomerRegistrationTxt.getText();
		 String id=idCustomerRegistrationTxt.getText();
		 String email=emailCustomerRegistrationTxt.getText();
		 String phone=phoneCustomerRegistrationTxt.getText();
		 String creditCard=creditCardCustomerRegistrationTxt.getText();
		 if(firstName.trim().isEmpty()|| lastName.trim().isEmpty()|| id.trim().isEmpty()) {
			 if(email.trim().isEmpty()|| phone.trim().isEmpty()|| creditCard.trim().isEmpty())
				 lblAlert.setText("Please fill all of the fields. thank you!");
		 }
		 //else 
			 
			 
	 }

	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
	    primaryStage.setTitle("Ekrut - Customer");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/CustomerRegistrationForm.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		primaryStage.show(); 
			
	}

	

}
