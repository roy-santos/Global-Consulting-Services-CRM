package Controller;

import DAO.AppointmentsDAO;
import DAO.CustomerDAO;
import Model.Appointment;
import Model.Customer;
import Model.Session;
import Utilities.DateAndTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddAppointmentScreenController implements Initializable {

    Stage stage;
    Parent scene;
    private static ObservableList<Customer> filteredCustomers = FXCollections.observableArrayList();


    @FXML
    private Circle imageCircle;

    @FXML
    private Label userNameField;

    @FXML
    private Button appointmentsBtn;

    @FXML
    private TextField titleField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField contactField;

    @FXML
    private TextField typeField;

    @FXML
    private TextField urlField;

    @FXML
    private TableView<Customer> apptCustomerTableView;

    @FXML
    private TableColumn<Customer, Integer> custIdCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox<String> startTimeBox;

    @FXML
    private ChoiceBox<String> endTimeBox;

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Changes wont be saved. Continue?");
        alert.setTitle("CONFIRMATION");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/AppointmentScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionSaveAppt(ActionEvent event) throws ParseException, IOException {
        int maxAppointmentIdValue = -1;
        for(Appointment appointment : Session.allAppointments) {
            maxAppointmentIdValue = appointment.getAppointmentId();
        }

        LocalDateTime start = DateAndTime.apptTimeFormatter(datePicker.getValue(), startTimeBox.getValue());
        LocalDateTime end = DateAndTime.apptTimeFormatter(datePicker.getValue(), endTimeBox.getValue());

        if(start.isAfter(LocalDateTime.now()) && end.isAfter(start)) {
            Session.allAppointments.add(new Appointment(
                    maxAppointmentIdValue + 1,
                    apptCustomerTableView.getSelectionModel().getSelectedItem().getCustomerId(),
                    Session.currentUser.getUserId(),
                    titleField.getText(),
                    descriptionField.getText(),
                    locationField.getText(),
                    contactField.getText(),
                    typeField.getText(),
                    urlField.getText(),
                    start,
                    end,
                    DateAndTime.ldtTimeFormatter(LocalDateTime.now()),
                    Session.currentUser.getUsername(),
                    DateAndTime.ldtTimeFormatter(LocalDateTime.now()),
                    Session.currentUser.getUsername()
            ));

            AppointmentsDAO.addNewAppointment(Session.allAppointments.get(Session.allAppointments.size() - 1));

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/AppointmentScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Make sure that start date is later than today and end date is later than start.");
            alert.setTitle("ERROR");

            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    private void setUserImage() {
        // Default image is show. Logic can be added to select user image if one is available in the img folder
        userNameField.setText(Session.currentUser.getUsername());
        Image userImage = new Image("/img/user.png",false);
        imageCircle.setFill(new ImagePattern(userImage));
    }

    private void setChoiceBoxes() {
        startTimeBox.setItems(Session.allTimes);
        endTimeBox.setItems(Session.allTimes);
    }

    private void setCustomerTable() {
        CustomerDAO.loadCustomers();

        if(!filteredCustomers.isEmpty()) {
            filteredCustomers.clear();
        }

        for(Customer customer : Session.allCustomers) {
            if(customer.getCreatedBy().equals(Session.currentUser.getUsername())) {
                filteredCustomers.add(customer);
            }
        }
        apptCustomerTableView.setItems(filteredCustomers);
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
    }

    private void setDatePicker() {
        datePicker.setValue(LocalDate.now());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM YYYY");
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return " ";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUserImage();
        setChoiceBoxes();
        setCustomerTable();
        setDatePicker();
        appointmentsBtn.setStyle("-fx-background-color: #2a9df4; ");
    }
}
