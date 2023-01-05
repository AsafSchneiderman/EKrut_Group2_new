package gui;
import java.io.IOException;

import Entities.Message;
import Entities.MessageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EmployeeRegistrationController {
	

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
	    private TextField firstNameTxt;

	    @FXML
	    private TextField lastNameTxt;

	    @FXML
	    private TextField idTxt;

	    @FXML
	    private TextField emailTxt;

	    @FXML
	    private TextField phoneTxt;

	    @FXML
	    private Button sendCustomerRegistrationButton;

	    @FXML
	    private Button backCustomRegistrationBtn;

	    @FXML
	    private Label creditCardCustomerRegistrationLbl;

	    @FXML
	    private Label lblAlert;

	    @FXML
	    private RadioButton regionManagerBtn;

	    @FXML
	    private RadioButton stockWorkerBtn;

	    @FXML
	    private RadioButton delivryWorkerBtn;

	    @FXML
	    private RadioButton custmerServiceBtn;

	    @FXML
	    void clickOnBack(ActionEvent event) {

	    }

	    @FXML
	    void sendForApproval(ActionEvent event) {

	    }

		public void start(Stage primaryStage) throws IOException {
			ClientMenuController.clientStage = primaryStage;
		    primaryStage.setTitle("Ekrut - Employee Registration");
			Parent root = FXMLLoader.load(getClass().getResource("/gui/EmployeeRegistrationForm.fxml"));
			Scene home = new Scene(root);
			primaryStage.setScene(home);
			// On pressing X (close window) the client is disconnect from server.
			primaryStage.setOnCloseRequest(e -> {
				ClientMenuController.clientControl.accept(new Message(MessageType.disconnected,""));
			});
			primaryStage.show();
			
		}


}
