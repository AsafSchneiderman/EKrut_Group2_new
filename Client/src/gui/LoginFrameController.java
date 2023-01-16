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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import gui.CustomerServiceController;

public class LoginFrameController implements Initializable {

	public static User user = null;
	public static Scene home;
	public static Time timer; // Define time to timer

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
	void exit(ActionEvent event) {

		ClientMenuController.clientControl.accept(new Message(MessageType.disconnected, ""));

		// create a PopUp message
		PopUpMessageFrameController popUpMsgController = new PopUpMessageFrameController();
		try {
			popUpMsgController.start(ClientMenuController.clientStage);
			popUpMsgController.closeMsg(3000);

		} catch (IOException e) {
			e.printStackTrace();

		}

		/*
		 * Alert a = new Alert(AlertType.NONE, "Hope to see you soon.");
		 * a.setTitle("Exit from EKRUT"); a.setHeaderText("Bye Bye..."); // show the
		 * dialog a.show(); try { Thread.sleep(3000); } catch (InterruptedException e) {
		 * // TODO Auto-generated catch block e.printStackTrace(); }
		 * a.setAlertType(AlertType.INFORMATION); a.close();
		 */

		/*
		 * synchronized (a) {
		 * 
		 * // set alert type //a.setAlertType(AlertType.INFORMATION);
		 * //a.setTitle("Exit from EKRUT"); //a.setHeaderText("Bye Bye..."); //
		 * a.setContentText("Hope to see you soon.");
		 * 
		 * 
		 * try { a.wait(3000);
		 * 
		 * } catch (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 */
		// a.close();

	}

	/**
	 * the client enter his userName and password and the system check in the DB
	 * 
	 * @param event (Click on Enter button)
	 */

	@FXML
	void pressEnter(ActionEvent event) {
		user = null;
		String password = txtPassword.getText();
		String userName = txtUserName.getText();

		if (password.trim().isEmpty() || userName.trim().isEmpty())
			lblAlert.setText("Please fill both user name and password");
		else {
			// Create message to send to server
			msg = new Message(MessageType.login, userName + "#" + password);

			// handle message to server and GUI
			new handleDbService(lblAlert).start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (user != null) // by configuration (EK/OL)
				if (ClientMenuController.config.equals("EK"))
					this.openFrameByRole_EK(user.getRole());
				else // OL
					this.openFrameByRole_OL(user.getRole());
		}
	}

	/**
	 * HandleDbService class used for concurrency working with DB and JavaFx GUI
	 * 
	 * @author Aviram
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
								userData[6], userData[7], userData[8], Integer.valueOf(userData[9]), userData[10]);
						System.out.println(userData[10]);
						System.out.println(user.getRegion());
					}
					Thread.sleep(500);
					return "";
				}

			};
		}
	}

	/**
	 * This method moves to the next frame according to the role of the user EK
	 * configuration - local connection, only Customer or ClubMember
	 * 
	 * @param role - the role of the user
	 */
	public void openFrameByRole_EK(String role) {

		// Customer
		if (user.getRole().equals("Customer")) {
			CustomerFrameController costumerFrame = new CustomerFrameController();
			try {
				costumerFrame.start(ClientMenuController.clientStage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (user.getRole().equals("ClubMember")) {

		} else // message to register
		{
			Alert a = new Alert(AlertType.WARNING);

			// set title
			a.setTitle("EKRUT Messages");
			// set header text
			a.setHeaderText("You are NOT registered in EKRUT system!				");

			// set content text
			a.setContentText("To login the EKRUT system you must register as a Customer or Club Member.\n"
					+ "\nPlease register in our Customer Service:\nPhone Number:  077-77777777");
			a.show();
			// logout the user from DB
			ClientMenuController.clientControl
					.accept(new Message(MessageType.logout, LoginFrameController.user.getUserName()));
		}

	}

	/**
	 * This method moves to the next frame according to the role of the user OL
	 * configuration - remote connection, all users role, except User
	 * 
	 * @param role - the role of the user
	 */
	public void openFrameByRole_OL(String role) {

		// region manager
		if (user.getRole().equals("RegionManager")) {

			// get the region of the region manager
			ClientMenuController.clientControl
					.accept(new Message(MessageType.Get_region, LoginFrameController.user.getUserID()));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// get the messages of the region manager
			ClientMenuController.clientControl
					.accept(new Message(MessageType.Get_messages, LoginFrameController.user.getUserID()));
			RegionManagerFrameController regionManagerFrameController = new RegionManagerFrameController();
			try {
				regionManagerFrameController.start(ClientMenuController.clientStage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// CEO
		else if (user.getRole().equals("CEO")) {

			// get the region of the CEO
			ClientMenuController.clientControl
					.accept(new Message(MessageType.Get_region, LoginFrameController.user.getUserID()));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user.setRegion((String) ChatClient.msgServer.getMessageData());
			CEOFrameController CEOFrameController = new CEOFrameController();
			try {
				CEOFrameController.start(ClientMenuController.clientStage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Customer
		else if (user.getRole().equals("Customer")) {
			/*
			 * if (ClientMenuController.config.equals("OL")) { OnlineOrderFrameController
			 * onlineOrder = new OnlineOrderFrameController();
			 * ClientMenuController.clientControl.accept(new
			 * Message(MessageType.Get_vendingMachines, "")); try {
			 * onlineOrder.start(ClientMenuController.clientStage); } catch (IOException e)
			 * { e.printStackTrace(); }
			 * 
			 * } else {
			 */
			//CustomerFrameController costumerFrame = new CustomerFrameController();
			OnlineOrderFrameController onlineOrder =  new OnlineOrderFrameController();
			 ClientMenuController.clientControl.accept(new Message(MessageType.Get_vendingMachines, ""));
			try {
				onlineOrder.start(ClientMenuController.clientStage);
			} catch (IOException e) {
				e.printStackTrace();
			}
			/*
			 * }
			 * 
			 * OrderFrameController order = new OrderFrameController(); try {
			 * order.start(ClientMenuController.clientStage); } catch (IOException e) {
			 * e.printStackTrace(); }
			 */
		}

		// delivery worker
		else if (user.getRole().equals("Deliver")) {
			DeliveryWorkerFrameController order = new DeliveryWorkerFrameController();
			try {
				order.start(ClientMenuController.clientStage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Customer Service Worker
		else if (user.getRole().equals("CustomerServiceWorker")) {
			CustomerServiceController customerService = new CustomerServiceController();
			try {
				customerService.start(ClientMenuController.clientStage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Operations worker
		else if (user.getRole().equals("OperationsWorker")) {
			// get the messages of the region manager
			ClientMenuController.clientControl
					.accept(new Message(MessageType.Get_messages, LoginFrameController.user.getUserID()));
			OperationsWorkerFrameController OperationsWorkerFrame = new OperationsWorkerFrameController();
			try {
				OperationsWorkerFrame.start(ClientMenuController.clientStage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else // user role is none of the types above - send a message to register
		{
			Alert a = new Alert(AlertType.WARNING);

			// set title
			a.setTitle("EKRUT Messages");
			// set header text
			a.setHeaderText("You are NOT registered in EKRUT system!				");

			// set content text
			a.setContentText("To login the EKRUT system you must register.\n"
					+ "\nPlease register in our Customer Service:\nPhone Number:  077-77777777");
			a.show();
			// logout the user from DB
			ClientMenuController.clientControl
					.accept(new Message(MessageType.logout, LoginFrameController.user.getUserName()));
		}
	}

	/**
	 * start the LoginFrame
	 * 
	 * @param primaryStage
	 * @throws IOException
	 */
	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Client >> Login");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/LoginFrame.fxml"));
		home = new Scene(root);
		primaryStage.setScene(home);

		// On pressing X (close window) the client is disconnect from server.
		primaryStage.setOnCloseRequest(e -> {
			ClientMenuController.clientControl.accept(new Message(MessageType.disconnected, ""));
			// create a PopUp message
			this.exit(null);
		});
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
		BackgroundImage image = new BackgroundImage(new Image("images/LoginFrame.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));
	}

}
