package Controller;

import Model.Appointment;
import Model.Session;
import Model.AppointmentTypesReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class AppointmentReportController implements Initializable {

    ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    ObservableList<String> years = FXCollections.observableArrayList("2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030");

    @FXML
    private ComboBox<String> monthPicker;

    @FXML
    private ComboBox<String> yearPicker;

    @FXML
    private TableView<AppointmentTypesReport> appointmentsTblView;

    @FXML
    private TableColumn<AppointmentTypesReport, String> apptTypeCol;

    @FXML
    private TableColumn<AppointmentTypesReport, Integer> numberOfAppointmentsCol;

    private void setChoiceBox() {
        yearPicker.setItems(years);

        for(String year : years) {
            if(String.valueOf(LocalDateTime.now().getYear()).equals(year)) {
                yearPicker.setValue(year);
            }
        }

        monthPicker.setItems(months);

        for(String month : months) {
            if(String.valueOf(LocalDateTime.now().getMonth()).toLowerCase().equals(month.toLowerCase())) {
                monthPicker.setValue(month);
            }
        }
    }

    @FXML
    void onActionGenReportMonth(ActionEvent event) {
        generateReport();
    }

    @FXML
    void onActionGenReportYear(ActionEvent event) {
        generateReport();
    }

    private void generateReport() {

        ObservableList<Appointment> monthFilteredAppointments = FXCollections.observableArrayList();
        ObservableList<AppointmentTypesReport> typesPerMonthList = FXCollections.observableArrayList();
        ArrayList<String> uniqueTypes = new ArrayList<String>();
        ArrayList<String> allTypes = new ArrayList<String>();

        for(Appointment appointment : Session.allAppointments) {
            if(appointment.getUserId() == Session.currentUser.getUserId()) {
                if(String.valueOf(appointment.getStart().getYear()).toLowerCase().equals(yearPicker.getValue().toLowerCase())) {
                    if(String.valueOf(appointment.getStart().getMonth()).toLowerCase().equals(monthPicker.getValue().toLowerCase())) {
                        monthFilteredAppointments.add(appointment);
                    }
                }
            }
        }

        for(Appointment appointment : monthFilteredAppointments) {
            allTypes.add(appointment.getType());
            if(!uniqueTypes.contains(appointment.getType())) {
                uniqueTypes.add(appointment.getType());
            }
        }

        for(String type : uniqueTypes) {
            int occurrence = Collections.frequency(allTypes, type);
            typesPerMonthList.add(new AppointmentTypesReport(type, occurrence));
        }

        appointmentsTblView.setItems(typesPerMonthList);
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        numberOfAppointmentsCol.setCellValueFactory(new PropertyValueFactory<>("amountOfType"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setChoiceBox();
        generateReport();
    }
}
