package Controller;

import Model.AppointmentTypesReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ScheduleReportController implements Initializable {

    private static ObservableList<AppointmentTypesReport> scheduleReport = FXCollections.observableArrayList();

    @FXML
    private TableView<?> appointmentsTblView;

    @FXML
    private TableColumn<?, ?> customerNameCol;

    @FXML
    private TableColumn<?, ?> apptTypeCol;

    @FXML
    private TableColumn<?, ?> dateAndTimeCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}