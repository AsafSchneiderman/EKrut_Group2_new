package gui;

import java.io.IOException;

import Entities.Message;
import Entities.MessageType;
import controller.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RegionManagerFrameController {

    @FXML
    private Button btnUpdateThresholdLevel;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnRestockMessage;

    @FXML
    private Button btnViewReports;
    
    private static Message msg; //message to send to service

    @FXML
    void RestockMessageToWorker(ActionEvent event) {

    }

    
    //The user exit from the system and the system do logout to the user from the DB
    @FXML
    void exit(ActionEvent event) {
    	
    	//Create message 
    	msg =new Message(MessageType.Logout,LoginFrameController.user.getUserName());
    	ClientMenuController.clientControl.accept(msg);
    }

    @FXML
    void updateThresholdLevel(ActionEvent event) {

    }

    /** Open report search frame **/
    @FXML
    void viewReports(ActionEvent event) {
    	ReportSearchFrameController reportSearchFrameController = new ReportSearchFrameController();
    	try {
			reportSearchFrameController.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void start(Stage primaryStage) throws IOException{
    	ClientMenuController.clientStage = primaryStage;
    	Parent root = FXMLLoader.load(getClass().getResource("/gui/RegionManagerFrame.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		
		//On pressing X (close window) the client is disconnect from server.
		primaryStage.setOnCloseRequest(e -> { 
			msg =new Message(MessageType.Logout,LoginFrameController.user.getUserName());
			ClientMenuController.clientControl.accept(msg);
			ClientMenuController.clientControl.accept(new Message(MessageType.disconnected,LoginFrameController.user.getUserName()));
		});
		primaryStage.show();
    }


}
