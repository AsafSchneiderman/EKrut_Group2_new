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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

public class OnlineOrderFrameController implements Initializable{
	
	private static ArrayList<VendingMachine> vendingMachines = new ArrayList<>(); // list of vending machines in the DB

	public static Message msg;
	public static String machine;
	
    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblDeliveryAddr;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnContinue;

    @FXML
    private ImageView imgIcone;

    @FXML
    private TextField txtFldStreet;

    @FXML
    private Label lblCity;

    @FXML
    private Label lblStreet;

    @FXML
    private ComboBox<String> cmbBoxVendingMachine;

    @FXML
    private Label lblMachine;

    @FXML
    private ComboBox<String> cmbCity;

    @FXML
    private RadioButton rduPickup;

    @FXML
    private RadioButton rduDelivery;
	private ToggleGroup tg = new ToggleGroup(); // create a toggle group

    @FXML
    void SelectVendingMachine(ActionEvent event) {
    	
    	lblMachine.setVisible(true);
		cmbBoxVendingMachine.setVisible(true);
		cmbCity.setVisible(false);
		lblStreet.setVisible(false);
		txtFldStreet.setVisible(false);
		lblCity.setVisible(false);
        
        //vendingMachines = (ArrayList<VendingMachine>) ChatClient.msgServer.getMessageData();
    	ObservableList<String> list = FXCollections.observableArrayList(); // initialize the comboBox
    	for (VendingMachine row : vendingMachines)
    		list.add(row.getLocation());

    	cmbBoxVendingMachine.setItems(list);
    	cmbBoxVendingMachine.setValue(list.get(0));
    	
    	machine = cmbBoxVendingMachine.getValue();

    }

    @FXML
    void SetDeliveryAddress(ActionEvent event) {
    	lblMachine.setVisible(false);
		cmbBoxVendingMachine.setVisible(false);
		cmbCity.setVisible(true);
		lblStreet.setVisible(true);
		txtFldStreet.setVisible(true);
		lblCity.setVisible(true);
		ObservableList<String> list = FXCollections.observableArrayList("Haifa","Karmiel","TelAviv"); // initialize the comboBox
		cmbCity.setItems(list);
		cmbCity.setValue(list.get(0));
    	machine = "warehouse";
    	

    }

    
   

    @FXML
    void backToHomePage(ActionEvent event) {

    }

    @FXML
    void continueToOrder(ActionEvent event) {
    	
    	
    		
    	
    	
    	OrderFrameController order = new OrderFrameController();
		try {
			order.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			e.printStackTrace();
		}

    }




	public void start(Stage customerStage) throws IOException {
		ClientMenuController.clientStage = customerStage;
		Parent root = FXMLLoader.load(getClass().getResource("/gui/OnlineOrderFrame.fxml"));
		Scene home = new Scene(root);
		customerStage.setScene(home);
		// On pressing X (close window) the user logout from system and the client is
		// disconnect from server.
customerStage.setOnCloseRequest(e -> {
	msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
	ClientMenuController.clientControl.accept(msg);
	ClientMenuController.clientControl
	.accept(new Message(MessageType.disconnected, LoginFrameController.user.getUserName()));
});
		customerStage.show();
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// initialize the background image
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/OnlineOrderFrame.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));

		rduPickup.setToggleGroup(tg);
		rduDelivery.setToggleGroup(tg);

		rduPickup.setSelected(true);

		
		cmbCity.setVisible(false);
		lblStreet.setVisible(false);
		txtFldStreet.setVisible(false);
		lblCity.setVisible(false);
		
		
		
		
	}

}
