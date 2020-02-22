package Controller;

import DAO.LoginDAO;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    Stage stage;
    Parent scene;
    ResourceBundle rb = ResourceBundle.getBundle("Utilities/Lan", Locale.getDefault());

    @FXML
    private TextField usernameField;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField passwordField;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button logInBtn;

    @FXML
    void onActionLogInBtn(ActionEvent event) throws IOException {

        if (LoginDAO.authenticateLogin(usernameField.getText(), passwordField.getText())) {

            // Log user activity
            String filename = "Logger.txt";
            FileWriter fileWriter = new FileWriter(filename, true);
            PrintWriter outputFile = new PrintWriter(fileWriter);
            outputFile.println("Username: " + Session.currentUser.getUsername() + "\nLogin Time (UTC): " + Instant.now() + "\n");
            outputFile.close();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/HomeScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            if (Locale.getDefault().getLanguage().equals("es")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("invalid"));
                alert.setContentText(rb.getString("error"));
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Credentials");
                alert.setContentText("The username and password did not match.");
                alert.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Locale.getDefault().getLanguage().equals("es")) {
            usernameLabel.setText(rb.getString("username"));
            passwordLabel.setText(rb.getString("password"));
            logInBtn.setText(rb.getString("login"));
        }
    }
}
