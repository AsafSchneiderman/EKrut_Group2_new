package gui;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.Region;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ShowCustomerToRegistrateController implements Initializable{
	
    private AnchorPane pane;

    @FXML
    private Label nameLbl;

    @FXML
    private Label idLbl;

    @FXML
    private Label lastNameLbl;

    @FXML
    private Label emailLbl;

    @FXML
    private Label phoneLbl;

    @FXML
    private TextField creditCardTxt;

    @FXML
    private ChoiceBox<Region> regionChoiceBox;

    @FXML
    private Button sendForApprovalBtn;

    @FXML
    private Button backBtn;

    @FXML
    void clickBack(ActionEvent event) {

    }

    @FXML
    void clickSendForApproval(ActionEvent event) {
    	String creditCardNum=creditCardTxt.getText();
    }

	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Customer");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/showCustomerToRegisterForm.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		primaryStage.show();
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
}
