package DAO;

import Model.Address;
import Model.Country;
import Model.Session;
import Utilities.DateAndTime;
import Utilities.QueryHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class AddressDAO {

    public static void loadAddresses() {
        try {
            QueryHelper.makeQuery("SELECT * FROM address");

            ResultSet result = QueryHelper.getResult();

            if(!Session.allAddresses.isEmpty()) {
                Session.allAddresses.clear();
            }

            while (result.next()) {
                Session.allAddresses.add(new Address(
                        result.getInt("addressId"),
                        result.getString("address"),
                        result.getString("address2"),
                        result.getInt("cityId"),
                        result.getString("postalCode"),
                        result.getString("phone"),
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

    public static void addNewAddress(Address address) {
        QueryHelper.makeQuery("INSERT INTO address VALUES ('"
                + address.getAddressId() + "', '"
                + address.getAddress() + "', '"
                + address.getAddress2() + "', '"
                + address.getCityId() + "', '"
                + address.getPostalCode() + "', '"
                + address.getPhone() + "', '"
                + DateAndTime.localTimetoUTC(address.getCreateDate()) + "', '"
                + address.getCreatedBy() + "', '"
                + DateAndTime.localTimetoUTC(address.getLastUpdate()) + "', '"
                + address.getLastUpdateBy() + "');"
        );
    }

    public static void modifyAddress(Address address) {
        QueryHelper.makeQuery("UPDATE address SET address = '"
                + address.getAddress() + "', address2 = '"
                + address.getAddress2() + "', cityId = '"
                + address.getCityId() + "', postalCode = '"
                + address.getPostalCode() + "', phone = '"
                + address.getPhone() + "', lastUpdate = '"
                + DateAndTime.localTimetoUTC(address.getLastUpdate()) + "', lastUpdateBy = '"
                + address.getLastUpdateBy() + "' WHERE addressId = "
                + address.getAddressId() + ";"
        );
    }

    public static void deleteAddress(int addressId) {
        QueryHelper.makeQuery("DELETE FROM address WHERE addressId = "
                + addressId + ";"
        );
    }
}
