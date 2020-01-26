package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class Customer {

    private int customerId;
    private String customerName;
    private int addressId;
    private boolean active;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    public void addAppointment(Appointment appt) {
        this.allAppointments.add(appt);
    }

    public void deleteAppointment(Appointment appt) {
        this.allAppointments.remove(appt);
    }

    public Customer(int customerId, String customerName, int addressId, boolean active, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }
}
