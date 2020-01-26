package DAO;

import Model.Customer;
import Model.Session;
import Utilities.QueryHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    public static void loadCustomers(String userName) {
        try {
            QueryHelper.makeQuery("SELECT * FROM customer");

            ResultSet result = QueryHelper.getResult();

            while (result.next()) {
                if (result.getString("createdBy").equals(userName)) {
                    Session.allCustomers.add(new Customer(
                            result.getInt("customerId"),
                            result.getString("customerName"),
                            result.getInt("addressId"),
                            result.getBoolean("active"),
                            result.getTimestamp("createDate"),
                            result.getString("createdBy"),
                            result.getTimestamp("lastUpdate"),
                            result.getString("LastUpdateBy")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
