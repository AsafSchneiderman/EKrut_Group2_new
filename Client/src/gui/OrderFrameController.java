package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class OrderFrameController {

    @FXML
    private ListView<?> lstViewCart;

    @FXML
    private VBox vBoxCart;

    @FXML
    private Pane paneLineCart;

    @FXML
    private ImageView imgProductCart;

    @FXML
    private Label lblProductNameCart;

    @FXML
    private Label lblProductPriceCart;

    @FXML
    private MenuButton menuBtnQuantityCart;

    @FXML
    private Button btnCheckOutOrder;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private Button btnCancelOrder;

    @FXML
    private ListView<?> lstViewProduct;

    @FXML
    private VBox vBoxProduct;

    @FXML
    private Pane paneLineProduct;

    @FXML
    private ImageView imgProduct;

    @FXML
    private Label lblProductDetails;

    @FXML
    private Label lblProductPrice;

    @FXML
    private MenuButton menuBtnQuantityProduct;

    @FXML
    private Button btnAddToCart;

    @FXML
    void addToCart(ActionEvent event) {

    }

    @FXML
    void cancelOrder(ActionEvent event) {

    }

    @FXML
    void checkOutOrder(ActionEvent event) {

    }

}
