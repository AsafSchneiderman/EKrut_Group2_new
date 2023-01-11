
package gui;
/////////
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
////////
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.control.TableColumn.CellEditEvent;
import entity.Subscriber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.event.EventHandler;

public class SubscriberFrameController implements Initializable
{

	@FXML
	private TableColumn<Subscriber, String> creditCardNum;

	@FXML
	private TableColumn<Subscriber, String> emailAddr;

	@FXML
	private TableColumn<Subscriber, String> firstName;

	@FXML
	private TableColumn<Subscriber, String> id;

	@FXML
	private TableColumn<Subscriber, String> lastName;

	@FXML
	private TableColumn<Subscriber, String> phoneNum;

	@FXML
	private TableColumn<Subscriber, String> subId;

	@FXML
	private TableView<Subscriber> tableSubscribers;
	
	static TableView<Subscriber> tableSub;
	
	static ArrayList<String> updateSub= new ArrayList<>();

	//@FXML
	//private TextField textFieldCreditCardUpdate;

	//@FXML
	//private TextField textFieldSubNumUpdate;

	//public static TextField subNumTextUpdate;
	//public static TextField creditTextUpdate;
	
	
	
	/*
	 * 
		;
		firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		firstNameColumn.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
			@Override
			public void handle(CellEditEvent<Person, String> event) {
				Person person = event.getRowValue();
				person.setFirstName(event.getNewValue());
			}
		});
	 * */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for(int i = 0; i < 3; i++)
		{
			updateSub.add(" ");
		}
		tableSub = tableSubscribers;
		tableSubscribers.setEditable(true);
		creditCardNum.setCellValueFactory(new PropertyValueFactory<Subscriber,String> ("CreditCardSub"));
		firstName.setCellValueFactory(new PropertyValueFactory<Subscriber,String> ("FirstNameSub"));
		lastName.setCellValueFactory(new PropertyValueFactory<Subscriber,String> ("LastNameSub"));
		emailAddr.setCellValueFactory(new PropertyValueFactory<Subscriber,String> ("EmailAddressSub"));
		phoneNum.setCellValueFactory(new PropertyValueFactory<Subscriber,String> ("PhoneNumberSub"));
		subId.setCellValueFactory(new PropertyValueFactory<Subscriber,String> ("SubscriberNumber"));
		id.setCellValueFactory(new PropertyValueFactory<Subscriber,String> ("IdSub"));
		
		creditCardNum.setCellFactory(TextFieldTableCell.forTableColumn());
		creditCardNum.setOnEditCommit(new EventHandler<CellEditEvent<Subscriber, String>>() {

			@Override
			public void handle(CellEditEvent<Subscriber, String> event) {
				Subscriber sub = event.getRowValue();
				sub.setCreditCardSub(event.getNewValue());
				
					updateSub.set(0, sub.getIdSub());
				updateSub.set(2, sub.getCreditCardSub());
				updateSub.set(1, sub.getSubscriberNumber());
				//ClientUI.clientControl.accept(updateSub);
				//db			
			}		
		});
		
		subId.setCellFactory(TextFieldTableCell.forTableColumn());
		subId.setOnEditCommit(new EventHandler<CellEditEvent<Subscriber, String>>() {

			@Override
			public void handle(CellEditEvent<Subscriber, String> event) {
				Subscriber sub = event.getRowValue();
				sub.setSubscriberNumber(event.getNewValue());
				//db
				
				updateSub.set(0, sub.getIdSub());
				updateSub.set(2, sub.getCreditCardSub());
				updateSub.set(1, sub.getSubscriberNumber());
				//ClientUI.clientControl.accept(updateSub);
			}
		});	
		
		
		ClientMenuController.clientControl.accept(new ArrayList<>());
		
	}
	
	
	public void start(Stage primaryStage) throws IOException{
		
		//primaryStage.setTitle("Ekrut - Client");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/SubscriberFrame.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		primaryStage.setOnCloseRequest(e -> { 
			ClientMenuController.clientControl.accept("disconnect");
		});
		primaryStage.show();
		//BorderPane a = new BorderPane();
		//tableSubscribers = new TableView<Subscriber>();
		
		 //creditCardNum = new TableColumn<Subscriber, String>("credit Card");
		// subId = new TableColumn<Subscriber, String>("Subscriber Number");
		//creditCardNum.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("creditCardNum"));
		//subId.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("SubscriberNumber"));
		
		//initialize(null, null);
		
		
			

		//a.setCenter(tableSubscribers);
			
	}

	//
	public void showSubToTable(ArrayList<String> msg) {
		
		ObservableList<Subscriber> sub = FXCollections.observableArrayList();
		
		for (String row : msg)
		{
			//System.out.println(row);
			String[] s = row.split("\\,");
			Subscriber e = new Subscriber(s[0], s[1], s[2],s[3] ,s[4], s[5], s[6]);
			sub.add(e);
		}
		
		tableSub.setItems(sub);

	}

	@FXML
	void UpdateDataTable(ActionEvent event) {
	
		ClientMenuController.clientControl.accept(updateSub);
	} 
}
