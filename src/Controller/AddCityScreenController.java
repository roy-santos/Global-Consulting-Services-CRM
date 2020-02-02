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

public class AddCityScreenController implements Initializable {

    Stage stage;
    Parent scene;

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
    void onActionSave(ActionEvent event) throws IOException {

        if(!cityNameField.getText().isEmpty() && countryChoiceBox.getSelectionModel().getSelectedItem() != null) {

            int maxCityIdValue = -1;
            for(City city : Session.allCities) {
                if(city.getCityId() > maxCityIdValue) {
                    maxCityIdValue = city.getCityId();
                }
            }

            Session.allCities.add(new City(
                    maxCityIdValue + 1,
                    cityNameField.getText(),
                    countryChoiceBox.getSelectionModel().getSelectedItem().getCountryId(),
                    DateAndTime.ldtTimeFormatter(LocalDateTime.now()),
                    Session.currentUser.getUsername(),
                    DateAndTime.ldtTimeFormatter(LocalDateTime.now()),
                    Session.currentUser.getUsername()
            ));

            CitiesDAO.addNewCity(Session.allCities.get(Session.allCities.size() - 1));

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/AddCustomerScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No country selected.");
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUserImage();
        setCountryChoiceBox();
        customerBtn.setStyle("-fx-background-color: #2a9df4; ");
    }
}
