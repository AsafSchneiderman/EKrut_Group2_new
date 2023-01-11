package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.OrderToDeliveryDetails;

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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DeliveryWorkerFrameController implements Initializable {

    @FXML
    private TableColumn<OrderToDeliveryDetails, String> CustomerRecivedOrderCol;

    @FXML
    private TableColumn<OrderToDeliveryDetails, String> addrCol;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnUpdateStatusOrder;

    @FXML
    private TableColumn<OrderToDeliveryDetails, String> dateCol;

    @FXML
    private TableColumn<OrderToDeliveryDetails, String> deliveryRecievedDeliveryCol;

    @FXML
    private TableColumn<OrderToDeliveryDetails, String> orderIdCol;


    @FXML
    private TableView<OrderToDeliveryDetails> tblViewDelivery;

    @FXML
    private Label welcomeWorkerLbl;

    @FXML
    void exit(ActionEvent event) {

    }

    @FXML
    void updateOrderStatusToDelivery(ActionEvent event) {

    }

    public void start(Stage primaryStage) throws IOException{
    	
    	
    	
    	ClientMenuController.clientStage = primaryStage;
    	Parent root = FXMLLoader.load(getClass().getResource("/gui/DeliveryWorkerFrame.fxml"));    	
    	Scene home = new Scene(root);
		primaryStage.setScene(home);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tblViewDelivery.setEditable(true);
		
		orderIdCol.setCellValueFactory(new PropertyValueFactory<OrderToDeliveryDetails,String>("orderId"));
		addrCol.setCellValueFactory(new PropertyValueFactory<OrderToDeliveryDetails,String>("addressToDelivey"));
		dateCol.setCellValueFactory(new PropertyValueFactory<OrderToDeliveryDetails,String>("date"));
		deliveryRecievedDeliveryCol.setCellValueFactory(new PropertyValueFactory<OrderToDeliveryDetails,String>("btnDeliverAccept"));
		CustomerRecivedOrderCol.setCellValueFactory(new PropertyValueFactory<OrderToDeliveryDetails,String>("btnOrderIsDone"));
		
ObservableList<OrderToDeliveryDetails> tvObservableList = FXCollections.observableArrayList();
    	
    	tvObservableList.addAll(new OrderToDeliveryDetails("1","motzkin","28/12"),
    										new OrderToDeliveryDetails("2","karmiel","8/12"));
    	System.out.println("deliveryWorker - start: "+tvObservableList.get(1).getAddressToDelivey());
    	tblViewDelivery.setItems(tvObservableList);
		
		//adding buttons to table

	
	/*	Callback<TableColumn<OrderToDeliveryDetails, String>, TableCell<OrderToDeliveryDetails, String>> cellFactory = new Callback<TableColumn<OrderToDeliveryDetails, String>, TableCell<OrderToDeliveryDetails, String>>() {

			@Override
			public TableCell<OrderToDeliveryDetails, String> call(final TableColumn<OrderToDeliveryDetails, String> param) {
				final TableCell<OrderToDeliveryDetails, String> cell  = new TableCell<OrderToDeliveryDetails, String>() {

                    private final Button btn = new Button("Accept");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	OrderToDeliveryDetails data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                        });
                    }
                    @Override
                    public void updateItem(String item, boolean empty) {
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

		deliveryRecievedDeliveryCol.setCellFactory(cellFactory);*/
		}

}
