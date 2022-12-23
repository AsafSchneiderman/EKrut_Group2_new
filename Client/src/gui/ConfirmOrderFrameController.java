package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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

}
