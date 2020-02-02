package DAO;

import Model.Appointment;
import Model.Customer;
import Model.Session;
import Utilities.DateAndTime;
import Utilities.QueryHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentsDAO {

    public static void loadAppointments() {
        try {
            QueryHelper.makeQuery("SELECT * FROM appointment");

            ResultSet result = QueryHelper.getResult();

            if(!Session.allAppointments.isEmpty()) {
                Session.allAppointments.clear();
            }

            while (result.next()) {
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
                        DateAndTime.UTCtoLocaltime(result.getTimestamp("start")),
                        DateAndTime.UTCtoLocaltime(result.getTimestamp("end")),
                        DateAndTime.UTCtoLocaltime(result.getTimestamp("createDate")),
                        result.getString("createdBy"),
                        DateAndTime.UTCtoLocaltime(result.getTimestamp("lastUpdate")),
                        result.getString("lastUpdateBy")
                ));
            }
        } catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return;
    }

    public static void addNewAppointment(Appointment appointment) {
        QueryHelper.makeQuery("INSERT INTO appointment VALUES ('"
                + appointment.getAppointmentId() + "', '"
                + appointment.getCustomerId() + "', '"
                + appointment.getUserId() + "', '"
                + appointment.getTitle() + "', '"
                + appointment.getDescription() + "', '"
                + appointment.getLocation() + "', '"
                + appointment.getContact() + "', '"
                + appointment.getType() + "', '"
                + appointment.getUrl() + "', '"
                + DateAndTime.localTimetoUTC(appointment.getStart()) + "', '"
                + DateAndTime.localTimetoUTC(appointment.getEnd()) + "', '"
                + DateAndTime.localTimetoUTC(appointment.getCreateDate()) + "', '"
                + appointment.getCreatedBy() + "', '"
                + DateAndTime.localTimetoUTC(appointment.getLastUpdate()) + "', '"
                + appointment.getLastUpdateBy() + "');"
        );
    }

    public static void modifyAppointment(Appointment appointment) {
        QueryHelper.makeQuery("UPDATE appointment SET customerId = '"
                + appointment.getCustomerId() + "', title = '"
                + appointment.getTitle() + "', description = '"
                + appointment.getDescription() + "', location = '"
                + appointment.getLocation() + "', contact = '"
                + appointment.getContact() + "', type = '"
                + appointment.getType() + "', url = '"
                + appointment.getUrl() + "', start = '"
                + DateAndTime.localTimetoUTC(appointment.getStart()) + "', end = '"
                + DateAndTime.localTimetoUTC(appointment.getEnd()) + "', lastUpdate = '"
                + DateAndTime.localTimetoUTC(appointment.getLastUpdate()) + "', lastUpdateBy = '"
                + appointment.getLastUpdateBy() + "' WHERE appointmentId = "
                + appointment.getAppointmentId() + ";"
        );
    }

    public static void deleteAppointment(Appointment appointment) {
        QueryHelper.makeQuery("DELETE FROM appointment WHERE appointmentId = "
                + appointment.getAppointmentId() + ";"
        );
    }
}
