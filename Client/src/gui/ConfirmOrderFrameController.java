package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.Message;
import Entities.MessageType;
import Entities.Order;
import Entities.OrderProductsForTbl;
import Entities.ProductForOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfirmOrderFrameController implements Initializable {
	public static Stage clientStage;
	public static Message msg;
	public static Order order;
	 @FXML
	    private AnchorPane pane;

	    @FXML
	    private Button btnConfirmOrder;

	    @FXML
	    private Button btnCancelOrder;

	    @FXML
	    private TableView<OrderProductsForTbl> tlbInvoice;

	    @FXML
	    private TableColumn<OrderProductsForTbl, ImageView> colImgProd;

	    @FXML
	    private TableColumn<OrderProductsForTbl, String> colQuantityProd;

	    @FXML
	    private TableColumn<OrderProductsForTbl, String> colProdName;

	    @FXML
	    private TableColumn<OrderProductsForTbl, String> colProdPrice;

	    @FXML
	    private ImageView imgIcone;

	    @FXML
	    private Label lblTotalPrice;
	private  ObservableList<ProductForOrder> tvObservableList;
	private ObservableList<OrderProductsForTbl> cartObservableList;
	private String location;
	
	

    public ConfirmOrderFrameController(ObservableList<ProductForOrder> tvObservableList,
			ObservableList<OrderProductsForTbl> cartObservableList, String location, Label lblTotalPrice) {
		super();
		this.tvObservableList = tvObservableList;
		this.cartObservableList = cartObservableList;
		this.location = location;
		this.lblTotalPrice = lblTotalPrice;
	}

	@FXML
    void cancelOrder(ActionEvent event) {

    }

    @FXML
    void confirmOrder(ActionEvent event) {
    	if(LoginFrameController.user.getRole().equals("ClubMember"))
    	{
    		if((this.location.equals("warehouse")))
    		{
    			order = new Order(, this.location,,,LoginFrameController.user.getUserID(),this.lblTotalPrice );
    		}
    		else
    		{
    			order = new Order(, this.location,,,LoginFrameController.user.getUserID(),this.lblTotalPrice );
    		}
    	}
    	else
    	{
    		if((this.location.equals("warehouse")))
    		{
    			order = new Order(, this.location,,,LoginFrameController.user.getUserID(),this.lblTotalPrice );
    		}
    		else
    		{
    			order = new Order(, this.location,,,LoginFrameController.user.getUserID(),this.lblTotalPrice );
    		}
    	}

    }

	public void start(Stage customerStage) throws IOException {
		ClientMenuController.clientStage = customerStage;
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ConfirmOrderFrame.fxml"));
		ClientMenuController.clientStage.setTitle("Ekrut - Costumer >> Confirm Order");
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
	
	// Function to convert String to Float
    public static float convertStringToFloat(String str)
    {
  
        // Convert string to float
        // using valueOf() method
        return Float.valueOf(str);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// initialize the background image and Icon
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
				false);
		BackgroundImage image = new BackgroundImage(new Image("images/InvoiceBackgroundFrame.png"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
		pane.setBackground(new Background(image));  			  			
		
		tlbInvoice.setEditable(true);
		Image cartIcone = new Image("images/addToBasket.png");
		imgIcone.setImage(cartIcone);
		imgIcone.setFitWidth(50);
		imgIcone.setFitHeight(50);
		
		colImgProd.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, ImageView>("imgSrc"));
		colProdName.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, String>("productName"));
		colQuantityProd.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, String>("quantity"));
		colProdPrice.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, String>("price"));
		/*for(OrderProductsForTbl row : cartObservableList)
		{
			
			//Image pic = new Image(row.getImgSrc());
			ImageView img = new ImageView();
			img = row.getImgSrc();
			img.setFitWidth(50);
			img.setFitHeight(50);
			
			OrderProductsForTbl tempList = new OrderProductsForTbl(row.getProductName(),row.getPrice() ,row.getQuantity(), img, row.getBntToAdd(), row.getBntToSub());
		}*/
		
		// TODO Auto-generated method stub
		tlbInvoice.setItems(cartObservableList);
	}
		
	

}
