package DAO;

import Model.Appointment;
import Model.Session;
import Utilities.QueryHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentsDAO {

    public static void loadAppointments(int userId) {
        try {
            QueryHelper.makeQuery("SELECT * FROM appointment");

            ResultSet result = QueryHelper.getResult();

            while (result.next()) {
                if(userId == result.getInt("userId")) {
                    Session.allAppointments.add(new Appointment(
                            result.getInt("appointmentId"),
                            result.getInt("customerId"),
                            result.getInt("userId"),
                            result.getString("title"),
                            result.getString("description"),
                            result.getString("location"),
                            result.getString("contact"),
                            result.getString("type"),
                            result.getString("url"),
                            result.getTimestamp("start"),
                            result.getTimestamp("end"),
                            result.getTimestamp("createDate"),
                            result.getString("createdBy"),
                            result.getTimestamp("lastUpdate"),
                            result.getString("lastUpdateBy")
                    ));
                }
            }
        } catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return;
    }
}
