package Controller;

import Model.Appointment;
import Model.AppointmentTypesReport;
import Model.Customer;
import Model.ScheduleReport;
import Model.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ScheduleReportController implements Initializable {

    @FXML
    private TableView<ScheduleReport> appointmentsTblView;

    @FXML
    private TableColumn<ScheduleReport, String> customerNameCol;

    @FXML
    private TableColumn<ScheduleReport, String> apptTypeCol;

    @FXML
    private TableColumn<ScheduleReport, LocalDateTime> dateAndTimeCol;

    private void generateReport() {

        ObservableList<Appointment> userFilteredAppointments = FXCollections.observableArrayList();
        ObservableList<ScheduleReport> scheduleList = FXCollections.observableArrayList();

        for(Appointment appointment : Session.allAppointments) {
            if(Session.currentUser.getUsername().equals(appointment.getCreatedBy())) {
                userFilteredAppointments.add(appointment);
            }
        }

        for(Appointment appointment : userFilteredAppointments) {

            for(Customer customer : Session.allCustomers) {
                if(customer.getCustomerId() == appointment.getCustomerId()) {
                    scheduleList.add(new ScheduleReport(customer.getCustomerName(), appointment.getType(), appointment.getStart()));
                }
            }
        }

        appointmentsTblView.setItems(scheduleList);
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        dateAndTimeCol.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateReport();
    }
}