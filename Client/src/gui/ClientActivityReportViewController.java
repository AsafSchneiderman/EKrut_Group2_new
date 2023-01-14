package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Entities.ClientActivityReport;
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
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class ClientActivityReportViewController implements Initializable{
	
    @FXML
    private BarChart<String, Integer> BarChartClientActivity;

    @FXML
    private CategoryAxis categoryAxisProductsAmount;
    
    @FXML
    private NumberAxis numberAxisClientsAmount;
    
    private static ClientActivityReport clientActivityReport;
    private static Series<String, Integer> series;

    @FXML
    void BackToPreviosePage(ActionEvent event) {
    	ReportSearchFrameController reportSearchFrameController = new ReportSearchFrameController();
		try {
			reportSearchFrameController.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void start(Stage primaryStage, ClientActivityReport selected) throws IOException {
    	clientActivityReport = selected;
    	ClientMenuController.clientStage = primaryStage;
    	primaryStage.setTitle("Ekrut - Client");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ClientActivityReportView.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		primaryStage.show();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		series = clientActivityReport.getGraph();
		BarChartClientActivity.getData().add(series);
		BarChartClientActivity.setTitle("Client Activity ("+clientActivityReport.getMonth()+"-"+clientActivityReport.getYear()+")");
	}

	
}
