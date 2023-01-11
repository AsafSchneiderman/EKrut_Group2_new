package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OnlineOrderFrameController {

    @FXML
    private MenuButton menuBtnSelectOnlineOrder;

    @FXML
    private MenuItem menuItemPickUp;

    @FXML
    private MenuItem menuItemDelivery;

    @FXML
    private Label lblDeliveryAddr;

    @FXML
    private TextField txtDeliveryAddr;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnContinue;

    @FXML
    private TableView<?> vendingMachineTable;

    @FXML
    private TableColumn<?, ?> machineNumCol;

    @FXML
    private TableColumn<?, ?> locationCol;

    @FXML
    void SelectVendingMachine(ActionEvent event) {

    }

    @FXML
    void SetDeliveryAddress(ActionEvent event) {

    }

    @FXML
    void backToHomePage(ActionEvent event) {

    }

    @FXML
    void continueToOrder(ActionEvent event) {

    }

	public void start(Stage customerStage) throws IOException {
		ClientMenuController.clientStage = customerStage;
		Parent root = FXMLLoader.load(getClass().getResource("/gui/OnlineOrderFrame.fxml"));
		Scene home = new Scene(root);
		customerStage.setScene(home);
		customerStage.show();
		
	}

}
