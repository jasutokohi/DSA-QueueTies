package model;

public class BSTMotorPHInventory {
    String engineNumber;   
    String dateEntered;
    String stockLabel;
    String brand;
    String status;

    BSTMotorPHInventory left;
    BSTMotorPHInventory right;
    
    public BSTMotorPHInventory(String engineNumber, String dateEntered, String stockLabel, String brand, String status) {
        this.engineNumber = engineNumber;
        this.dateEntered = dateEntered;
        this.stockLabel = stockLabel;
        this.brand = brand;
        this.status = status;
        this.left = null;
        this.right = null;
    }
}


