package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.util.Date;

public class Session {

    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    public static ObservableList<City> allCities = FXCollections.observableArrayList();
    public static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    public static User currentUser;

    public static void createSessionUser(int userId, String username, String password, Boolean active, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy){
        currentUser = new User(userId, username, password, active, createDate, createdBy, lastUpdate, lastUpdateBy);
    }

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static void addCustomer(Customer customer) {
        Session.allCustomers.add(customer);
    }

    public static void deleteCustomer(Customer customer) {
        Session.allCustomers.remove(customer);
    }

    public static ObservableList<City> getAllCities() {
        return allCities;
    }

    public static void addCity(City city) {
        Session.allCities.add(city);
    }

    public static void deleteCity(City city) {
        Session.allCities.remove(city);
    }

    public static ObservableList<Country> getAllCountries() {
        return allCountries;
    }

    public static void addCountry(Country country) {
        Session.allCountries.add(country);
    }

    public static void deleteCustomer(Country country) {
        Session.allCountries.remove(country);
    }

    public static void endSession() {
        allCustomers.clear();
        allCities.clear();
        allCountries.clear();
        allAppointments.clear();
        currentUser = null;
    }
}
