package Model;

public class AppointmentTypesReport {

    private String type;
    private int amountOfType;

    public AppointmentTypesReport(String type, int amountOfType) {
        this.type = type;
        this.amountOfType = amountOfType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmountOfType() {
        return amountOfType;
    }

    public void setAmountOfType(int amountOfType) {
        this.amountOfType = amountOfType;
    }
}
