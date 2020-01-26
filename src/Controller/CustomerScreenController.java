package Controller;

import DAO.AppointmentsDAO;
import Model.Customer;
import Model.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class CustomerScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Circle imageCircle;

    @FXML
    private Label userNameField;

    @FXML
    private Button homeBtn;

    @FXML
    private Button customerBtn;

    @FXML
    private Button appointmentsBtn;

    @FXML
    private Button signOutBtn;

    @FXML
    private TableView<Customer> customerTblView;

    @FXML
    private TableColumn<Customer, Integer> custIdCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, Boolean> activeCol;

    @FXML
    private TableColumn<Customer, Timestamp> createDateCol;

    @FXML
    private TableColumn<Customer, String> createdByCol;

    @FXML
    private TableColumn<Customer, Timestamp> lastUpdateCol;

    @FXML
    private TableColumn<Customer, String> lastUpdatedByCol;

    @FXML
    void onActionAddCust(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddCustomerScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
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
    void onActionDeleteCust(ActionEvent event) {

    }

    @FXML
    void onActionHomeScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/HomeScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionModifyCust(ActionEvent event) {

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

   private void setCustomerTable() {
        if(Session.allCustomers.isEmpty()) {
            AppointmentsDAO.loadAppointments(Session.currentUser.getUserId());
        }
       customerTblView.setItems(Session.allCustomers);
       custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
       customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
       activeCol.setCellValueFactory(new PropertyValueFactory<>("active"));
       addressCol.setCellValueFactory(new PropertyValueFactory<>("addressId"));
       createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
       createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
       lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
       lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdateBy"));
   }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUserImage();
        setCustomerTable();
        customerBtn.setStyle("-fx-background-color: #2a9df4; ");
    }
}
