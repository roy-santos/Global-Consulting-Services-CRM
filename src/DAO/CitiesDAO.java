package DAO;

import Model.City;
import Model.Session;
import Utilities.QueryHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CitiesDAO {

    public static void loadCities() {
        try {
            QueryHelper.makeQuery("SELECT * FROM city");

            ResultSet result = QueryHelper.getResult();

            while (result.next()) {
                Session.allCities.add(new City(
                        result.getInt("cityId"),
                        result.getString("city"),
                        result.getInt("countryId"),
                        result.getTimestamp("createDate"),
                        result.getString("createdBy"),
                        result.getTimestamp("lastUpdate"),
                        result.getString("lastUpdateBy")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
