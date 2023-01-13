package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entities.ClientActivityReport;
import Entities.Message;
import Entities.MessageType;
import Entities.OrdersReport;
import Entities.Report;
import Entities.ReportType;
import Entities.StockStatusReport;
import entity.Subscriber;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ReportSearchFrameController implements Initializable {
	
	public enum myMonths {January("01"), February("02"), March("03"), 
		April("04"), May("05"), June("06"), July("07"), August("08"),
		September("09"), October("10"), November("11"), December("12");
		
		private String monthNum;
		myMonths(String monthNum) {
			this.monthNum = monthNum;
		}
		
		public String getMonthNum() {
			return monthNum;
		}
	}

	ObservableList<String> reportTypesList = FXCollections.observableArrayList("Select type", "Show all report types",
			"Order", "Stock_Status", "Client_Activity");

	ObservableList<String> yearsList = FXCollections.observableArrayList("Select year", "2022", "2023");

	ObservableList<String> monthsList = FXCollections.observableArrayList("Select month", "January",
			"February", "March", "April", "May", "June", "July", "August", "September", "October", "November",
			"December");

	@FXML
	private TableColumn<Report, String> colReportName;

	@FXML
	private TableColumn<Report, Void> colViewReports;

	@FXML
	private ChoiceBox<String> selectMonth;

	@FXML
	private ChoiceBox<String> selectReportType;

	@FXML
	private ChoiceBox<String> selectYear;

	@FXML
	private TableView<Report> tblReports;
	
	private static ObservableList<Report> reports = FXCollections.observableArrayList();
	private static TableView<Report> reportTableView;

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
		String month =  myMonths.valueOf(selectMonth.getValue()).getMonthNum();
		String year = selectYear.getValue();
		if (reportType.equals("Select type") || month.equals("Select month") || year.equals("Select year"))
			System.out.println("please select {type, month, year} !");
		
		Message msg = new Message(MessageType.Get_reports, reportType + "#" + month + "#" + year + "#" + LoginFrameController.user.getRegion());
		ClientMenuController.clientControl.accept((Object) msg);
		reportTableView.setItems(reports);
	}
	
	/** add all requested reports to the table view**/
	public static void addReportsToTableView(List<Report> reportsList)
	{
		for (int i = 0 ; i < reports.size(); i++)
			reports.remove(i);
		System.out.println("Amount of reports: " + reportsList.size());
		
		for (Report report : reportsList)
			reports.add(report);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selectReportType.setValue("Select type");
		selectMonth.setValue("Select month");
		selectYear.setValue("Select year");

		selectReportType.setItems(reportTypesList);
		selectMonth.setItems(monthsList);
		selectYear.setItems(yearsList);

		reportTableView = tblReports;
		colReportName.setCellValueFactory(new PropertyValueFactory<Report, String>("reportName"));
		// set button cells for the 'view report' column
		Callback<TableColumn<Report, Void>, TableCell<Report, Void>> btnCellFactory = new Callback<TableColumn<Report, Void>, TableCell<Report, Void>>() {
			@Override
			public TableCell<Report, Void> call(final TableColumn<Report, Void> param) {
				final TableCell<Report, Void> cell = new TableCell<Report, Void>() {

					private final Button btn = new Button("view");

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							btn.setOnAction(e -> {
								
								Report selectedReport = getTableView().getItems().get(getIndex());
								try {
									switch(selectedReport.getReportType())
									{
									case Order: // open order report frame with the firstReport
										System.out.println((OrdersReport)selectedReport);
										OrderReportViewController orderReportView = new OrderReportViewController();
										orderReportView.start(ClientMenuController.clientStage, (OrdersReport)selectedReport);
										break;
									case Stock_Status: // open Stock status report frame with the 'selectedReports' array
										System.out.println((StockStatusReport)selectedReport);
										StockStatusReportViewController stockStatusReportView = new StockStatusReportViewController();
										stockStatusReportView.start(ClientMenuController.clientStage, (StockStatusReport)selectedReport);
										break;
									case Client_Activity:
										// open client activity report frame with the firstReport
										System.out.println((ClientActivityReport)selectedReport);
										ClientActivityReportViewController clientActivityReportViewController = new ClientActivityReportViewController();
										clientActivityReportViewController.start(ClientMenuController.clientStage, (ClientActivityReport)selectedReport);
										break;
									default:
										System.out.println("No such report type!");
										break;
									}	
								}catch (IOException e1) {
									e1.printStackTrace();
								}
							});
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};
		colViewReports.setCellFactory(btnCellFactory);
	}

}
