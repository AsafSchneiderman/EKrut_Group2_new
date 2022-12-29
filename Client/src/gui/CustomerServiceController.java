package gui;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CustomerServiceController {
	public static CustomerServiceController customerService;
	public static CustomerRegistrationController coustomerRegistration;

	@FXML
	private Label customerServicelbl;

	@FXML
	private Button registerCustomerBtn;

	@FXML
	private Button registerEmployeeBtn;

	@FXML
	private Button logoutCustomerServiceBtn;
	
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

	    }

		public void start(Stage primaryStage) throws IOException {
			ClientMenuController.clientStage = primaryStage;
		    primaryStage.setTitle("Ekrut - Customer");
			Parent root = FXMLLoader.load(getClass().getResource("/gui/CustomerServiceForm.fxml"));
			Scene home = new Scene(root);
			primaryStage.setScene(home);
			primaryStage.show(); 
			
		}
	 

	

}
