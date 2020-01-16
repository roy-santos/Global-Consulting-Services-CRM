package DAO;

import Utilities.QueryHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuthentication {

    public static boolean authenticate(String user, String pass) {
        try {
            QueryHelper.makeQuery("SELECT userName, password FROM user");

            ResultSet result = QueryHelper.getResult();

            while(result.next()) {
                if(user.equals(result.getString("userName")) && pass.equals(result.getString("password"))) {
                    return true;
                }
            }
        } catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

}
