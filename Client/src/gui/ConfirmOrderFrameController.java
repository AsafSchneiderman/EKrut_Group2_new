package gui;

import java.io.IOException;

import Entities.OrderProductsForTbl;
import Entities.ProductForOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfirmOrderFrameController {
	 @FXML
	    private AnchorPane pane;

	    @FXML
	    private Button btnConfirmOrder;

	    @FXML
	    private Button btnCancelOrder;

	    @FXML
	    private TableView<?> tlbInvoice;

	    @FXML
	    private TableColumn<?, ?> colImgProd;

	    @FXML
	    private TableColumn<?, ?> colQuantityProd;

	    @FXML
	    private TableColumn<?, ?> colProdName;

	    @FXML
	    private TableColumn<?, ?> colProdPrice;

	    @FXML
	    private ImageView imgIcone;

	    @FXML
	    private Label lblTotalPrice;
	private  ObservableList<ProductForOrder> tvObservableList;
	private ObservableList<OrderProductsForTbl> cartObservableList;
	

    public ConfirmOrderFrameController(ObservableList<ProductForOrder> tvObservableList,
			ObservableList<OrderProductsForTbl> cartObservableList) {
		super();
		this.tvObservableList = tvObservableList;
		this.cartObservableList = cartObservableList;
	}

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
