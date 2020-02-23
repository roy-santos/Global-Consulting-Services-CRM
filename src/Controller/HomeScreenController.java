package Controller;

import DAO.AppointmentsDAO;
import DAO.CustomerDAO;
import Model.Appointment;
import Model.Customer;
import Model.Session;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {

    Stage stage;
    Parent scene;
    private static ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();

    @FXML
    private Circle imageCircle;

    @FXML
    private Button homeBtn;

    @FXML
    private Label userNameField;

    @FXML
    private Label nextAppointmentDate;

    @FXML
    private Label nextCustomerName;

    @FXML
    void onActionSchedReport(ActionEvent event) throws IOException {

        Stage secondStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane root = fxmlLoader.load(getClass().getResource("/View/ScheduleReportScreen.fxml").openStream());
        secondStage.setScene(new Scene(root, 500, 325));
        secondStage.setTitle("Schedule Report");
        secondStage.showAndWait();
    }

    @FXML
    void onActionApptReport(ActionEvent event) throws IOException {

        Stage secondStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane root = fxmlLoader.load(getClass().getResource("/View/AppointmentReportScreen.fxml").openStream());
        secondStage.setScene(new Scene(root, 325, 325));
        secondStage.setTitle("Appointments Report");
        secondStage.showAndWait();
    }

    @FXML
    void onActionCustReport(ActionEvent event) throws IOException {

        Stage secondStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane root = fxmlLoader.load(getClass().getResource("/View/CustomerReportScreen.fxml").openStream());
        secondStage.setScene(new Scene(root, 325, 325));
        secondStage.setTitle("Appointments Report");
        secondStage.showAndWait();
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
    void onActionHomeScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/HomeScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionSignOut(ActionEvent event) throws IOException {
        Session.endSession();
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/LoginScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    private void setUserImage() {
        // Default image is show. Logic can be added to select user image if one is available in the img folder
        userNameField.setText(Session.currentUser.getUsername());
        Image userImage = new Image("/img/user.png",false);
        imageCircle.setFill(new ImagePattern(userImage));
    }

    private void setNextInfo() {
        AppointmentsDAO.loadAppointments();

        if (!filteredAppointments.isEmpty()) {
            filteredAppointments.clear();
        }

        for (Appointment appointment : Session.allAppointments) {
            if (appointment.getUserId() == Session.currentUser.getUserId()) {
                filteredAppointments.add(appointment);
            }
        }

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMMM dd, YYYY");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");

        Collections.sort(filteredAppointments);

        LocalDateTime upcomingAppt = null;
        for(Appointment appointment : filteredAppointments) {
            if (appointment.getStart().isAfter(LocalDateTime.now())) {
                upcomingAppt = appointment.getStart();


                LocalDateTime nextAppt = upcomingAppt;
                String dateApptStr = nextAppt.format(dateFormat);
                String timeApptStr = nextAppt.format(timeFormat);
                nextAppointmentDate.setText(dateApptStr + " at " + timeApptStr);

                CustomerDAO.loadCustomers();

                for (Customer customer : Session.allCustomers) {
                    if (customer.getCustomerId() == appointment.getCustomerId()) {
                        nextCustomerName.setText(customer.getCustomerName());
                    }
                }
                break;
            } else {
                nextAppointmentDate.setText("No upcoming appointment");
                nextCustomerName.setText("No upcoming customer");
            }
        }
    }

    private void nextAppointmentAlert() {
        if(filteredAppointments.get(0).getStart().isAfter(LocalDateTime.now()) && filteredAppointments.get(0).getStart().isBefore(LocalDateTime.now().plusMinutes(15))) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Next appointment is within 15 minutes.");
            alert.setTitle("WARNING");

            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUserImage();
        setNextInfo();
        nextAppointmentAlert();
        homeBtn.setStyle("-fx-background-color: #2a9df4; ");
    }
}
