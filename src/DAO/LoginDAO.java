package DAO;

import Model.Session;
import Utilities.QueryHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    public static boolean authenticateLogin(String user, String pass) {
        try {
            QueryHelper.makeQuery("SELECT * FROM user");
            ResultSet result = QueryHelper.getResult();

            while(result.next()) {
                if(user.equals(result.getString("userName")) && pass.equals(result.getString("password"))) {
                    Session.createSessionUser(
                            result.getInt("userId"),
                            result.getString("userName"),
                            result.getString("password"),
                            result.getBoolean("active"),
                            result.getTimestamp("createDate"),
                            result.getString("createdBy"),
                            result.getTimestamp("lastUpdate"),
                            result.getString("lastUpdate")
                    );
                    return true;
                }
            }
        } catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

}
