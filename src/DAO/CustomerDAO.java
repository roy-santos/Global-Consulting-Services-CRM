package DAO;

import Model.Address;
import Model.Customer;
import Model.Session;
import Utilities.DateAndTime;
import Utilities.QueryHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    public static void loadCustomers() {
        try {
            QueryHelper.makeQuery("SELECT * FROM customer");

            ResultSet result = QueryHelper.getResult();

            if(!Session.allCustomers.isEmpty()) {
                Session.allCustomers.clear();
            }

            while (result.next()) {
                    Session.allCustomers.add(new Customer(
                            result.getInt("customerId"),
                            result.getString("customerName"),
                            result.getInt("addressId"),
                            result.getBoolean("active"),
                            DateAndTime.UTCtoLocaltime(result.getTimestamp("createDate")),
                            result.getString("createdBy"),
                            DateAndTime.UTCtoLocaltime(result.getTimestamp("lastUpdate")),
                            result.getString("LastUpdateBy")
                    ));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void addNewCustomer(Customer customer) {
        QueryHelper.makeQuery("INSERT INTO customer VALUES ('"
                + customer.getCustomerId() + "', '"
                + customer.getCustomerName() + "', '"
                + customer.getAddressId() + "', '"
                + customer.isActive() + "', '"
                + DateAndTime.localTimetoUTC(customer.getCreateDate()) + "', '"
                + customer.getCreatedBy() + "', '"
                + DateAndTime.localTimetoUTC(customer.getLastUpdate()) + "', '"
                + customer.getLastUpdateBy() + "');"
        );
    }

    public static void modifyCustomer(Customer customer) {
        QueryHelper.makeQuery("UPDATE customer SET customerName = '"
                + customer.getCustomerName() + "', active = '"
                + customer.isActive() + "', lastUpdate = '"
                + DateAndTime.localTimetoUTC(customer.getLastUpdate()) + "', lastUpdateBy = '"
                + customer.getLastUpdateBy() + "' WHERE customerId = "
                + customer.getCustomerId() + ";"
        );
    }

    public static void deleteCustomer(Customer customer) {
        QueryHelper.makeQuery("DELETE FROM customer WHERE customerId = "
                + customer.getCustomerId() + ";"
        );

        AddressDAO.deleteAddress(customer.getAddressId());
    }
}
