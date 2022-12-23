package gui;

import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import Entities.*;
import controller.ChatClient;

public class LoginFrameController {

	public static User user = null; 
	public static Message message;
	
	public static Message msg; //message to send to service
	
	@FXML
	 private PasswordField txtPassword;
	 
    @FXML
    private Button btnEnter;

    @FXML
    private Label lblAlert;

    @FXML
    private ImageView loginImage;

    @FXML
    private TextField txtUserName;

    @FXML
    void pressEnter(ActionEvent event) {
    	String password = txtPassword.getText();
    	String userName = txtUserName.getText();
    	
    	if(password.trim().isEmpty() || userName.trim().isEmpty())
    		lblAlert.setText("Please fill both user name and password");
    	else {
    	//Create message 
    	msg =new Message(MessageType.login,userName+"#"+password);
    	
    	//handle message to server and GUI
    	new handleDbService(lblAlert).start();
    	System.out.println(user.getFirstName());
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(user != null)
    		this.openFrameByRole(user.getRole());
    	}  
    }
    
 
    //HandleDbService class used for concurrency working with DB and JavaFx GUI
    private static class handleDbService extends Service<String>{

    	private handleDbService(Label lblAlert) {
    		setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				
				@Override
				public void handle(WorkerStateEvent event) {
					lblAlert.setVisible(true);
					lblAlert.setText((String) event.getSource().getValue());
				}
			});
    	}
		@Override
		protected Task<String> createTask() {
			
			return new Task<String>() {

				@Override
				protected String call() throws Exception {
					ClientMenuController.clientControl.accept((Object)msg);
					Thread.sleep(100);
					String data = (String)ChatClient.msgServer.getMessageData();
					if(data.equals("Wrong_Input"))
						return "Wrong user name or password!";
					else if(data.equals("Already_logged_in"))
						return "User already logged in";
					else {
						String[] userData = data.split("#");  //Export user data
						user = new User(userData[0],userData[1],userData[2], userData[3], userData[4], userData[5], userData[6], userData[7], Integer.valueOf(userData[8]));
						System.out.println("else: "+user.getFirstName());
					}
					
					return "";
				}
				
			};
		}
    	
    }
    /*if(ChatClient.msgServer.getMessageData().toString().equals("Wrong_Input")) 
	lblAlert.setText("Wrong user name or password!");
else if(ChatClient.msgServer.getMessageData().toString().equals("Already_logged_in")) {
	lblAlert.setText("User already logged in");
}
else {
	String[] data =ChatClient.msgServer.getMessageData().toString().split("#");  //Export user data
	//System.out.println("user data length: "+ data.length);
	user = new User(data[0],data[1],data[2], data[3], data[4], data[5], data[6], data[7], Integer.valueOf(data[8]));
	this.openFrameByRole(user.getRole());
	
}*/

    
    public void openFrameByRole(String role) {
    	System.out.println("login o.k" + role);
    }

    public void start(Stage primaryStage) throws IOException{
    	Parent root = FXMLLoader.load(getClass().getResource("/gui/LoginFrame.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		
		//On pressing X (close window) the client is disconnect from server.
		primaryStage.setOnCloseRequest(e -> { 
			ClientMenuController.clientControl.accept("disconnect");
		});
		primaryStage.show();
    }

		
    }


