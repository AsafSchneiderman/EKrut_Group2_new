package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.*;

import controller.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


/*
 * DeliveryTimerController is a timer for specific customer.
 * The timer is showing the remaining time for receiving order,
 * and the customer can click for receiving order.
 * */
public class DeliveryTimerController implements Initializable{

	private static Message msg; // message to send to server
    @FXML
    private Button btnReceived;

    @FXML
    private Label lblHelloCustomer;

    @FXML
    private Text txtTimer;
    
    @FXML
    private AnchorPane pane;

    
  /*
   * changed deliver screen at button "Done" to setDisable false	
   * 
   * @param recieved button event
   * */
	@FXML
    void pressedRecieved(ActionEvent event) {
		
		int position = DeliveryWorkerFrameController.pos.get(1);
		Button b = DeliveryWorkerFrameController.btn1.get(position+1);
		b.setDisable(false);
		
		//close window
		DeliveryWorkerFrameController.newWindow.hide();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnReceived.setDisable(true);
		String userName = (String)ChatClient.msgServer.getMessageData();
		
		
		lblHelloCustomer.setText("Hi "+userName);

		//Setting Background
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/DeliveryTimer.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize.DEFAULT);
		pane.setBackground(new Background(image));
		
		
		Time time = new Time(DeliveryWorkerFrameController.time1.getCurrentTime());
		  txtTimer.setText(time.getCurrentTime());
		    Timeline timeline = new Timeline(
		            new KeyFrame(Duration.seconds(1),e ->{
		            
		            		if(time.oneSecondPassed()) 
		                    	   btnReceived.setDisable(false);
		                     txtTimer.setText(time.getCurrentTime());
		            }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
		
	}

}
