package gui;

import controller.ClientController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI extends Application {
	
	
	
	public  static ClientMenuController MenuFrame;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		MenuFrame = new ClientMenuController();
		MenuFrame.start(primaryStage);

	}

}
