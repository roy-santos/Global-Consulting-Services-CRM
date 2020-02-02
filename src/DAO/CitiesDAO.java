package DAO;

import Model.City;
import Model.Country;
import Model.Session;
import Utilities.DateAndTime;
import Utilities.QueryHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CitiesDAO {

    public static void loadCities() {
        try {
            QueryHelper.makeQuery("SELECT * FROM city");

            ResultSet result = QueryHelper.getResult();

            if(!Session.allCities.isEmpty()) {
                Session.allCities.clear();
            }

            while (result.next()) {
                Session.allCities.add(new City(
                        result.getInt("cityId"),
                        result.getString("city"),
                        result.getInt("countryId"),
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

    public static void addNewCity(City city) {
        QueryHelper.makeQuery("INSERT INTO city VALUES ('"
                + city.getCityId() + "', '"
                + city.getCity() + "', '"
                + city.getCountryId() + "', '"
                + DateAndTime.localTimetoUTC(city.getCreateDate()) + "', '"
                + city.getCreatedBy() + "', '"
                + DateAndTime.localTimetoUTC(city.getLastUpdate()) + "', '"
                + city.getLastUpdateBy() + "');"
        );
    }

    public static void modifyCity(City city) {
        QueryHelper.makeQuery("UPDATE city SET city = '"
                + city.getCity() + "', countryId = '"
                + city.getCountryId() + "', lastUpdate = '"
                + DateAndTime.localTimetoUTC(city.getLastUpdate()) + "', lastUpdateBy = '"
                + city.getLastUpdateBy() + "' WHERE cityId = "
                + city.getCityId() + ";"
        );
    }

    public static void deleteCity(City city) {
        QueryHelper.makeQuery("DELETE FROM city WHERE cityId = "
                + city.getCityId() + ";"
        );
    }
}
