package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

public class PopUpMessageFrameController implements Initializable {

	@FXML
	private AnchorPane pane;

	@FXML
	private Label lblMsg;

	/**
	 * close the PopUpMessageFrame after the time is end
	 * @param time - the seconds for the messageFrame to close
	 */
	public void closeMsg(int time)
	{
		long mTime = System.currentTimeMillis();
		long end = mTime + time; // in seconds 

		while (mTime < end) 
		{
		    mTime = System.currentTimeMillis();
		} 
		ClientMenuController.clientStage.close();
	}
	
	/**
	 * start the PopUpMessageFrame
	 * 
	 * @param primaryStage
	 * @throws IOException
	 */
	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut Message");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/PopUpMessageFrame.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		primaryStage.show();
	}

	/**
	 * initialize parameters when the frame start
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// initialize the background image
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/PopUpMessageFrame.png"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));

	}
}