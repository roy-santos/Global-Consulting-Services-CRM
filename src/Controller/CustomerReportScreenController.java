package Controller;

import Model.Appointment;
import Model.Customer;
import Model.CustomerReport;
import Model.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class CustomerReportScreenController implements Initializable {

    @FXML
    private TableView<CustomerReport> customerReportTableView;

    @FXML
    private TableColumn<CustomerReport, String> customerNameCol;

    @FXML
    private TableColumn<CustomerReport, Integer> amountOfApptsCol;

    private void generateReport() {

        ObservableList<CustomerReport> customerAppointmentsList = FXCollections.observableArrayList();
        ArrayList<Integer> allApptCustomerIds = new ArrayList<Integer>();

        for(Appointment appointment : Session.allAppointments) {
            if(appointment.getUserId() == Session.currentUser.getUserId()) {
                allApptCustomerIds.add(appointment.getCustomerId());
            }
        }

        for(Customer customer : Session.allCustomers) {
            if(Session.currentUser.getUsername().equals(customer.getCreatedBy())) {
                int occurrence = Collections.frequency(allApptCustomerIds, customer.getCustomerId());
                customerAppointmentsList.add(new CustomerReport(customer.getCustomerName(), occurrence));
            }
        }

        customerReportTableView.setItems(customerAppointmentsList);
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        amountOfApptsCol.setCellValueFactory(new PropertyValueFactory<>("numberOfAppts"));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateReport();
    }
}
