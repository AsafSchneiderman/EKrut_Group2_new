package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class ClientActivityFrameController {

    @FXML
    private BarChart<String, String> BarChartClientActivity;

    @FXML
    private CategoryAxis categoryAxisProductsAmount;

    @FXML
    private NumberAxis numberAxisClientsAmount;

    @FXML
    void BackToPreviosePage(ActionEvent event) {

    }

}
