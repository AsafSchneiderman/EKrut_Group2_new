package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.Message;
import Entities.MessageType;
import Entities.Report;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReportSearchFrameController implements Initializable {

	ObservableList<String> reportTypesList= FXCollections.observableArrayList("Select type", "Show all report types" , "Order", "Stock status", "Clients activity");
	
	ObservableList<String> yearsList= FXCollections.observableArrayList("Select year", "Show all years", "2022", "2023");
	
	ObservableList<String> monthsList= FXCollections.observableArrayList("Select month", "Show all months", "January", "February", "March", "April", "May", "June", "July", "August", "September","October", "November", "December");
			
	@FXML
	private Label LblReportName;

	@FXML
	private Pane paneReportRes;

	@FXML
	private ListView<String> reportViewList;

	@FXML
	private ChoiceBox<String> selectMonth;

	@FXML
	private ChoiceBox<String> selectReportType;

	@FXML
	private ChoiceBox<String> selectYear;

	@FXML
	private VBox vBoxReport;
	
	private static ListView<String> reportListView;
	private static String selectedReport = null;
	private static Report[] selectedReports;

	public void start(Stage primaryStage) throws IOException {
		ClientMenuController.clientStage = primaryStage;
		primaryStage.setTitle("Ekrut - Client");
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ReportSearchFrame.fxml"));
		Scene home = new Scene(root);
		primaryStage.setScene(home);
		primaryStage.show();
	}

	/** Opens Region Manager frame or CEO frame **/
	@FXML
	void BackToPreviousPage(ActionEvent event) {
		if (LoginFrameController.user.getRole().equals("RegionManager")) {
			RegionManagerFrameController regionManagerFrameController = new RegionManagerFrameController();
			try {
				regionManagerFrameController.start(ClientMenuController.clientStage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			/*
			 * TODO remove comments after CeoFrameController.java is created
			 */
			/*
			 * CeoFrameController ceoFrameController = new ceoFrameController(); try {
			 * ceoFrameController.start(ClientMenuController.clientStage); } catch
			 * (IOException e) { e.printStackTrace(); }
			 */
		}
	}

	/** Get reports from the database **/
	@FXML
	void searchReport(ActionEvent event) {
		String reportType = selectReportType.getValue();
		String month = selectMonth.getValue();
		String year = selectYear.getValue();
		Message msg = new Message(MessageType.Get_reports,reportType+"#"+month+"#"+year);
		ClientMenuController.clientControl.accept((Object)msg);
	}

	@FXML
	void viewReport(ActionEvent event) {
		if (selectedReport == null)
		{
			System.out.println("please select a report!");
			return;
		}
		Message msg = new Message(MessageType.Show_report,selectedReport);
		ClientMenuController.clientControl.accept((Object)msg);
		
		Report firstReport = selectedReports[0];
		if (firstReport.getReportType().equals("Stock status"))
		{
			// open Stock status report frame with the 'selectedReports' array
			StockStatusReportViewController stockStatusReportView = new StockStatusReportViewController();
			stockStatusReportView.start(ClientMenuController.clientStage, selectedReports);
			
		}
		else if (firstReport.getReportType().equals("Order"))
		{
			// open order report frame with the firstReport
			OrderReportViewController orderReportView = new OrderReportViewController();
			orderReportView.start(ClientMenuController.clientStage, firstReport);
		}
		else if (firstReport.getReportType().equals("Client activity"))
		{
			// open client activity report frame with the firstReport
			/* TODO
			 * ClientActivityReportController clientActivityReport = new ClientActivityReportController();
			 * clientActivityReport.start(ClientMenuController.clientStage, firstReport);
			 */
		}
		else {
			System.out.println("so such report type!");
		}
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		reportListView = reportViewList;
		selectReportType.setValue("Select type");
		selectMonth.setValue("Select month");
		selectYear.setValue("Select year");
		
		
		selectReportType.setItems(reportTypesList);
		selectMonth.setItems(monthsList);
		selectYear.setItems(yearsList);
	}
	
	
	/** adds all the requested report to the list view **/
	public void addReportsToListView(String[] reports)
	{
		reportListView.getItems().clear();
		reportListView.getItems().setAll(reports);
		reportListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				selectedReport = reportListView.getSelectionModel().getSelectedItem();
			}
		});
	}
	
	/** get the info of the selected report**/
	public void getReport(Report[] selectedReports)
	{
		ReportSearchFrameController.selectedReports = selectedReports;
	}

}
