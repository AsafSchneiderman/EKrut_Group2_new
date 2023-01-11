package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UpdateThresholdFrameController {

    @FXML
    private TableView<?> tblViewVendingMachine;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> locationCol;

    @FXML
    private TableColumn<?, ?> thresholdLevelCol;

    @FXML
    private Button btnUpdateThresholdLevel;

    @FXML
    private Button btnBack;

    @FXML
    void back(ActionEvent event) {

    }

    @FXML
    void updateThresholdLevel(ActionEvent event) {

    }

}
