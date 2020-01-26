package Controller;

import DAO.LoginDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    void onActionSignOnBtn(ActionEvent event) throws IOException {

        if(LoginDAO.authenticateLogin(usernameField.getText(), passwordField.getText())) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/HomeScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Credentials");
            alert.setContentText("Please enter the correct username and password.");
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
