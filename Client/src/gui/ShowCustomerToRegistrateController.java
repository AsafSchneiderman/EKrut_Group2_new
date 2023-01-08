package gui;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ShowCustomerToRegistrateController {
	@FXML
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
    private ChoiceBox<?> regionChoiceBox;

    @FXML
    private Button sendForApprovalBtn;

    @FXML
    private Button backBtn;

    @FXML
    void clickBack(ActionEvent event) {

    }

    @FXML
    void cliclSendForApproval(ActionEvent event) {

    }

	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Customer");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/showCustomerToRegistrateForm.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		primaryStage.show();
		
	}
}
