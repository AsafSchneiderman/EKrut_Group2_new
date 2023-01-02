package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
import Entities.*;
import controller.ChatClient;

public class LoginFrameController implements Initializable {

	public static User user = null;
	//public static Message message;

	private static Message msg; // message to send to server

	@FXML
	private AnchorPane pane;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Button btnEnter;

	@FXML
	private Label lblAlert;

    @FXML
    private ImageView imglogin;

	@FXML
	private TextField txtUserName;

	@FXML
	void pressEnter(ActionEvent event) {
		String password = txtPassword.getText();
		String userName = txtUserName.getText();

		if (password.trim().isEmpty() || userName.trim().isEmpty())
			lblAlert.setText("Please fill both user name and password");
		else {
			// Create message to send to server
			msg = new Message(MessageType.login, userName + "#" + password);

			// handle message to server and GUI
			new handleDbService(lblAlert).start();
			// System.out.println(user.getFirstName());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (user != null)
				this.openFrameByRole(user.getRole());
		}
	}

	/**
	 * HandleDbService class used for concurrency working with DB and JavaFx GUI
	 * 
	 * @author
	 *
	 */
	private static class handleDbService extends Service<String> {

		private handleDbService(Label lblAlert) {
			setOnSucceeded(new EventHandler<WorkerStateEvent>() {

				@Override
				public void handle(WorkerStateEvent event) {
					lblAlert.setVisible(true);
					lblAlert.setText((String) event.getSource().getValue());
				}
			});
		}

		@Override
		protected Task<String> createTask() {

			return new Task<String>() {

				@Override
				protected String call() throws Exception {
					ClientMenuController.clientControl.accept((Object) msg);
					Thread.sleep(500);
					String data = (String) ChatClient.msgServer.getMessageData();
					if (data.equals("Wrong_Input"))
						return "Wrong user name or password!";
					else if (data.equals("Already_logged_in"))
						return "User already logged in";
					else {
						String[] userData = data.split("#"); // Export user data
						user = new User(userData[0], userData[1], userData[2], userData[3], userData[4], userData[5],
								userData[6], userData[7], Integer.valueOf(userData[8]));
					}
					Thread.sleep(500);
					return "";
				}

			};
		}
	}

	public void openFrameByRole(String role) {
		// System.out.println("login o.k" + role);

		if (user.getRole().equals("RegionManager")) {
			RegionManagerFrameController regionManagerFrameController = new RegionManagerFrameController();
			try {
				regionManagerFrameController.start(ClientMenuController.clientStage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (user.getRole().equals("Customer")) {
			OrderFrameController order = new OrderFrameController();
			try {
				order.start(ClientMenuController.clientStage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (user.getRole().equals("Deliver")) {
			DeliveryWorkerFrameController order = new DeliveryWorkerFrameController();
			try {
				order.start(ClientMenuController.clientStage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		if (user.getRole().equals("CustomerService")) {
			CustomerServiceController customerService = new CustomerServiceController();
			try {
				customerService.start(ClientMenuController.clientStage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Client >> Login");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/LoginFrame.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);

		// On pressing X (close window) the client is disconnect from server.
		primaryStage.setOnCloseRequest(e -> {
			ClientMenuController.clientControl.accept(new Message(MessageType.disconnected,""));
		});
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// initialize the background image
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/LoginFrame.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));
	}
}
