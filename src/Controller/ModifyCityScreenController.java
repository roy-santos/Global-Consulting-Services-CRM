package Controller;

import DAO.CitiesDAO;
import Model.City;
import Model.Country;
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

public class ModifyCityScreenController implements Initializable {

    Stage stage;
    Parent scene;
    int cityId;

    @FXML
    private Circle imageCircle;

    @FXML
    private Label userNameField;

    @FXML
    private Button customerBtn;

    @FXML
    private TextField cityNameField;

    @FXML
    private ChoiceBox<Country> countryChoiceBox;

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Changes wont be saved. Continue?");
        alert.setTitle("CONFIRMATION");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/AddCustomerScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionDelete(ActionEvent event) throws IOException {

        for (City city : Session.allCities) {

            if (cityNameField.getText().equals(city.getCity())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "City will be deleted. Continue?");
                alert.setTitle("CONFIRMATION");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    CitiesDAO.deleteCity(city);
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/View/AddCustomerScreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR, "No matching city exists.");
        alert.setTitle("ERROR");

        Optional<ButtonType> result = alert.showAndWait();
    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        if(!cityNameField.getText().isEmpty() && countryChoiceBox.getSelectionModel().getSelectedItem() != null) {
            for (City city : Session.allCities) {
                if (cityId == city.getCityId()) {
                    city.setCity(cityNameField.getText());
                    city.setCountryId(countryChoiceBox.getSelectionModel().getSelectedItem().getCountryId());
                    city.setLastUpdate(DateAndTime.ldtTimeFormatter(LocalDateTime.now()));
                    city.setLastUpdateBy(Session.currentUser.getUsername());

                    CitiesDAO.modifyCity(city);

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/View/AddCustomerScreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        } else {
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

    private void setCountryChoiceBox() {
        countryChoiceBox.setItems(Session.allCountries);
    }

    public void sendCityInfo(City city) {
        cityId = city.getCityId();
        cityNameField.setText(city.getCity());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUserImage();
        setCountryChoiceBox();
        customerBtn.setStyle("-fx-background-color: #2a9df4; ");
    }
}

