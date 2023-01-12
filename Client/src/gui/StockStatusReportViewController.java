package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entities.Report;
import Entities.StockStatusReport;
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
		ReportSearchFrameController reportSearchFrameController = new ReportSearchFrameController();
		try {
			reportSearchFrameController.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void start(Stage primaryStage, StockStatusReport selectedReport) throws IOException{
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Client");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/StockStatusReportView.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
	/** sets the data of the barChart**/
	public void setStockPerMachineData() {
		
	}

}
