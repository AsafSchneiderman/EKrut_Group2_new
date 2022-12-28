package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RestockMessageController {

    @FXML
    private TableView<?> tblViewVendingMachine1;

    @FXML
    private TableColumn<?, ?> idCol1;

    @FXML
    private TableColumn<?, ?> locationCol1;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    private ComboBox<?> cmbBoxStatus;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnUpdateRestockStatus;

    @FXML
    void back(ActionEvent event) {

    }

    @FXML
    void updateRestockStatus(ActionEvent event) {

    }

}
