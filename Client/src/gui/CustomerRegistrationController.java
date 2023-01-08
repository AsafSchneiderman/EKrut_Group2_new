package gui;

import java.beans.Statement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.management.Query;

import Entities.*;
import controller.ChatClient;
import entity.OrderToDeliveryDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

public class CustomerRegistrationController implements Initializable{
	public static Message message;
	public static ArrayList<UsersToRegister> userList = new ArrayList<>();
	public static CustomerServiceController customerService;
	public static ShowCustomerToRegistrateController showCustomerToRegistrate;

	
	 @FXML
	    private TableColumn<ButtonForUsersToSignup, String> userIdCol;

	    @FXML
	    private TableColumn<ButtonForUsersToSignup, String> nameCol;

	    @FXML
	    private TableColumn<ButtonForUsersToSignup, String> lastNameCol;

	    @FXML
	    private TableColumn<ButtonForUsersToSignup, Button> buttonsCol;

	@FXML
    private AnchorPane pane;
	
	@FXML
    private TableView<ButtonForUsersToSignup> usersTable;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// initialize the background image
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/CustomerRegistrationBackground.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));
		
		usersTable.setEditable(true);
		userIdCol.setCellValueFactory(new PropertyValueFactory<ButtonForUsersToSignup,String>("id"));
		nameCol.setCellValueFactory(new PropertyValueFactory<ButtonForUsersToSignup,String>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<ButtonForUsersToSignup,String>("lastName"));
		buttonsCol.setCellValueFactory(new PropertyValueFactory<ButtonForUsersToSignup,Button>("btnShow"));
		ObservableList<ButtonForUsersToSignup> tvObservableList = FXCollections.observableArrayList();
		message=new Message(MessageType.showUsersToRegister,"");
		ClientMenuController.clientControl.accept(message);
		try {
			Thread.sleep(1000);
			System.out.println(ChatClient.msgServer.getMessageData().toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userList = (ArrayList<UsersToRegister>) ChatClient.msgServer.getMessageData();
		for (UsersToRegister row : userList)
		{
			final Button show=new Button("show user");
			show.setOnAction((ActionEvent event)->{
				//clickOnShowUser(event);
				showCustomerToRegistrate = new ShowCustomerToRegistrateController();
				try {
					showCustomerToRegistrate.start(ClientMenuController.clientStage);
				} catch (IOException e) {

					e.printStackTrace();
				}//  send to UI*/
			});
			ButtonForUsersToSignup tempList = new ButtonForUsersToSignup(row.getId(),row.getFirstName(),row.getLastName(),show);
			tvObservableList.add(tempList);
		}

		usersTable.setItems(tvObservableList);
		
		
	}
	/*void clickOnShowUser(ActionEvent event) {
		showCustomerToRegistrate = new ShowCustomerToRegistrateController();
		try {
			showCustomerToRegistrate.start(ClientMenuController.clientStage);
		} catch (IOException e) {

			e.printStackTrace();
		}//  send to UI*/
	//}


	

	@FXML
	void clickOnBack(ActionEvent event) {
		customerService = new CustomerServiceController();
		try {
			customerService.start(ClientMenuController.clientStage);
		} catch (IOException e) {

			e.printStackTrace();
		}//  send to UI*/
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

