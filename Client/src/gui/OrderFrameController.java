package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Entities.*;
import controller.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrderFrameController implements Initializable {
	Order order;
	Product product;
	static ArrayList<Product> productsList = new ArrayList<>();
	public  static CustomerFrameController customerFrame;
	public static Stage clientStage;
	public  static ConfirmOrderFrameController confirmOrderFrame;
	public static Message msg;
	//public static ObservableList<String>tempForProducts;

	@FXML
	private AnchorPane pane;
    @FXML
    private TableView<ProductForOrder> tblProducts;

    @FXML
    private TableView<?> tblCart;
   // @FXML
    //private ListView<?> lstViewCart;

   // @FXML
    //private VBox vBoxCart;

   // @FXML
   // private Pane paneLineCart;

    //@FXML
    //private ImageView imgProductCart;

    //@FXML
    //private Label lblProductNameCart;

    //@FXML
    //private Label lblProductPriceCart;


    @FXML
    private Button btnCheckOutOrder;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private Button btnCancelOrder;

    //@FXML
    //private ListView<String> lstViewProduct;

    //@FXML
    //private static VBox vBoxProduct;

   // @FXML
    //private static Pane paneLineProduct;

    //@FXML
    //private static ImageView imgProduct;

    //@FXML
   // private static Label lblProductDetails;

   // @FXML
    //private static Label lblProductPrice;

    @FXML
    private static Button btnAddToCart;

   // @FXML
   // private Label lblQuantatyPerProduct;

    @FXML
    private Button bntAddProduct;

    @FXML
    private Button bntSubFromCart;
    @FXML
    private TableColumn<ProductForOrder, String> colNameOfProduct;

    @FXML
    private TableColumn<ProductForOrder, String> colPriceOfProduct;
    
    @FXML
    private TableColumn<?, ?> colProductInCart;

    @FXML
    private TableColumn<?, ?> colQuantityInCart;
    
    /*static class lstViewProduct extends ListCell<String>
    {
    	ActionEvent event;
    	 
    	public lstViewProduct()
    	{
    		super();
    		vBoxProduct.getChildren().addAll(paneLineProduct,imgProduct,lblProductDetails,lblProductPrice, btnAddToCart);
    		VBox.setVgrow(paneLineProduct, Priority.ALWAYS);
    		btnAddToCart.setOnAction((EventHandler<ActionEvent>) event);
    	}
    	public void updateItem(String item, boolean empty)
    	{
    		super.updateItem(item, empty);
    		setText(null);
    		setGraphic(null);
    		if(item != null && !empty)
    		{
    			lblProductDetails.setText(item);
    			setGraphic(vBoxProduct);
    		}  			
    	}
    }*/
    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	
    	// initialize the background image
    			BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
    					false);
    			BackgroundImage image = new BackgroundImage(new Image("images/orderFrameBackground.png"),
    					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
    			pane.setBackground(new Background(image));  			  			
    			tblProducts.setEditable(true);
    			
    			colNameOfProduct.setCellValueFactory(new PropertyValueFactory<ProductForOrder, String>("productName"));
    			colPriceOfProduct.setCellValueFactory(new PropertyValueFactory<ProductForOrder, String>("price"));
    			ObservableList<ProductForOrder> tvObservableList = FXCollections.observableArrayList();
    			productsList = (ArrayList<Product>) ChatClient.msgServer.getMessageData();
    			for (Product row : productsList)
    			{
    				//tvObservableList.add(row);
    				ProductForOrder tempVar = new ProductForOrder(row.getProductName(), Float.toString(row.getPrice()));
    				tvObservableList.add(tempVar);	
    			}
    				
    			tblProducts.setItems(tvObservableList);   			 	
    }
    
  
	

   
    
    @FXML
    void addToCart(ActionEvent event) {
    	String str1, str2;
    	
    	//str1 = tblProducts.
    	
    	//str2 = lblProductPrice.getText();
    	float price = convertStringToFloat(str2);
    	
    	productsList.add(product);
    	float totalPrice = order.getTotalPrice();
    	
    	totalPrice = totalPrice + price;
    	
		order.setTotalPrice(totalPrice );
		if(order.getProducts() == null)
		{
			order.setProducts(str1);
		}
		else
		{
			String temp = order.getProducts();
			temp += "\n ";
			temp += product.getProductName();
			order.setProducts(temp);
		}
		int quantity;
		int i = 0;///hi
		while(order.getQuantityPerProducts(i) != 0)
		{
			i++;
		}
		order.setQuantityPerProducts(i, 1); 
		if(order.getQuantityOfProducts() == 0)
		{
			order.setQuantityOfProducts(1);
		}
		else
		{
			quantity = order.getQuantityOfProducts();
			order.setQuantityOfProducts(quantity+1);
		}
			
		
		
		
		
		//String temp, temp2, temp3;
		//temp = product.getProductName()+product.getProductCode()+temp2+temp3;
		//msg =  new Message(MessageType.Orders_list,temp);
		ClientMenuController.clientControl.accept(msg);
		

    }

    @FXML
    void cancelOrder(ActionEvent event) {
    	customerFrame = new CustomerFrameController();
    	try {
			customerFrame.start(clientStage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void checkOutOrder(ActionEvent event) {
    	confirmOrderFrame = new ConfirmOrderFrameController();
    	try {
			confirmOrderFrame.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 // Function to convert String to Float
    public static float convertStringToFloat(String str)
    {
  
        // Convert string to float
        // using valueOf() method
        return Float.valueOf(str);
    }

	public void start(Stage customerStage) throws IOException {
		ClientMenuController.clientStage = customerStage;
		ClientMenuController.clientStage.setTitle("Ekrut - Costumer >> Make an Order");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/OrderFrame.fxml"));
		Scene home = new Scene(root);
		customerStage.setScene(home);
		clientStage.setOnCloseRequest(e -> {
			msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
			ClientMenuController.clientControl.accept(msg);
		});
		customerStage.show(); 
    }
	
	
	

    @FXML
    void subQuantatyFromCart(ActionEvent event) {
    	
    	
    		/*if(lblProductNameCart.getText().equals(lstViewCart.getItems().get(i).getProductName()))
    		{
    			int num = order.getQuantityPerProducts(i);
    			if(num==1)
    			{
    				lstViewCart.getItems().remove(i);
    				float num2 = convertStringToFloat(lblProductPriceCart.getText());
       			 	float num3 = order.getTotalPrice();
       			 	num3 = num3-num2;
       			 	order.setTotalPrice(num3);
       			 	String temp = order.getProducts();
       			 	temp.replace(productsList.get(i).getProductName(), "");
       			 	order.setProducts(temp);
       			 	order.setQuantityPerProducts(i, 0);
       			 	int quantityOfProducts  = order.getQuantityOfProducts();
       			 	order.setQuantityOfProducts(quantityOfProducts-1);
    			}
    			else
    			{
    				num--;
    				order.setQuantityPerProducts(i, num);
        			productsList.get(i).setQuantity(num);
        			float num2 = convertStringToFloat(lblProductPriceCart.getText());
       			 	float num3 = order.getTotalPrice();
       			 	num3 = num3-num2;
       			 	order.setTotalPrice(num3);
       			 	int quantityOfProducts  = order.getQuantityOfProducts();
    			 	order.setQuantityOfProducts(quantityOfProducts-1);
    			}
    				
    		}*/
    	
    	


    }
    
    @FXML
    void addQuantatyToCart(ActionEvent event) {
    	
    	/*if(order.getProducts().contains(lblProductNameCart.getText()))
    	{
    		int i = 0;
    		while(!(productsList.get(i).getProductName().contains(lblProductNameCart.getText())))
    		{
    			i++;
    		}
    		int quantity = order.getQuantityPerProducts(i);
    		order.setQuantityPerProducts(i, quantity+1);
    		quantity = order.getQuantityOfProducts();
    		order.setQuantityOfProducts(quantity+1);
    		float totPrice = order.getTotalPrice();
    		float price = convertStringToFloat(lblProductPriceCart.getText());
    		totPrice = totPrice + price;
    		order.setTotalPrice(totPrice);
    
    	}*/
    	
    	

    }

		
}


