package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    void newLocalOrder(ActionEvent event) {
    	customerFrame=new CustomerFrameController();
    	orderFrame= new OrderFrameController();
    	try {
			orderFrame.start(customerStage);
	} catch (IOException e) {
		
		e.printStackTrace();
	} //send to UI
    }

    @FXML
    void newOnlineOrder(ActionEvent event) {
    	customerFrame=new CustomerFrameController();
    	onlineOrderFrame= new OnlineOrderFrameController();
    	try {
			onlineOrderFrame.start(customerStage);
	} catch (IOException e) {
		
		e.printStackTrace();
	} //send to UI
    }

}
