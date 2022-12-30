package gui;

import java.beans.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Scanner;

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
//	public static CustomerRegistrationController customerRegistration;
	public static CustomerServiceLastFrameController lastFrame;
	public static CustomerServiceController customerService;

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
		Scanner sc = new Scanner(System.in);
		String firstName = firstNameCustomerRegistrationTxt.getText();
		String lastName = lastNameCustomerRegistrationTxt.getText();
		String id = idCustomerRegistrationTxt.getText();
		String idSearchDB = sc.next();
		String email = emailCustomerRegistrationTxt.getText();
		String phone = phoneCustomerRegistrationTxt.getText();
		String creditCard = creditCardCustomerRegistrationTxt.getText();
		if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || id.trim().isEmpty()) {
			if (email.trim().isEmpty() || phone.trim().isEmpty() || creditCard.trim().isEmpty())
				lblAlert.setText("Please fill all of the fields. thank you!");
		} else {
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost/db_ekrut?serverTimezone=IST",
						"root", "password");
				java.sql.Statement stmt = con.createStatement();
				String SQL = "SELECT * FROM users WHERE id='" + idSearchDB + "'";
				stmt.execute(SQL);
				ResultSet rs = stmt.executeQuery(SQL);
				if (rs.next())
					lblAlert.setText("user already has an acount!");
				else {
					lastFrame = new CustomerServiceLastFrameController();
					try {
						lastFrame.start(ClientMenuController.clientStage);
					} catch (IOException e) {

						e.printStackTrace();
					} // send to UI*/
				}

			} catch (Exception e) {
				System.out.println("Error" + e.getMessage());
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

}

