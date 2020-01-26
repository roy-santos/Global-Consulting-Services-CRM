package Controller;

import DAO.AppointmentsDAO;
import Model.Appointment;
import Model.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AppointmentScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Circle imageCircle;

    @FXML
    private Button homeBtn;

    @FXML
    private Button customerBtn;

    @FXML
    private Button appointmentsBtn;

    @FXML
    private Button signOutBtn;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label userNameField;

    @FXML
    private Button viewBtn;

    @FXML
    private TableView<Appointment> appointmentsTblView;

    @FXML
    private TableColumn<Appointment, Integer> apptIdCol;

    @FXML
    private TableColumn<Appointment, String> apptTypeCol;

    @FXML
    private TableColumn<Appointment, String> customerNameCol;

    @FXML
    private TableColumn<Appointment, String> contactCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableColumn<Appointment,String> typeCol;

    @FXML
    private TableColumn<Appointment, String> urlCol;

    @FXML
    private TableColumn<Appointment, Timestamp> startCol;

    @FXML
    private TableColumn<Appointment, Timestamp> endCol;

    @FXML
    private TableColumn<Appointment, Timestamp> createDateCol;

    @FXML
    private TableColumn<Appointment, String> createdByCol;

    @FXML
    private TableColumn<Appointment, Timestamp> lastUpdateCol;

    @FXML
    private TableColumn<Appointment, String> lastUpdatedByCol;

    @FXML
    void onActionAddAppt(ActionEvent event) {

    }

    @FXML
    void onActionApptScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AppointmentScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionCustomersScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/CustomerScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDeleteAppt(ActionEvent event) {

    }

    @FXML
    void onActionHomeScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/HomeScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionModifyAppt(ActionEvent event) {

    }

    @FXML
    void onActionSignOut(ActionEvent event) throws IOException {
        Session.endSession();
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/LoginScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionToggleViewBtn(ActionEvent event) {
        if(viewBtn.getText().equals("Week View")) {
            viewBtn.setText("Month View");
        } else {
            viewBtn.setText("Week View");
        }
    }

    private void setUserImage() {
        // Default image is show. Logic can be added to select user image if one is available in the img folder
        userNameField.setText(Session.currentUser.getUsername());
        Image userImage = new Image("/img/user.png",false);
        imageCircle.setFill(new ImagePattern(userImage));
    }

    private void setDatePicker() {
        datePicker.setValue(LocalDate.now());
        String pattern = "dd MMMM YYYY";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
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

    private void setAppointmentsTable() {
        if(Session.allAppointments.isEmpty()) {
            AppointmentsDAO.loadAppointments(Session.currentUser.getUserId());
        }

        appointmentsTblView.setItems(Session.allAppointments);
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        urlCol.setCellValueFactory(new PropertyValueFactory<>("url"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdateBy"));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUserImage();
        setDatePicker();
        setAppointmentsTable();
        appointmentsBtn.setStyle("-fx-background-color: #2a9df4; ");
    }
}
