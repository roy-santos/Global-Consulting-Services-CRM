package Controller;

import DAO.AddressDAO;
import DAO.AppointmentsDAO;
import DAO.CustomerDAO;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerScreenController implements Initializable {

    Stage stage;
    Parent scene;
    private static ObservableList<Customer> filteredCustomers = FXCollections.observableArrayList();

    @FXML
    private Circle imageCircle;

    @FXML
    private Label userNameField;

    @FXML
    private Button customerBtn;

    @FXML
    private TableView<Customer> customerTblView;

    @FXML
    private TableColumn<Customer, Integer> custIdCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> addressIdCol;

    @FXML
    private TableColumn<Customer, Boolean> activeCol;

    @FXML
    private TableColumn<Customer, LocalDateTime> createDateCol;

    @FXML
    private TableColumn<Customer, String> createdByCol;

    @FXML
    private TableColumn<Customer, LocalDateTime> lastUpdateCol;

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
    void onActionDeleteCust(ActionEvent event) throws IOException {
        if(customerTblView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Customer data will be deleted. Continue?");
            alert.setTitle("CONFIRMATION");

            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                CustomerDAO.deleteCustomer(customerTblView.getSelectionModel().getSelectedItem());

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/CustomerScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No customer selected to be deleted.");
            alert.setTitle("ERROR");

            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    @FXML
    void onActionHomeScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/HomeScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionViewModifyCust(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyCustomerScreen.fxml"));
            loader.load();

            ModifyCustomerScreenController modCustomerController = loader.getController();
            modCustomerController.sendCustomerInfo(customerTblView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No customer selected.");
            alert.setTitle("ERROR");

            Optional<ButtonType> result = alert.showAndWait();
        }
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
        CustomerDAO.loadCustomers();
        AddressDAO.loadAddresses();

        if(!filteredCustomers.isEmpty()) {
            filteredCustomers.clear();
        }

        for(Customer customer : Session.allCustomers) {
            if(customer.getCreatedBy().equals(Session.currentUser.getUsername())) {
                filteredCustomers.add(customer);
            }
        }

        customerTblView.setItems(filteredCustomers);
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        activeCol.setCellValueFactory(new PropertyValueFactory<>("active"));
        addressIdCol.setCellValueFactory(new PropertyValueFactory<>("addressId"));
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
