package gui;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import Entities.ButtonForUsersToSignup;
import Entities.Message;
import Entities.MessageType;
import Entities.UsersToRegister;
import controller.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
public class RegistrationRequestsForRegionManagerController implements Initializable {

	private static Message msg; 
	public static ArrayList<UsersToRegister> userList = new ArrayList<>();
	public static int userNum=0;
	public static RegistrationRequestDetailsController requestDetails;

	 @FXML
	    private AnchorPane pane;

	    @FXML
	    private TableView<ButtonForUsersToSignup> userTable;

	    @FXML
	    private TableColumn<ButtonForUsersToSignup, String> userIDCol;

	    @FXML
	    private TableColumn<ButtonForUsersToSignup, String> firstNameCol;

	    @FXML
	    private TableColumn<ButtonForUsersToSignup, String> lastNameCol;

	    @FXML
	    private TableColumn<ButtonForUsersToSignup, Button> buttonsCol;

	    @FXML
	    private Button backBtn;

	    @FXML
	    private Button exitBtn;

	    @FXML
	    void clickOnBack(ActionEvent event) {

	    }

	    @FXML
	    void clickOnExit(ActionEvent event) {

	    }

	    public void popUpMessages() {
			// popup messages from the DB
			String message = (String) ChatClient.msgServer.getMessageData();
			if (!message.equals("")) {

				Alert a = new Alert(AlertType.INFORMATION);

				// set title
				a.setTitle("EKRUT Messages");
				// set header text
				a.setHeaderText("You have new messages");

				// set content text
				a.setContentText(message);

				// show the dialog
				Optional<ButtonType> result = a.showAndWait();
				if (result.get() == ButtonType.OK)
					// update the messages status of the region manager to read
					ClientMenuController.clientControl
							.accept(new Message(MessageType.update_messagesStatus, LoginFrameController.user.getUserID()));
			}
		}

	    
		public void start(Stage primaryStage) throws IOException {

			ClientMenuController.clientStage = primaryStage;
			primaryStage.setTitle("Ekrut - Region Manager >> View Requests");
			Parent root = FXMLLoader.load(getClass().getResource("/gui/RegistarationRequestsForApprovalFrame.fxml"));
			Scene home = new Scene(root);
			primaryStage.setScene(home);

		// On pressing X (close window) the user logout from system and the client is
		// disconnect from server.
		primaryStage.setOnCloseRequest(e -> {
			msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
			ClientMenuController.clientControl.accept(msg);
			ClientMenuController.clientControl
					.accept(new Message(MessageType.disconnected, LoginFrameController.user.getUserName()));
			// create a PopUp message
			PopUpMessageFrameController popUpMsgController = new PopUpMessageFrameController();

			try {
				popUpMsgController.start(ClientMenuController.clientStage);
				popUpMsgController.closeMsg(3000);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		primaryStage.show();
		this.popUpMessages(); // show new messages
	}
				@Override
		public void initialize(URL location, ResourceBundle resources) {
			// initialize the background image
			BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
					false);
			BackgroundImage image = new BackgroundImage(new Image("images/RegistrationRequestsBackground.png"), BackgroundRepeat.NO_REPEAT,
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
			pane.setBackground(new Background(image));
			
			//initialize the table 
			userTable.setEditable(true);
			userIDCol.setCellValueFactory(new PropertyValueFactory<ButtonForUsersToSignup,String>("id"));
			firstNameCol.setCellValueFactory(new PropertyValueFactory<ButtonForUsersToSignup,String>("firstName"));
			lastNameCol.setCellValueFactory(new PropertyValueFactory<ButtonForUsersToSignup,String>("lastName"));
			buttonsCol.setCellValueFactory(new PropertyValueFactory<ButtonForUsersToSignup,Button>("btnShow"));
			ObservableList<ButtonForUsersToSignup> tvObservableList = FXCollections.observableArrayList();
			System.out.println(LoginFrameController.user.getRegion());//**********************************
			msg=new Message(MessageType.showRegistrationRequests, LoginFrameController.user.getRegion());
			ClientMenuController.clientControl.accept(msg);
			try {
				Thread.sleep(1000);
				System.out.println(ChatClient.msgServer.getMessageData().toString());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			userList = (ArrayList<UsersToRegister>) ChatClient.msgServer.getMessageData();
			for (UsersToRegister row : userList)
			{
				final Button show=new Button("show user");
				show.setOnAction((ActionEvent event)->{
					userNum=userList.indexOf(row);
					//clickOnShowUser(event);
					requestDetails = new RegistrationRequestDetailsController();
					try {
						requestDetails.start(ClientMenuController.clientStage);
					} catch (IOException e) {

						e.printStackTrace();
					}//  send to UI*/
				});
				ButtonForUsersToSignup tempList = new ButtonForUsersToSignup(row.getId(),row.getFirstName(),row.getLastName(),show);
				tvObservableList.add(tempList);
			}

			userTable.setItems(tvObservableList);
			
		}
	}

