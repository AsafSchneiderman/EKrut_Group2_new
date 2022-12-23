package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DeliveryWorkerFrameController {

    @FXML
    private TableView<?> tblViewDelivery;

    @FXML
    private TableColumn<?, ?> orderCodeCol;

    @FXML
    private TableColumn<?, ?> addrCol;

    @FXML
    private TableColumn<?, ?> dateCol;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    private ComboBox<?> cmbBoxStatus;

    @FXML
    private Button btnUpdateStatusOrder;

    @FXML
    private Button btnExit;

    @FXML
    void updateOrderStatusToDelivery(ActionEvent event) {

    }

    @FXML
    void exit(ActionEvent event) {

    }

}
