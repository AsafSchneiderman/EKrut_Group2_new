package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Entities.Message;
import Entities.MessageType;
import Entities.VendingMachine;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

public class InstallFrameController implements Initializable {

	@FXML
	private AnchorPane pane;

	@FXML
	private RadioButton radioBtnOL;

	@FXML
	private RadioButton radioBtnEK;

	@FXML
	private Button btnInstall;

	@FXML
	private ComboBox<String> cmbBoxVendingMachine;

	@FXML
	private Label lblMsg;

	private static ArrayList<VendingMachine> vendingMachines = new ArrayList<>(); // list of vending machines in the DB

	private ToggleGroup tg = new ToggleGroup(); // create a toggle group

	private String config = ""; // (EK/OL)

	@FXML
	void chooseEK(ActionEvent event) {

		lblMsg.setVisible(true);
		cmbBoxVendingMachine.setVisible(true);
		// show the vending machines
		vendingMachines = (ArrayList<VendingMachine>) ChatClient.msgServer.getMessageData();
		ObservableList<String> list = FXCollections.observableArrayList(); // initialize the comboBox
		for (VendingMachine row : vendingMachines)
			list.add(row.getLocation());

		cmbBoxVendingMachine.setItems(list);
		cmbBoxVendingMachine.setValue(list.get(0));

		config = "EK";

	}

	@FXML
	void chooseOL(ActionEvent event) {

		lblMsg.setVisible(false);
		cmbBoxVendingMachine.setVisible(false);
		config = "OL";
	}

	@FXML
	void install(ActionEvent event) {

		if (config.equals("EK")) {

			ClientMenuController.config = "EK";
			ClientMenuController.clientStage.setScene(ClientMenuController.home);
		}

		if (config.equals("OL")) {

			ClientMenuController.config = "OL";
			ClientMenuController.clientStage.setScene(ClientMenuController.home);
		}
		
		Alert a = new Alert(AlertType.NONE);
		 // set alert type
        a.setAlertType(AlertType.ERROR);

        // set content text
        a.setContentText("No option selected!\r\n"
        		+ "Select a configuration and click the Install button.");

        // show the dialog
        a.show();

	}

	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Client >> Install");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/InstallFrame.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);

		// On pressing X (close window) the client is disconnect from server.
		primaryStage.setOnCloseRequest(e -> {
			ClientMenuController.clientControl.accept(new Message(MessageType.disconnected, ""));
		});
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// initialize the background image
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/InstallFrame.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));

		radioBtnEK.setToggleGroup(tg);
		radioBtnOL.setToggleGroup(tg);

		radioBtnOL.setSelected(true);
		
		lblMsg.setVisible(false);
		cmbBoxVendingMachine.setVisible(false);
	}

}
