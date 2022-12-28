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
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

public class StockStatusReportViewController implements Initializable{

    @FXML
    private BarChart<?, ?> BarChartStockByMachine;

    @FXML
    private Button BntBack;

    @FXML
    private MenuButton BntChooseMachine;

    @FXML
    private CategoryAxis categoryAxisProduct;

    @FXML
    private NumberAxis numberAxisAmount;

    @FXML
    void BackToPreviosePage(ActionEvent event) {

    }

    @FXML
    void chooseMachine(ActionEvent event) {

    }

	public void start(Stage clientStage, Report[] selectedReports) {
		
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
