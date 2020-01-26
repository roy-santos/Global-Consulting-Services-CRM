package DAO;

import Model.Country;
import Model.Session;
import Utilities.QueryHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesDAO {

    public static void loadCountries() {
        try {
            QueryHelper.makeQuery("SELECT * FROM country");

            ResultSet result = QueryHelper.getResult();

            while (result.next()) {
                Session.allCountries.add(new Country(
                result.getInt("countryId"),
                result.getString("country"),
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
