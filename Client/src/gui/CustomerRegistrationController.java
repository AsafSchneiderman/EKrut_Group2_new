package gui;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class CustomerRegistrationController {
	

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

		public void start(Stage primaryStage) throws IOException {
			ClientMenuController.clientStage = primaryStage;
	    	primaryStage.setTitle("Ekrut - Customer");
			Parent root = FXMLLoader.load(getClass().getResource("/gui/CustomerRegistrationForm.fxml"));
			Scene home = new Scene(root);
			primaryStage.setScene(home);
			primaryStage.show(); 
			
		}

	

}
