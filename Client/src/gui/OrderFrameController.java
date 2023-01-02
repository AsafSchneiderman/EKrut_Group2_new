package gui;

import java.io.IOException;
import java.util.ArrayList;

import Entities.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrderFrameController {
	Order order;
	Product product;
	ArrayList<Product> productsList;
	public  static CustomerFrameController customerFrame;
	public static Stage clientStage;
	public  static ConfirmOrderFrameController confirmOrderFrame;
	public static Message msg;

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
    private Button btnCheckOutOrder;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private Button btnCancelOrder;

    @FXML
    private ListView<?> lstViewProduct;

    @FXML
    private static VBox vBoxProduct;

    @FXML
    private static Pane paneLineProduct;

    @FXML
    private static ImageView imgProduct;

    @FXML
    private static Label lblProductDetails;

    @FXML
    private static Label lblProductPrice;

    @FXML
    private static Button btnAddToCart;

    @FXML
    private Label lblQuantatyPerProduct;

    @FXML
    private Button bntAddProduct;

    @FXML
    private Button bntSubFromCart;
    
    static class lstViewProduct extends ListCell<String>
    {
    	public lstViewProduct()
    	{
    		super();
    		vBoxProduct.getChildren().addAll(paneLineProduct,imgProduct,lblProductDetails,lblProductPrice, btnAddToCart);
    		VBox.setVgrow(paneLineProduct, Priority.ALWAYS);
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
    }

    @FXML
    void addToCart(ActionEvent event) {
    	String str1, str2;
    	
    	str1 = lblProductDetails.getText();
    	
    	str2 = lblProductPrice.getText();
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
		int i = 0;
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
		Parent root = FXMLLoader.load(getClass().getResource("/gui/OrderFrame.fxml"));
		Scene home = new Scene(root);
		customerStage.setScene(home);
		customerStage.show(); 
    }
	

    @FXML
    void subQuantatyFromCart(ActionEvent event) {
    	
    	for(int i = 0 ; i < lstViewCart.getItems().size(); i++)
    	{
    		if(lblProductNameCart.getText().equals(lstViewCart.getItems().get(i).getProductName()))
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
    				
    		}
    	}
    	
    	

    }
    
    @FXML
    void addQuantatyToCart(ActionEvent event) {
    	
    	if(order.getProducts().contains(lblProductNameCart.getText()))
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
    
    	}
    	
    	

    }

		
}


