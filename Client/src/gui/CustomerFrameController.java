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

public class CustomerFrameController{ 
	public static CustomerFrameController customerFrame;
	public static OnlineOrderFrameController onlineOrderFrame;
	public static OrderFrameController orderFrame;
	public static Stage customerStage;

    @FXML
    private Label lblHelloUser;

    @FXML
    private Button btnLocalOrder;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnOnlineOrder;

    @FXML
    void exit(ActionEvent event) {

    }

    @FXML
    void newLocalOrder(ActionEvent event) throws IOException {
    	orderFrame= new OrderFrameController();
    	try {
			orderFrame.start(ClientMenuController.clientStage);
	} catch (IOException e) {
		
		e.printStackTrace();
	} //send to UI*/
    	
    	//comment1
    }

    @FXML
    void newOnlineOrder(ActionEvent event) {
    	onlineOrderFrame= new OnlineOrderFrameController();
    	try {
			onlineOrderFrame.start(ClientMenuController.clientStage);
	} catch (IOException e) {
		
		e.printStackTrace();
	} //send to UI
    }

	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
    	primaryStage.setTitle("Ekrut - Customer");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/CustomerFrame.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		primaryStage.show(); 
		
	}

}
