package DAO;

import Model.Country;
import Model.Session;
import Utilities.DateAndTime;
import Utilities.QueryHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesDAO {

    public static void loadCountries() {
        try {
            QueryHelper.makeQuery("SELECT * FROM country");

            ResultSet result = QueryHelper.getResult();

            if(!Session.allCountries.isEmpty()) {
                Session.allCountries.clear();
            }

            while (result.next()) {
                Session.allCountries.add(new Country(
                        result.getInt("countryId"),
                        result.getString("country"),
                        DateAndTime.UTCtoLocaltime(result.getTimestamp("createDate")),
                        result.getString("createdBy"),
                        DateAndTime.UTCtoLocaltime(result.getTimestamp("lastUpdate")),
                        result.getString("lastUpdateBy")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void addNewCountry(Country country) {
        QueryHelper.makeQuery("INSERT INTO country VALUES ('"
                + country.getCountryId() + "', '"
                + country.getCountry() + "', '"
                + DateAndTime.localTimetoUTC(country.getCreateDate()) + "', '"
                + country.getCreatedBy() + "', '"
                + DateAndTime.localTimetoUTC(country.getLastUpdate()) + "', '"
                + country.getLastUpdateBy() + "');"
        );
    }

    public static void modifyCountry(Country country) {
        QueryHelper.makeQuery("UPDATE country SET country = '"
                + country.getCountry() + "', lastUpdate = '"
                + DateAndTime.localTimetoUTC(country.getLastUpdate()) + "', lastUpdateBy = '"
                + country.getLastUpdateBy() + "' WHERE countryId = "
                + country.getCountryId() + ";"
        );
    }

    public static void deleteCountry(Country country) {
        QueryHelper.makeQuery("DELETE FROM country WHERE countryId = "
                + country.getCountryId() + ";"
        );
    }
}
