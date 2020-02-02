package Main;

import Model.Session;
import Utilities.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/LoginScreen.fxml"));
        primaryStage.setTitle("Global Consulting Solutions");
        primaryStage.setScene(new Scene(root, 300, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        DBConnection.startConnection();
            launch(args);
            DBConnection.closeConnection();
    }
}
