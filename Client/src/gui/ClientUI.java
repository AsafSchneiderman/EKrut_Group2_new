package gui;

import controller.ClientController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientUI extends Application {
	
	
	
	public  static ClientMenuController MenuFrame;
	//public static CustomerFrameController customerFrame;// for check,will delete later
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		MenuFrame = new ClientMenuController();
		MenuFrame.start(primaryStage);
		
		//customerFrame=new CustomerFrameController();-for check, will delete later
		//customerFrame.start(primaryStage);- for check, will delete later
		

	}

}
