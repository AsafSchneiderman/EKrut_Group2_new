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
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

public class ThresholdLevelFrameController {

    @FXML
    private TableColumn<?, ?> regionCol;

    @FXML
    private TableColumn<?, ?> locationCol;

    @FXML
    private TableColumn<?, ?> thresholdLevelCol;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnUpdateThresholdLevel;
    
    private static Message msg; //message to send to service

    /**
     * Goes back to the previous window of RegionManagerFrameController
     * @param event (Click on Back button)
     */
    @FXML
    void BackToPreviousPage(ActionEvent event) {
    	
    	RegionManagerFrameController RegionManagerController = new RegionManagerFrameController();
    	try {
    		RegionManagerController.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

    @FXML
    void updateThresholdLevel(ActionEvent event) {

    }
    
    /**
     * start the ThresholdLevelFrame
     * @param primaryStage
     * @throws IOException
     */
    public void start(Stage primaryStage) throws IOException{
    	ClientMenuController.clientStage = primaryStage;
    	primaryStage.setTitle("Ekrut - Region Manager >> Threshold Level");
    	Parent root = FXMLLoader.load(getClass().getResource("/gui/ThresholdLevelFrame.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		
		//On pressing X (close window) the user logout from system and the client is disconnect from server.
		primaryStage.setOnCloseRequest(e -> { 
			msg =new Message(MessageType.logout,LoginFrameController.user.getUserName());
			ClientMenuController.clientControl.accept(msg);
			//ClientMenuController.clientControl.accept(new Message(MessageType.disconnected,LoginFrameController.user.getUserName()));
		});
		primaryStage.show();
    }

}
