package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfirmOrderFrameController {

    @FXML
    private VBox vBoxCart;

    @FXML
    private Pane paneLineCart;

    @FXML
    private ImageView imgProductCart;

    @FXML
    private Label lblProductNameCart;

    @FXML
    private Label lblQuantityCart;

    @FXML
    private Label lblProductPriceCart;

    @FXML
    private Button btnConfirmOrder;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private Button btnCancelOrder;

    @FXML
    void cancelOrder(ActionEvent event) {

    }

    @FXML
    void confirmOrder(ActionEvent event) {

    }

	public void start(Stage customerStage) throws IOException {
		ClientMenuController.clientStage = customerStage;
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ConfirmOrderFrame.fxml"));
		Scene home = new Scene(root);
		customerStage.setScene(home);
		customerStage.show(); 
    }
		
	

}
