package Model;

import java.time.LocalDateTime;

public class ScheduleReport {

    private String customerName;
    private String type;
    private LocalDateTime dateAndTime;

    public ScheduleReport(String customerName, String type, LocalDateTime dateAndTime) {
        this.customerName = customerName;
        this.type = type;
        this.dateAndTime = dateAndTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
