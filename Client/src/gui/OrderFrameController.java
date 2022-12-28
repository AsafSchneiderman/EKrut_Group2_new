package gui;

import java.io.IOException;
import java.util.ArrayList;

import Entities.Message;
import Entities.Order;
import Entities.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
    private ListView<Product> lstViewCart;

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
    private Button btnAddToCart;

    @FXML
    private Label lblQuantatyPerProduct;

    @FXML
    private Button bntAddProduct;

    @FXML
    private Button bntSubFromCart;

    @FXML
    void addToCart(ActionEvent event) {
    	String str;
    	float num;
    	str = lblProductDetails.getText();
    	product.setProductName(str);
    	str = lblProductPrice.getText();
    	num = convertStringToFloat(str);
    	product.setPrice(num);
    	productsList.add(product);
    	float totalPrice = 0;
    	for(int i=0; i<productsList.size();i++)
    	{
    		totalPrice = totalPrice+ productsList.get(i).getPrice();
    	}
		order.setTotalPrice(totalPrice );
		if(order.getProducts() == null)
		{
			order.setProducts(product.getProductName());
		}
		else
		{
			String temp = order.getProducts();
			temp += "\n ";
			temp += product.getProductName();
			order.setProducts(temp);
		}
		
		lstViewCart.getItems().addAll(product);
		String temp, temp2, temp3;
		temp2 = Float.toString(product.getPrice());
		temp3 = Float.toString(product.getQuantity());
		temp = product.getProductName()+product.getProductCode()+temp2+temp3;
		msg =  new Massage(MassageType.OrderList,temp);
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
    			int num = lstViewCart.getItems().get(i).getQuantity();
    			if(num==1)
    			{
    				lstViewCart.getItems().remove(i);
    				float num2 = productsList.get(i).getPrice();
       			 	float num3 = order.getTotalPrice();
       			 	num3 = num3-num2;
       			 	order.setTotalPrice(num3);
       			 	String temp = order.getProducts();
       			 	temp.replace(productsList.get(i).getProductName(), "");
       			 	order.setProducts(temp);
       			 	productsList.remove(i);
    			}
    			else
    			{
    				num--;
        			lstViewCart.getItems().get(i).setQuantity(num);
        			productsList.get(i).setQuantity(num);
        			float num2 = productsList.get(i).getPrice();
       			 	float num3 = order.getTotalPrice();
       			 	num3 = num3-num2;
       			 	order.setTotalPrice(num3);
    			}
    				
    		}
    	}
    	
    	

    }
    
    @FXML
    void addQuantatyToCart(ActionEvent event) {
    	
    	
    	for(int i = 0 ; i < lstViewCart.getItems().size(); i++)
    	{
    		if(lblProductNameCart.getText().equals(lstViewCart.getItems().get(i).getProductName()))
    		{
    			int num = lstViewCart.getItems().get(i).getQuantity();
    			num++;
    			lstViewCart.getItems().get(i).setQuantity(num);
    			productsList.get(i).setQuantity(num);
    			 float num2 = productsList.get(i).getPrice();
    			 float num3 = order.getTotalPrice();
    			 num3 = num3+num2;
    			 order.setTotalPrice(num3);
    			 
    			
    		}
    	}
    	
    	

    }

		
}


