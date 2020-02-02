package Controller;

import DAO.AddressDAO;
import DAO.CitiesDAO;
import DAO.CountriesDAO;
import DAO.CustomerDAO;
import Model.Address;
import Model.City;
import Model.Country;
import Model.Customer;
import Model.Session;
import Utilities.DateAndTime;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCustomerScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Circle imageCircle;

    @FXML
    private Label userNameField;

    @FXML
    private Button customerBtn;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField address1Field;

    @FXML
    private TextField address2Field;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField phoneField;

    @FXML
    private ChoiceBox<City> cityChoiceBox;

    @FXML
    private ChoiceBox<Country> countryChoiceBox;

    @FXML
    void onActionAddCity(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddCityScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionAddCountry(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddCountryScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionCancelCust(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Changes wont be saved. Continue?");
        alert.setTitle("CONFIRMATION");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/CustomerScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionModifyCity(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyCityScreen.fxml"));
            loader.load();

            ModifyCityScreenController modCityController = loader.getController();
            modCityController.sendCityInfo(cityChoiceBox.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No city selected.");
            alert.setTitle("ERROR");

            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    @FXML
    void onActionModifyCountry(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyCountryScreen.fxml"));
            loader.load();

            ModifyCountryScreenController modCityController = loader.getController();
            modCityController.sendCountryInfo(countryChoiceBox.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No country selected.");
            alert.setTitle("ERROR");

            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    @FXML
    void onActionSaveCust(ActionEvent event) throws IOException {
        try {
            int maxAddressIdValue = -1;
            for(Address address : Session.allAddresses) {
                if(address.getAddressId() > maxAddressIdValue) {
                    maxAddressIdValue = address.getAddressId();
                }
            }

            Session.allAddresses.add(new Address(
                    maxAddressIdValue + 1,
                    address1Field.getText(),
                    address2Field.getText(),
                    cityChoiceBox.getSelectionModel().getSelectedItem().getCityId(),
                    postalCodeField.getText(),
                    phoneField.getText(),
                    DateAndTime.ldtTimeFormatter(LocalDateTime.now()),
                    Session.currentUser.getUsername(),
                    DateAndTime.ldtTimeFormatter(LocalDateTime.now()),
                    Session.currentUser.getUsername()

            ));

            AddressDAO.addNewAddress(Session.allAddresses.get(Session.allAddresses.size() - 1));

            int maxCustomerIdValue = -1;
            for(Customer customer : Session.allCustomers) {
                if(customer.getCustomerId() > maxCustomerIdValue) {
                    maxCustomerIdValue = customer.getCustomerId();
                }
            }

            Session.allCustomers.add(new Customer(
                    maxCustomerIdValue + 1,
                    customerNameField.getText(),
                    Session.allAddresses.size(),
                    true,
                    DateAndTime.ldtTimeFormatter(LocalDateTime.now()),
                    Session.currentUser.getUsername(),
                    DateAndTime.ldtTimeFormatter(LocalDateTime.now()),
                    Session.currentUser.getUsername()
            ));

            CustomerDAO.addNewCustomer(Session.allCustomers.get(Session.allCustomers.size() - 1));

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/CustomerScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Missing information.");
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
        cityChoiceBox.setItems(Session.allCities);
        countryChoiceBox.setItems(Session.allCountries);
    }

    private void initializeLists() {
        AddressDAO.loadAddresses();
        CitiesDAO.loadCities();
        CountriesDAO.loadCountries();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUserImage();
        setChoiceBoxes();
        initializeLists();
        customerBtn.setStyle("-fx-background-color: #2a9df4; ");
    }
}
