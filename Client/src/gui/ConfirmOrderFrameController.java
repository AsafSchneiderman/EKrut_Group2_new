package gui;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import Entities.Message;
import Entities.MessageType;
import Entities.Order;
import Entities.OrderProductsForTbl;
import Entities.Product;
import Entities.ProductForOrder;
import Entities.Time;
import controller.ChatClient;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import Entities.*;
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter; 

public class ConfirmOrderFrameController implements Initializable {
	public static Stage clientStage;
	public static Message msg, msg2, msg3;
	public static Order order, order2;
	public static ArrayList<Order>orderList = new ArrayList<>();;
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
	private int counterForProducts;
	float totPrice;
	java.sql.Date date;
	SimpleDateFormat formatter;
	String strDate;
    @FXML
    private Text txtTimer;

    public ConfirmOrderFrameController(ObservableList<ProductForOrder> tvObservableList,
			ObservableList<OrderProductsForTbl> cartObservableList, String location, Label lblTotalPrice, int counterForProducts) {
		super();
		this.tvObservableList = tvObservableList;
		this.cartObservableList = cartObservableList;
		this.location = location;
		this.lblTotalPrice = lblTotalPrice;
		this.counterForProducts=counterForProducts;
	}

	@FXML
    void cancelOrder(ActionEvent event) {
		
		ClientMenuController.clientStage.setScene(LoginFrameController.home);
		// Logout
		msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
		ClientMenuController.clientControl.accept(msg);

    }

    @FXML
    void confirmOrder(ActionEvent event) {
    	msg = new Message(MessageType.Orders_list, "");
		ClientMenuController.clientControl.accept(msg);
		try {
			Thread.sleep(1000);
			System.out.println(ChatClient.msgServer.getMessageData().toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		orderList = (ArrayList<Order>) ChatClient.msgServer.getMessageData();
		int orderNum;
		int[] arrOrderNums = new int [orderList.size()];
		for(int i=0; i<orderList.size(); i++)
		{
			arrOrderNums[i] = orderList.get(i).getOrderNum();
		
		}
		 Arrays.sort(arrOrderNums);
		 orderNum = arrOrderNums[arrOrderNums.length-1]+1;

    	if(LoginFrameController.user.getRole().equals("ClubMember"))
    	{
    		if((this.location.equals("warehouse")))
    		{
    			  long millis=System.currentTimeMillis();  
    		      
    			    // creating a new object of the class Date  
    			     date = new java.sql.Date(millis);       
    			     formatter = new SimpleDateFormat("MM-dd-yyyy");  
    			     strDate = formatter.format(date);
    			     totPrice = convertStringToFloat(this.lblTotalPrice.getText());
    			     
    			order = new Order(orderNum,this.location,strDate,"placed",LoginFrameController.user.getUserID() ,totPrice,"delivery",this.counterForProducts);
    			msg3 = new  Message(MessageType.addOrder,order);
       			ClientMenuController.clientControl.accept(msg3);
    			
    		}
    		else
    		{
    			 long millis=System.currentTimeMillis(); 
    			// creating a new object of the class Date  
			     date = new java.sql.Date(millis);       
			     formatter = new SimpleDateFormat("MM-dd-yyyy");  
			     strDate = formatter.format(date);
			     totPrice = convertStringToFloat(this.lblTotalPrice.getText());
			     String type;
			     if(ClientMenuController.config.equals("OL"))
			    {
			    	 type = "pickup";
			    }
			     else
			     {
			    	 type = "local";
			     }
    			order = new Order(orderNum,this.location,strDate,"placed",LoginFrameController.user.getUserID(),totPrice, type,this.counterForProducts);
    			msg2 = new Message(MessageType.updateProductStock,OrderFrameController.productsList);
       			ClientMenuController.clientControl.accept(msg2);
    			msg3 = new  Message(MessageType.addOrder,order);
       			ClientMenuController.clientControl.accept(msg3);
    		
    		}
    	}
    	else
    	{
    		if((this.location.equals("warehouse")))
    		{
    			 long millis=System.currentTimeMillis(); 
     			// creating a new object of the class Date  
 			     date = new java.sql.Date(millis);       
 			     formatter = new SimpleDateFormat("MM-dd-yyyy");  
 			     strDate = formatter.format(date);
 			     
 			    order = new Order(orderNum,this.location,strDate,"placed",LoginFrameController.user.getUserID() ,totPrice,"delivery",this.counterForProducts);
 			   msg3 = new  Message(MessageType.addOrder,order);
 	   			ClientMenuController.clientControl.accept(msg3);
    		}
    		else
    		{
    			 long millis=System.currentTimeMillis(); 
     			// creating a new object of the class Date  
 			     date = new java.sql.Date(millis);       
 			     formatter = new SimpleDateFormat("MM-dd-yyyy");  
 			     strDate = formatter.format(date);
 			    String type;
			     if(ClientMenuController.config.equals("OL"))
			    {
			    	 type = "pickup";
			    }
			     else
			     {
			    	 type = "local";
			     }
   			order = new Order(orderNum,this.location,strDate,"placed",LoginFrameController.user.getUserID(),totPrice, type,this.counterForProducts);
   			
   			msg2 = new Message(MessageType.updateProductStock,OrderFrameController.productsList);
   			ClientMenuController.clientControl.accept(msg2);
   			
   			msg3 = new  Message(MessageType.addOrder,order);
   			ClientMenuController.clientControl.accept(msg3);
   			
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
		
		Time time = new Time("00:15:00");
		  txtTimer.setText(time.getCurrentTime());
		    Timeline timeline = new Timeline(
		            new KeyFrame(Duration.seconds(1),e ->{
		            
		            		if(time.oneSecondPassed())
		            		{
		            			ClientMenuController.clientStage.setScene(LoginFrameController.home);
		            			// Logout
		            			msg = new Message(MessageType.logout, LoginFrameController.user.getUserName());
		            			ClientMenuController.clientControl.accept(msg);
		            		}
		                     txtTimer.setText(time.getCurrentTime());
		            }));
      timeline.setCycleCount(Timeline.INDEFINITE);
      timeline.play();
	}
		
	

}
