package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Directory {

    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    public static ObservableList<City> allCities = FXCollections.observableArrayList();
    public static ObservableList<Country> allCountries = FXCollections.observableArrayList();

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static void addCustomer(Customer customer) {
        Directory.allCustomers.add(customer);
    }

    public static void deleteCustomer(Customer customer) {
        Directory.allCustomers.remove(customer);
    }

    public static ObservableList<City> getAllCities() {
        return allCities;
    }

    public static void addCity(City city) {
        Directory.allCities.add(city);
    }

    public static void deleteCity(City city) {
        Directory.allCities.remove(city);
    }

    public static ObservableList<Country> getAllCountries() {
        return allCountries;
    }

    public static void addCountry(Country country) {
        Directory.allCountries.add(country);
    }

    public static void deleteCustomer(Country country) {
        Directory.allCountries.remove(country);
    }
}
