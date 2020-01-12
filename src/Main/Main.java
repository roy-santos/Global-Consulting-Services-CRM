package Main;

import DAO.DBConnection;
import DAO.QueryHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View_Controller/sample.fxml"));
        primaryStage.setTitle("JDBC App");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {

        int employeeID;
        String employeeName, department, hireDate;

        // Create Scanner Object
        Scanner keyboard = new Scanner(System.in);

        // Get input
        System.out.print("Enter employee id: ");
        employeeID = keyboard.nextInt();

//        System.out.print("Enter employee name: ");
//        employeeName = keyboard.nextLine();

//        System.out.print("Enter department name: ");
//        department = keyboard.nextLine();

//        System.out.print("Enter hire date (YYYY-MM-DD HH:MM:SS): ");
//        hireDate = keyboard.nextLine();

        try {
            DBConnection.startConnection();

            // Create Statement object
//            Statement stmt = DBConnection.conn.createStatement();

            // Write SQL insert statement
//            String sqlStatement = "INSERT INTO employee_tbl(EmployeeName, Department, HireDate)" +
//                            "VALUES (" +
//                            "'" + employeeName + "', " +
//                            "'" + department + "', " +
//                            "'" + hireDate + "'" +
//                            ")";


            // Write SQL delete statement
            String sqlStatement = "DELETE FROM employee_tbl WHERE EmployeeID = " + String.valueOf(employeeID);

            //Execute INSERT statement
//            stmt.executeUpdate(sqlStatement);
            QueryHelper.makeQuery(sqlStatement);


            // Write SQL statement
             sqlStatement = "SELECT * FROM employee_tbl";
             QueryHelper.makeQuery(sqlStatement);

            // Execute Statement and Create ResultSet object
            ResultSet result = QueryHelper.getResult();
            // Gets all records from ResultSet object
            while(result.next()) {
                System.out.print(result.getInt("EmployeeID") + ", ");
                System.out.print(result.getString("EmployeeName") + ", ");
                System.out.print(result.getString("Department") + ", ");
                System.out.print(result.getDate("HireDate"));
                System.out.print(result.getTime("HireDate"));
                System.out.println();
            }

            launch(args);
            DBConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
}
