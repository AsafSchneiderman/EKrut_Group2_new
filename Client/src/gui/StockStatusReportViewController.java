package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entities.Report;
import Entities.StockStatusReport;
import javafx.beans.value.ObservableValue;
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
	
	public static ObservableList<String> machineLocationsList = FXCollections.observableArrayList();
	private static Series<String, Integer> series;
	private static StockStatusReport stockStatusReport;
	
	@FXML
	void BackToPreviousPage(ActionEvent event) {
		
		ReportSearchFrameController reportSearchFrameController = new ReportSearchFrameController();
		try {
			reportSearchFrameController.start(ClientMenuController.clientStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void start(Stage primaryStage, StockStatusReport selectedReport) throws IOException{
		stockStatusReport = selectedReport;
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Client");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/StockStatusReportView.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbInstitution.getItems().clear();
		machineLocationsList.addAll(stockStatusReport.getVendingMachinesLocations());
		cbInstitution.setItems(machineLocationsList);
		cbInstitution.getSelectionModel().selectedItemProperty()
		.addListener((ObservableValue<? extends String> locationList, String oldLocation, String newLocation) -> {
			BarChartStockPerMachine.getData().removeAll(series);
			loadStockStatusOfMachine(newLocation);
		});
		cbInstitution.setValue( machineLocationsList.get(0));
	}
	
	private void loadStockStatusOfMachine(String location) {
		BarChartStockPerMachine.setTitle("Stock status of vending machine at "+location+" ("+stockStatusReport.getMonth()+" - "+stockStatusReport.getYear()+")");
		series = stockStatusReport.getGraph(location);
		BarChartStockPerMachine.getData().add(series);
	}
}
