package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Entities.Message;
import Entities.MessageType;
import Entities.RegionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class ClientActivityReportViewController implements Initializable{
	
	private static ObservableList<String> institutionList = FXCollections.observableArrayList("Select location");
    @FXML
    private BarChart<String, String> BarChartClientActivity;

    
    @FXML
    private CategoryAxis categoryAxisProductsAmount;
    

    @FXML
    private NumberAxis numberAxisClientsAmount;
    
    @FXML
    private ChoiceBox<String> cbInstitution;


    @FXML
    void BackToPreviosePage(ActionEvent event) {
    	ReportSearchFrameController reportSearchFrameController = new ReportSearchFrameController();
		try {
			reportSearchFrameController.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void start(Stage primaryStage) throws IOException {
    	ClientMenuController.clientStage = primaryStage;		
    	primaryStage.setTitle("Ekrut - Client Connection");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ClientActivityFrame.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		primaryStage.show();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Message msg = new Message(MessageType.Get_locations, ((RegionManager)LoginFrameController.user).getRegion());
		ClientMenuController.clientControl.accept((Object) msg);
		
		cbInstitution.setItems(institutionList);	
		cbInstitution.setValue("Select location");
	}

	public static void setInstitutionList(ArrayList<String> locations) {
		institutionList.addAll(locations);
	}
}
