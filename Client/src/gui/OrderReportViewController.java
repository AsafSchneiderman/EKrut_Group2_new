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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

public class OrderReportViewController implements Initializable{


    @FXML
    private BarChart<String, Integer> BarChartSalesPerInstitution;

    @FXML
    private CategoryAxis categoryAxisInstitutions;

    @FXML
    private NumberAxis numberAxisSales;
    
    @FXML
    private AnchorPane pane;

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
		if (LoginFrameController.user.getRole().equals("RegionManager"))
			primaryStage.setTitle("Ekrut - Region Manager >> Menu >> Report Search >> Orders Report");
		else
			primaryStage.setTitle("Ekrut - CEO >> Menu >> Report Search >> Orders Report");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/OrderReportView.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		primaryStage.show();
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), true, true, true, false);
		Image backgroundImage = null;
		if (LoginFrameController.user.getRole().equals("RegionManager"))
			backgroundImage = new Image("images/RegionManagerFrame.png");
		else
			backgroundImage = new Image("images/CEOFrame.png");
		BackgroundImage image = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
														BackgroundPosition.DEFAULT, backgroundSize);
		
		pane.setBackground(new Background(image));
		
		series1 = orderReport.getGraph("local");
		series2 = orderReport.getGraph("pickup");
		BarChartSalesPerInstitution.setTitle("Number of orders per vending machine ("+orderReport.getMonth()+" - "+orderReport.getYear()+")");
		BarChartSalesPerInstitution.getData().addAll(series1, series2);
	}

}
