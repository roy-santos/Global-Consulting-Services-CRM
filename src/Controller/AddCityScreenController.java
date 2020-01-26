package Controller;

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
           scene = FXMLLoader.load(getClass().getResource("/View/CustomerScreen.fxml"));
           stage.setScene(new Scene(scene));
           stage.show();
       }
    }

    @FXML
    void onActionSave(ActionEvent event) {
        // TODO -learn localization/timezones for pushing pushing new record to the DB
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
