package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;

public class Session {

    public static User currentUser;
    public static ObservableList<Address> allAddresses = FXCollections.observableArrayList();
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    public static ObservableList<City> allCities = FXCollections.observableArrayList();
    public static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    public static final ObservableList<String> allTimes = FXCollections.observableArrayList(
            "09:00 AM", "09:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM",
            "12:00 PM", "12:30 PM", "01:00 PM", "01:30 PM", "02:00 PM", "02:30 PM", "03:00 PM",
            "03:30 PM", "04:00 PM", "04:30 PM", "05:00 PM"
    );

    public static void createSessionUser(int userId, String username, String password, Boolean active, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy){
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
        allAddresses.clear();
        allCustomers.clear();
        allCities.clear();
        allCountries.clear();
        allAppointments.clear();
        currentUser = null;
    }
}
