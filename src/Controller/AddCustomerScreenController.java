package Controller;

import DAO.CitiesDAO;
import DAO.CountriesDAO;
import Model.City;
import Model.Country;
import Model.Session;
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
    private Button homeBtn;

    @FXML
    private Button customerBtn;

    @FXML
    private Button appointmentsBtn;

    @FXML
    private Button signOutBtn;

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField address1Field;

    @FXML
    private TextField address2Field;

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
    void onActionAddCountry(ActionEvent event) {

    }

    @FXML
    void onActionApptScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AppointmentScreen.fxml"));
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
            System.out.println("Exception: " + e);
        }
    }

    @FXML
    void onActionModifyCountry(ActionEvent event) {
        System.out.println(countryChoiceBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    void onActionSaveCust(ActionEvent event) {

    }

    private void setUserImage() {
        // Default image is show. Logic can be added to select user image if one is available in the img folder
        userNameField.setText(Session.currentUser.getUsername());
        Image userImage = new Image("/img/user.png",false);
        imageCircle.setFill(new ImagePattern(userImage));
    }

    @FXML
    void onActionSignOut(ActionEvent event) throws IOException {
        Session.endSession();
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/LoginScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    private void setChoiceBoxes() {
        cityChoiceBox.setItems(Session.allCities);
        countryChoiceBox.setItems(Session.allCountries);
    }

    private void initializeCitiesAndCountries() {
        if(Session.allCities.isEmpty()) {
            CitiesDAO.loadCities();
        }

        if(Session.allCountries.isEmpty()) {
            CountriesDAO.loadCountries();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUserImage();
        setChoiceBoxes();
        initializeCitiesAndCountries();
        customerBtn.setStyle("-fx-background-color: #2a9df4; ");
    }
}
