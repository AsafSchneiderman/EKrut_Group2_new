package gui;

import java.net.URL;
import java.util.ResourceBundle;

import Entities.Report;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OrderReportViewController implements Initializable{


    @FXML
    private BarChart<?, ?> BarChartSalesPerInstitution;

    @FXML
    private CategoryAxis categoryAxisInstitutions;

    @FXML
    private NumberAxis numberAxisSales;

    @FXML
    void BackToPreviosePage(ActionEvent event) {

    }

	public void start(Stage clientStage, Report firstReport) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
