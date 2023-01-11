package gui;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
public class CustomerServiceLastFrameController {
	 @FXML
	    private Button backToMainBtn;

	    @FXML
	    private Button logoutBtn;

	    @FXML
	    void clickBackToMainPage(ActionEvent event) {

	    }

	    @FXML
	    void clickOnLogout(ActionEvent event) {

	    }

		public void start(Stage primaryStage) throws IOException {
			ClientMenuController.clientStage = primaryStage;
	    	primaryStage.setTitle("Ekrut - Customer");
			Parent root = FXMLLoader.load(getClass().getResource("/gui/CustomerServiceLastFrame.fxml"));
			Scene home = new Scene(root);
			primaryStage.setScene(home);
			primaryStage.show(); 
			
		}

}
