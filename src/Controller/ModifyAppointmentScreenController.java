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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyAppointmentScreenController implements Initializable {

    Stage stage;
    Parent scene;
    int appointmentId;
    private static ObservableList<Customer> filteredCustomers = FXCollections.observableArrayList();
    private static ObservableList<Appointment> userFilteredAppointments = FXCollections.observableArrayList();


    @FXML
    private Circle imageCircle;

    @FXML
    private Label userNameField;

    @FXML
    private Button appointmentsBtn;

    @FXML
    private TextField apptIdField;

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

    private static boolean overlapChecker(LocalDateTime start, LocalDateTime end) {

        if(!userFilteredAppointments.isEmpty()) {
            userFilteredAppointments.clear();
        }

        for(Appointment appointment : Session.allAppointments) {
            if(appointment.getUserId() == Session.currentUser.getUserId()) {
                userFilteredAppointments.add(appointment);
            }
        }

        for(Appointment appointment : userFilteredAppointments) {
            if(start.isAfter(appointment.getStart()) && start.isBefore(appointment.getEnd())) {
                return true;
            } else if (start.isEqual(appointment.getStart()) || start.isEqual(appointment.getEnd())) {
                return true;
            } else if(end.isAfter(appointment.getStart()) && end.isBefore(appointment.getEnd())){
                return true;
            } else if(end.isEqual(appointment.getStart()) || end.isEqual(appointment.getEnd())) {
                return true;
            }
        }
        return false;
    }

    @FXML
    void onActionSaveAppt(ActionEvent event) throws ParseException, IOException {

        LocalDateTime start = DateAndTime.apptTimeFormatter(datePicker.getValue(), startTimeBox.getValue());
        LocalDateTime end = DateAndTime.apptTimeFormatter(datePicker.getValue(), endTimeBox.getValue());

        if(!overlapChecker(start, end)) {
            for (Appointment appointment : Session.allAppointments) {
                if (appointment.getAppointmentId() == appointmentId) {
                    if (start.isAfter(LocalDateTime.now()) && !start.getDayOfWeek().toString().equals("SATURDAY") && !start.getDayOfWeek().toString().equals("SUNDAY") && end.isAfter(start)) {
                        appointment.setCustomerId(apptCustomerTableView.getSelectionModel().getSelectedItem().getCustomerId());
                        appointment.setTitle(titleField.getText());
                        appointment.setDescription(descriptionField.getText());
                        appointment.setLocation(locationField.getText());
                        appointment.setContact(contactField.getText());
                        appointment.setType(typeField.getText());
                        appointment.setUrl(urlField.getText());

                        appointment.setStart(start);
                        appointment.setEnd(end);
                        appointment.setLastUpdate(DateAndTime.ldtTimeFormatter(LocalDateTime.now()));
                        appointment.setLastUpdateBy(Session.currentUser.getUsername());

                        AppointmentsDAO.modifyAppointment(appointment);

                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/View/AppointmentScreen.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();

                        break;
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Make sure that: \n\t\u2022 start date is later than today,\n\t\u2022 end date is later than start,\n\t\u2022 date selected is not a weekend.");
                        alert.setTitle("ERROR");

                        Optional<ButtonType> result = alert.showAndWait();
                    }
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment time overlaps with another appointment. Please select another date and time.");
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

    public void sendAppointmentInfo(Appointment appointment) throws ParseException {
        appointmentId = appointment.getAppointmentId();
        apptIdField.setText(String.valueOf(appointment.getAppointmentId()));
        titleField.setText(appointment.getTitle());
        descriptionField.setText(appointment.getDescription());
        locationField.setText(appointment.getLocation());
        contactField.setText(appointment.getContact());
        typeField.setText(appointment.getType());
        urlField.setText(appointment.getUrl());

        for(Customer customer : filteredCustomers) {
            if(customer.getCustomerId() == appointment.getCustomerId()) {
                apptCustomerTableView.getSelectionModel().select(customer);
            }
        }

        datePicker.setValue(appointment.getStart().toLocalDate());
         for(String time : Session.allTimes) {
             if(time.equals(DateAndTime.timeFormatter(appointment.getStart()))) {
                 startTimeBox.setValue(time);
             }

             if(time.equals(DateAndTime.timeFormatter(appointment.getEnd()))) {
                 endTimeBox.setValue(time);
             }
         }
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
        setDatePicker();
        setChoiceBoxes();
        setCustomerTable();
        appointmentsBtn.setStyle("-fx-background-color: #2a9df4; ");
    }
}