package Controller;

import DAO.LoginDAO;
import Model.Session;
import Utilities.AlertInterface;
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
import java.time.Instant;
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

        // Use of lambda expression reduces the amount of code written by allowing for code re-usability.
        AlertInterface loginErrorAlert = ((title, contentText) -> {
            if (Locale.getDefault().getLanguage().equals("es")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString(title));
                alert.setContentText(rb.getString(contentText));
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(title);
                alert.setContentText(contentText);
                alert.showAndWait();
            }
        });

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
                // Lambda expression is reused
                loginErrorAlert.alertCreator("invalid", "error");
            } else {
                // Lambda expression is reused
                loginErrorAlert.alertCreator("Invalid Credentials", "The username and password did not match");
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
