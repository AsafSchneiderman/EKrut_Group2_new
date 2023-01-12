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
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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
	public static ObservableList<ProductForOrder> tvObservableList = FXCollections.observableArrayList();
	public static ObservableList<OrderProductsForTbl> cartObservableList = FXCollections.observableArrayList();
	//public static ObservableList<String>tempForProducts;

	@FXML
	private AnchorPane pane;
    @FXML
    private TableView<ProductForOrder> tblProducts;

    @FXML
    private TableView<OrderProductsForTbl> tblCart;
    @FXML
    private TableColumn<OrderProductsForTbl, ImageView> imgSelectedProdCol;
    @FXML
    private TableColumn<OrderProductsForTbl, String> colProductInCart;

    @FXML
    private TableColumn<OrderProductsForTbl, String> colQuantityInCart;


    @FXML
    private TableColumn<OrderProductsForTbl, Button> addProdBntCol;
    

    @FXML
    private TableColumn<OrderProductsForTbl, Button> subProdBntCol;

    @FXML
    private TableColumn<OrderProductsForTbl, String> priceSelProdCol;


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
    private TableColumn<ProductForOrder, ImageView> colProductImg;
    @FXML
    private TableColumn<ProductForOrder, String> bntColAddCart;
    @FXML
    private ImageView imgForIcon;
    
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
    			tblCart.setEditable(true);
    			Image cartIcone = new Image("images/addToBasket.png");
    			imgForIcon.setImage(cartIcone);
    			imgForIcon.setFitWidth(50);
    			imgForIcon.setFitHeight(50);
    			colProductImg.setCellValueFactory(new PropertyValueFactory<ProductForOrder, ImageView>("imgSrc"));
    			colNameOfProduct.setCellValueFactory(new PropertyValueFactory<ProductForOrder, String>("productName"));
    			colPriceOfProduct.setCellValueFactory(new PropertyValueFactory<ProductForOrder, String>("price"));
    			bntColAddCart.setCellValueFactory(new PropertyValueFactory<ProductForOrder, String>("bntToAdd"));
    			
    			imgSelectedProdCol.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, ImageView>("imgSrc"));
    			colProductInCart.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, String>("productName"));
    			colQuantityInCart.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, String>("quantity"));
    			addProdBntCol.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, Button>("bntToAdd"));
    			subProdBntCol.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, Button>("bntToSub"));
    			priceSelProdCol.setCellValueFactory(new PropertyValueFactory<OrderProductsForTbl, String>("price"));
    			
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
    				addCartBnt.setOnAction(e -> {
    					
    					if(Integer.parseInt(row.getStockQuantity()) == 0)
    					{
    						Label stockAlert = new Label();
    						stockAlert.setText("Out Of Stock");
    						stockAlert.setVisible(true);
    					}
    					else
    					{
    						ImageView imgForCart = new ImageView();
    						imgForCart.setImage(pic);
    						imgForCart.setFitWidth(50);
    						imgForCart.setFitHeight(50);
    						String stockTempStr = row.getStockQuantity();
    						int stockNumTemp = Integer.parseInt(stockTempStr);
    						stockNumTemp = stockNumTemp - 1;
    						stockTempStr = String.valueOf(stockNumTemp);
    						row.setStockQuantity(stockTempStr);
    						Button addQuantity = new Button("+");
        					Button subQuantity = new Button("-");
        					OrderProductsForTbl toCartAdd;
        					OrderProductsForTbl toCart = new OrderProductsForTbl(row.getProductName(),row.getPrice(), "1",imgForCart,addQuantity,subQuantity);
        					addQuantity.setOnAction(a->{
        						
        						//new handleAddToCartService(toCart).start();
        					
        						
        						if(Integer.parseInt(row.getStockQuantity()) == 0)
            					{
            						Label stockAlert = new Label();
            						stockAlert.setText("Out Of Stock");
            						stockAlert.setVisible(true);
            					}
        						else
        						{
        							String tempQuantityStr = toCart.getQuantity();
        							int tempQuantityNum = Integer.parseInt(tempQuantityStr);
        							tempQuantityNum = tempQuantityNum + 1;
        							tempQuantityStr = String.valueOf(tempQuantityNum);
        							String stockTempStr2 = row.getStockQuantity();
            						int stockNumTemp2 = Integer.parseInt(stockTempStr2);
            						stockNumTemp2 = stockNumTemp2 - 1;
            						stockTempStr2 = String.valueOf(stockNumTemp2);
            						row.setStockQuantity(stockTempStr2);
            						toCart.setQuantity(tempQuantityStr);
        							
        						}
        						
        					});
        					
        					subQuantity.setOnAction(b->{
        						
        						if(Integer.parseInt(toCart.getQuantity()) == 1)
        						{
        							
        							String stockTempStr1 = row.getStockQuantity();
            						int stockNumTemp1 = Integer.parseInt(stockTempStr1);
            						stockNumTemp1 = stockNumTemp1 + 1;
            						stockTempStr1 = String.valueOf(stockNumTemp1);
            						row.setStockQuantity(stockTempStr1);
            						cartObservableList.remove(toCart);
        						}
        						else
        						{
        							String tempQuantityStr = toCart.getQuantity();
        							int tempQuantityNum = Integer.parseInt(tempQuantityStr);
        							tempQuantityNum = tempQuantityNum - 1;
        							tempQuantityStr = String.valueOf(tempQuantityNum);
        							String stockTempStr2 = row.getStockQuantity();
            						int stockNumTemp2 = Integer.parseInt(stockTempStr2);
            						stockNumTemp2 = stockNumTemp2 + 1;
            						stockTempStr2 = String.valueOf(stockNumTemp2);
            						row.setStockQuantity(stockTempStr2);
            						toCart.setQuantity(tempQuantityStr);
        						}
        						
        					});
        					
        					
        					
        					cartObservableList.add(toCart);
    					}
    					
    				});
    				tblCart.setItems(cartObservableList);
    				ProductForOrder tempList = new ProductForOrder(row.getProductName(),row.getPrice(),img,addCartBnt);
    				tvObservableList.add(tempList);
    			}

    			tblProducts.setItems(tvObservableList);
			 	
    }
    
    
 
    
    
    /*
     * private static class handleDbService extends Service<String> {

		private handleDbService(Label lblAlert) {
			setOnSucceeded(new EventHandler<WorkerStateEvent>() {

				@Override
				public void handle(WorkerStateEvent event) {
					lblAlert.setVisible(true);
					lblAlert.setText((String) event.getSource().getValue());
				}
			});
		}

		@Override
		protected Task<String> createTask() {

			return new Task<String>() {

				@Override
				protected String call() throws Exception {
					ClientMenuController.clientControl.accept((Object) msg);
					Thread.sleep(500);
					String data = (String) ChatClient.msgServer.getMessageData();
					if (data.equals("Wrong_Input"))
						return "Wrong user name or password!";
					else if (data.equals("Already_logged_in"))
						return "User already logged in";
					else {
						String[] userData = data.split("#"); // Export user data
						user = new User(userData[0], userData[1], userData[2], userData[3], userData[4], userData[5],
								userData[6], userData[7], userData[8], Integer.valueOf(userData[9]));
					}
					Thread.sleep(500);
					return "";
				}

			};
		}
	}
     */
    
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
/**
 * 
 * 
 * @param event - > costumer want to checkout order
 * The button switch to new frame -> invoice frame
 * it sends costumer order to invoice to update database
 */
    @FXML
    void checkOutOrder(ActionEvent event) {
    	confirmOrderFrame = new ConfirmOrderFrameController(tvObservableList, cartObservableList);
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
	
	
	

    
 

		
}


