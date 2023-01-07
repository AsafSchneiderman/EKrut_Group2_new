package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
	public static ArrayList<Product> productsList = new ArrayList<>();
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

    @FXML
    private Button btnCheckOutOrder;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private Button btnCancelOrder;

    @FXML
    private TableColumn<ProductForOrder, String> colNameOfProduct;

    @FXML
    private TableColumn<ProductForOrder, String> colPriceOfProduct;
    
    @FXML
    private TableColumn<?, ?> colProductInCart;

    @FXML
    private TableColumn<?, ?> colQuantityInCart;
    @FXML
    private TableColumn<ProductForOrder, ImageView> colProductImg;
    @FXML
    private TableColumn<ProductForOrder, String> bntColAddCart;
    
    @SuppressWarnings({ "unchecked"})
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	// initialize the background image
    			BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true,
    					false);
    			BackgroundImage image = new BackgroundImage(new Image("images/orderFrameBackground.png"),
    					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
    			pane.setBackground(new Background(image));  			  			
    			tblProducts.setEditable(true);
    			
    			colProductImg.setCellValueFactory(new PropertyValueFactory<ProductForOrder, ImageView>("imgSrc"));
    			colNameOfProduct.setCellValueFactory(new PropertyValueFactory<ProductForOrder, String>("productName"));
    			colPriceOfProduct.setCellValueFactory(new PropertyValueFactory<ProductForOrder, String>("price"));
    			bntColAddCart.setCellValueFactory(new PropertyValueFactory<ProductForOrder, String>("bntToAdd"));
    			ObservableList<ProductForOrder> tvObservableList = FXCollections.observableArrayList();
    			msg = new Message(MessageType.Show_products, "");
    			ClientMenuController.clientControl.accept(msg);
    			try {
					Thread.sleep(1000);
					System.out.println(ChatClient.msgServer.getMessageData().toString());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			productsList = (ArrayList<Product>) ChatClient.msgServer.getMessageData();
    			for (Product row : productsList)
    			{
    				Image pic = new Image(row.getImgSrc());
    				ImageView img = new ImageView();
    				img.setImage(pic);
    				img.setFitWidth(50);
    				img.setFitHeight(50);
    				Button addCartBnt = new Button("Add To Cart");
    				addCartBnt.setOnAction(value);
    				ProductForOrder tempList = new ProductForOrder(row.getProductName(),row.getPrice(),img,addCartBnt);
    				tvObservableList.add(tempList);
    			}

    			tblProducts.setItems(tvObservableList);
			 	
    }
    
    
    /**
     * 
     * Callback<TableColumn<Data, Void>, TableCell<Data, Void>> cellFactory = new Callback<TableColumn<Data, Void>, TableCell<Data, Void>>() {
            @Override
            public TableCell<Data, Void> call(final TableColumn<Data, Void> param) {
                final TableCell<Data, Void> cell = new TableCell<Data, Void>() {

                    private final Button btn = new Button("Action");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Data data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        table.getColumns().add(colBtn);

     */
  
    
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


