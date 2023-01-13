package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.OrdersReport;
import Entities.Report;
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
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OrderReportViewController implements Initializable{


    @FXML
    private BarChart<String, Integer> BarChartSalesPerInstitution;

    @FXML
    private CategoryAxis categoryAxisInstitutions;

    @FXML
    private NumberAxis numberAxisSales;

    private static Series<String, Integer> series1;
    private static Series<String, Integer> series2;
    private static OrdersReport orderReport;
    // TODO add 
    
    
    @FXML
    void BackToPreviosePage(ActionEvent event) {
    	ReportSearchFrameController reportSearchFrameController = new ReportSearchFrameController();
		try {
			reportSearchFrameController.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public void start(Stage primaryStage, OrdersReport selectedReport) throws IOException{
		orderReport = selectedReport;
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Client");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/OrderReportView.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		primaryStage.show();
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		series1 = orderReport.getGraph("local");
		series2 = orderReport.getGraph("pickup");
		BarChartSalesPerInstitution.setTitle("Number of orders per vending machine ("+orderReport.getMonth()+" - "+orderReport.getYear()+")");
		BarChartSalesPerInstitution.getData().addAll(series1, series2);
	}

}
