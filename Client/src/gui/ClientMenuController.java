package gui;

import java.awt.Button;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import Entities.Message;
import Entities.MessageType;
import controller.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

public class ClientMenuController implements Initializable {

	public static ClientController clientControl;

	public static LoginFrameController loginFrame;

	public static InstallFrameController installFrame;

	public static String config; // installation configuration (EK/OL)
	public static String vendingMachine; // vending Machine to install configuration (EK)

	public static Stage clientStage;

	public static Scene home;

	@FXML
	private AnchorPane pane;

	@FXML
	private TextField txtIp;

	@FXML
	private TextField txtPort;

	@FXML
	private ImageView imgClient;

	/**
	 * Creates new frame LoginFrameController and connect to server.
	 * 
	 * @param event (Click on Connect button)
	 * @throws UnknownHostException
	 */
	@FXML
	void connectClient(ActionEvent event) throws UnknownHostException {

		String Ip = txtIp.getText();

		int Port = Integer.parseInt(txtPort.getText()); // need To test if the arguments are empty

		clientControl = new ClientController(Ip, Port);
		loginFrame = new LoginFrameController();
		installFrame = new InstallFrameController();
		Message msg = new Message(MessageType.connected, Inet4Address.getLocalHost().getHostAddress());
		ClientMenuController.clientControl.accept(msg);
		try {

			ClientMenuController.clientControl.accept(new Message(MessageType.Get_vendingMachines, ""));
			installFrame.start(clientStage);

		} catch (IOException e) {

			e.printStackTrace();
		} // send to UI

	}

	/**
	 * Start the loginFrame
	 */
	public static void startLoginFrame() {
		try {
			loginFrame.start(clientStage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start(Stage primaryStage) throws IOException {
		clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Client Connection");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ClientMenu.fxml"));
		home = new Scene(root);
		primaryStage.setScene(home);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// initialize the background image
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/ClientUiMenu.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));
	}

}
