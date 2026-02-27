package com.mycompany.dsaqueuetiesms2;

import dao.MotorPHInventoryDao;
import model.MotorNode;
import model.MotorPHInventory;
import model.MotorStock;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class DSAQueueTiesMS2 {
    public static void main(String[] args) {
        var motorPHInventory = new MotorPHInventory();

        Scanner sc = new Scanner(System.in);
        List<MotorStock> stocksFromCSV = MotorPHInventoryDao.loadStocks();

        for (MotorStock stock : stocksFromCSV) {
            motorPHInventory.insertAndMap(stock);
        }

        System.out.println("Loaded Stocks:");
        for (MotorStock s : stocksFromCSV) System.out.println(s);

        //Ask user for action
        System.out.print("\nPlease state what you want to do (add/search/delete/sort): ");
        String action = sc.nextLine().trim().toLowerCase();

        switch (action) {

            case "add" -> {
                System.out.print("Enter Date Entered (DD/MM/YYYY): ");
                String date = sc.nextLine();
                System.out.print("Enter Stock Label: ");
                String label = sc.nextLine();
                System.out.print("Enter Brand: ");
                String brand = sc.nextLine();
                System.out.print("Enter Engine Number: ");
                String engine = sc.nextLine();
                System.out.print("Enter Status: ");
                String status = sc.nextLine();

                MotorStock newStock = new MotorStock(date, label, brand, engine, status);

                //Add to BST and hashedMap
                motorPHInventory.insertAndMap(newStock);

                //Insert to csv
                MotorPHInventoryDao.addStock(newStock);

                System.out.println("Stock added successfully!");
            }

            case "search" -> {
                System.out.print("Which do you want to search(Engine(Engine Number) or Brand): ");
                String choice = sc.nextLine().toLowerCase().trim();
                switch(choice){
                    case "brand" -> {
                        System.out.print("Please insert brand: ");
                        String brand = sc.nextLine().trim();
                        List<MotorStock> foundStocks = motorPHInventory.searchByBrand(brand);
                        if (!foundStocks.isEmpty()) {
                            foundStocks.forEach(System.out::println);
                        } else {
                            System.out.println("Brand not found!");
                        }
                    }
                    case "engine" -> {
                        System.out.print("Please insert engine number: ");
                        String engine = sc.nextLine().trim();
                        MotorStock foundStock = motorPHInventory.searchByEngineNumber(engine);
                        if (foundStock != null) {
                            System.out.println("Found: " + foundStock);
                        } else {
                            System.out.println("Engine number not found!");
                        }
                    }
                    default -> {
                        System.out.println("Invalid choice!");
                    }
                }
            }

            case "delete" -> {
                System.out.print("Enter Engine Number to delete: ");
                String engine = sc.nextLine().trim();

                // Delete from BST + HashMaps
                motorPHInventory.deleteByEngineNumber(engine);

                // Also remove from CSV list and save
                stocksFromCSV.removeIf(s -> s.getEngineNumber().equals(engine));
                MotorPHInventoryDao.saveStocks(stocksFromCSV);

                System.out.println("Stock deleted successfully (if it existed)!");
            }

            case "sort" -> {
                System.out.print("Sort by(brand or date): ");
                String choice = sc.nextLine().toLowerCase().trim();
                switch(choice){
                    case "brand" -> {
                        // Convert HashMap values to List for Merge Sort
                        List<MotorStock> sortedStocks = motorPHInventory.mergeSortByBrand(new ArrayList<>(stocksFromCSV));

                        System.out.println("\nStocks sorted by Stock Label:");
                        for (MotorStock s : sortedStocks) System.out.println(s);
                        MotorPHInventoryDao.saveStocks(sortedStocks);
                    }
                    case "date" -> {
                        // Convert HashMap values to List for Merge Sort
                        List<MotorStock> sortedStocks = motorPHInventory.mergeSortByDate(new ArrayList<>(stocksFromCSV));

                        System.out.println("\nStocks sorted by Stock Label:");
                        for (MotorStock s : sortedStocks) System.out.println(s);
                        MotorPHInventoryDao.saveStocks(sortedStocks);
                    }
                    default -> {
                        System.out.println("Invalid choice!");
                    }
                }
            }

            default -> {
                System.out.println("This is not a valid action!");
            }
        }
    }

}
