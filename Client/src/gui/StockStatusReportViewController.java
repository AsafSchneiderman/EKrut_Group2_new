package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entities.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

public class StockStatusReportViewController implements Initializable {

	@FXML
	private BarChart<String, Integer> BarChartStockPerMachine;

	@FXML
	private CategoryAxis categoryAxisProduct;

	@FXML
	private ChoiceBox<String> cbInstitution;

	@FXML
	private NumberAxis numberAxisAmount;
	
	public static ObservableList<String> institutionList = FXCollections.observableArrayList();
	private static Series<String, Integer> series;
	
	@FXML
	void BackToPreviosePage(ActionEvent event) {

	}


	public void start(Stage clientStage, List<Report> selectedReports) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
	/** sets the data of the barChart**/
	public void setStockPerMachineData() {
		
	}

}
