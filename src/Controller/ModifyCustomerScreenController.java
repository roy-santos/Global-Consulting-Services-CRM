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

public class ModifyCustomerScreenController implements Initializable {

    Stage stage;
    Parent scene;
    int customerId;

    @FXML
    private Circle imageCircle;

    @FXML
    private Label userNameField;

    @FXML
    private Button customerBtn;

    @FXML
    private TextField customerIdField;

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
    void onActionSaveCust(ActionEvent event) throws IOException {
        for (Customer customer : Session.allCustomers) {
            if (customerId == customer.getCustomerId()) {
                customer.setCustomerName(customerNameField.getText());
                customer.setLastUpdate(LocalDateTime.now());
                customer.setLastUpdateBy(Session.currentUser.getUsername());

                for(Address address : Session.allAddresses) {
                    if(address.getAddressId() == customer.getAddressId()) {
                        address.setAddress(address1Field.getText());
                        address.setAddress2(address2Field.getText());
                        address.setCityId(cityChoiceBox.getSelectionModel().getSelectedItem().getCityId());
                        address.setPostalCode(postalCodeField.getText());
                        address.setPhone(phoneField.getText());
                        address.setLastUpdate(DateAndTime.ldtTimeFormatter(LocalDateTime.now()));
                        address.setLastUpdateBy(Session.currentUser.getUsername());

                        AddressDAO.modifyAddress(address);

                        break;
                    }
                }
                customer.setLastUpdate(LocalDateTime.now());
                customer.setLastUpdateBy(Session.currentUser.getUsername());

                CustomerDAO.modifyCustomer(customer);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/CustomerScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

                break;
            }
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

    public void sendCustomerInfo(Customer customer) {
        customerId = customer.getCustomerId();

        customerIdField.setText(String.valueOf(customer.getCustomerId()));
        customerNameField.setText(customer.getCustomerName());

        for(Address address : Session.allAddresses) {
            if(customer.getAddressId() == address.getAddressId()) {
                address1Field.setText(address.getAddress());
                address2Field.setText(address.getAddress2());
                postalCodeField.setText(address.getPostalCode());
                phoneField.setText(address.getPhone());

                for(City city : Session.allCities) {
                    if(address.getCityId() == city.getCityId()) {
                        cityChoiceBox.setValue(city);

                        for(Country country : Session.allCountries) {
                            if(city.getCountryId() == country.getCountryId()) {
                                countryChoiceBox.setValue(country);
                            }
                        }
                    }
                }
            }
        }
    }

    private void initializeLists() {
        CountriesDAO.loadCountries();
        CitiesDAO.loadCities();
        AddressDAO.loadAddresses();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUserImage();
        initializeLists();
        setChoiceBoxes();
        customerBtn.setStyle("-fx-background-color: #2a9df4; ");
    }
}
