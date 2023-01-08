package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entities.Message;
import Entities.MessageType;
import Entities.Report;
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

	ObservableList<String> reportTypesList = FXCollections.observableArrayList("Select type", "Show all report types",
			"Order", "Stock status", "Clients activity");

	ObservableList<String> yearsList = FXCollections.observableArrayList("Select year", "Show all years", "2022",
			"2023");

	ObservableList<String> monthsList = FXCollections.observableArrayList("Select month", "Show all months", "January",
			"February", "March", "April", "May", "June", "July", "August", "September", "October", "November",
			"December");

	@FXML
	private TableColumn<List<Report>, String> colReportName;

	@FXML
	private TableColumn<List<Report>, Void> colViewReports;

	@FXML
	private ChoiceBox<String> selectMonth;

	@FXML
	private ChoiceBox<String> selectReportType;

	@FXML
	private ChoiceBox<String> selectYear;

	@FXML
	private TableView<List<Report>> tblReports;

	private static TableView<List<Report>> reportTableView;

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
		Message msg = new Message(MessageType.Get_reports, reportType + "#" + month + "#" + year + "#" + LoginFrameController.user.getRegion());
		ClientMenuController.clientControl.accept((Object) msg);
	}
	
	/** add all requested reports to the table view**/
	void addReportsToTableView(List<List<Report>> reportsList)
	{
		ObservableList<List<Report>> reports = FXCollections.observableArrayList();
		for (List<Report> reportList : reportsList)
			reports.add(reportList);
		reportTableView.setItems(reports);
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
		colReportName.setCellValueFactory(new PropertyValueFactory<List<Report>, String>("ReportName"));
		// set button cells for the 'view report' column
		Callback<TableColumn<List<Report>, Void>, TableCell<List<Report>, Void>> btnCellFactory = new Callback<TableColumn<List<Report>, Void>, TableCell<List<Report>, Void>>() {
			@Override
			public TableCell<List<Report>, Void> call(final TableColumn<List<Report>, Void> param) {
				final TableCell<List<Report>, Void> cell = new TableCell<List<Report>, Void>() {

					private final Button btn = new Button("view");

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							btn.setOnAction(e -> {
								List<Report> selectedReports = getTableView().getItems().get(getIndex());
								Report firstReport = selectedReports.get(0);
								if (firstReport.getReportType().equals("Stock status")) {
									// open Stock status report frame with the 'selectedReports' array
									StockStatusReportViewController stockStatusReportView = new StockStatusReportViewController();
									try {
										stockStatusReportView.start(ClientMenuController.clientStage, selectedReports);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

								} else if (firstReport.getReportType().equals("Order")) {
									// open order report frame with the firstReport
									OrderReportViewController orderReportView = new OrderReportViewController();
									try {
										orderReportView.start(ClientMenuController.clientStage, firstReport);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								} else if (firstReport.getReportType().equals("Client activity")) {
									// open client activity report frame with the firstReport
									/*
									 * TODO ClientActivityReportController clientActivityReport = new
									 * ClientActivityReportController();
									 * clientActivityReport.start(ClientMenuController.clientStage, firstReport);
									 */
								} else {
									System.out.println("No such report type!");
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
