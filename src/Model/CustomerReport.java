package Model;

public class CustomerReport {

    private String customerName;
    private int numberOfAppts = 0;

    public CustomerReport(String customerName) {
        this.customerName = customerName;
    }

    public CustomerReport(String customerName, int numberOfAppts) {
        this.customerName = customerName;
        this.numberOfAppts = numberOfAppts;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getNumberOfAppts() {
        return numberOfAppts;
    }

    public void setNumberOfAppts(int numberOfAppts) {
        this.numberOfAppts = numberOfAppts;
    }
}
