package model;

//Data class
public class MotorStock {
    private final String dateEntered;
    private final String stockLabel;
    private final String brand;
    private final String engineNumber;
    private final String status;

    public MotorStock(String dateEntered, String stockLabel, String brand,
                 String engineNumber, String status) {
        this.dateEntered = dateEntered;
        this.stockLabel = stockLabel;
        this.brand = brand;
        this.engineNumber = engineNumber;
        this.status = status;
    }

    public String toString(){
        return dateEntered + " | " + stockLabel + " | " + brand + " | " + engineNumber + " | " + status;
    }

    public String getDateEntered() {
        return dateEntered;
    }

    public String getStockLabel() {
        return stockLabel;
    }

    public String getBrand() {
        return brand;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public String getStatus() {
        return status;
    }
}