package Controller;

import DAO.CountriesDAO;
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

public class ModifyCountryScreenController implements Initializable {

    Stage stage;
    Parent scene;
    int countryId;

    @FXML
    private Circle imageCircle;

    @FXML
    private Label userNameField;

    @FXML
    private Button customerBtn;

    @FXML
    private TextField countryField;

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

        for (Country country : Session.allCountries) {

            if (countryField.getText().equals(country.getCountry())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Country will be deleted. Continue?");
                alert.setTitle("CONFIRMATION");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    CountriesDAO.deleteCountry(country);
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/View/AddCustomerScreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR, "No matching country exists.");
        alert.setTitle("ERROR");

        Optional<ButtonType> result = alert.showAndWait();
    }


    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        for (Country country: Session.allCountries) {
            if(countryId == country.getCountryId()) {
                country.setCountry(countryField.getText());
                country.setLastUpdate(DateAndTime.ldtTimeFormatter(LocalDateTime.now()));
                country.setLastUpdateBy(Session.currentUser.getUsername());

                CountriesDAO.modifyCountry(country);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/AddCustomerScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }

    private void setUserImage() {
        // Default image is show. Logic can be added to select user image if one is available in the img folder
        userNameField.setText(Session.currentUser.getUsername());
        Image userImage = new Image("/img/user.png",false);
        imageCircle.setFill(new ImagePattern(userImage));
    }

    public void sendCountryInfo(Country country) {
        countryId = country.getCountryId();
        countryField.setText(country.getCountry());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUserImage();
        customerBtn.setStyle("-fx-background-color: #2a9df4; ");
    }
}
