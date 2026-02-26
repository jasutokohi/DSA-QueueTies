package com.mycompany.ms2.dsa;

import model.Stock;
import dao.MotorPHStocksDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.BSTMotorPHInventory;
import model.InventoryBST;
import model.StockHashMapManager;
import model.StockSorter;

public class MS2DSA {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 1️⃣ Load from CSV
        List<Stock> stocksFromCSV = MotorPHStocksDao.loadStocks();

        // 2️⃣ Initialize BST and HashMap managers
        InventoryBST bst = new InventoryBST();
        StockHashMapManager mapManager = new StockHashMapManager();

        // Add all stocks to BST and HashMap
        for (Stock s : stocksFromCSV) {
        bst.add(s);           
        mapManager.addStock(s); 
        }

        // 3️⃣ Show loaded stocks
        System.out.println("Loaded Stocks:");
        for (Stock s : stocksFromCSV) System.out.println(s);

        // 4️⃣ Ask user for action
        System.out.print("\nPlease state what you want to do (add/search/delete/sort): ");
        String action = sc.nextLine().trim().toLowerCase();

        switch (action) {

            case "add" -> {
                System.out.print("Enter Stock Label: ");
                String label = sc.nextLine();
                System.out.print("Enter Brand: ");
                String brand = sc.nextLine();
                System.out.print("Enter Engine Number: ");
                String engine = sc.nextLine();
                System.out.print("Enter Status: ");
                String status = sc.nextLine();
                System.out.print("Enter Date Entered (YYYY-MM-DD): ");
                String date = sc.nextLine();

                Stock newStock = new Stock(date, label, brand, engine, status);

                // Recursive BST add
                bst.add(newStock);

                // HashMap add for fast search
                mapManager.addStock(newStock);

                System.out.println("Stock added successfully!");
            }

            case "search" -> {
                System.out.print("Enter Stock Label to search: ");
                String label = sc.nextLine();
                BSTMotorPHInventory found = mapManager.searchStock(label); // search via HashMap
                if (found != null) System.out.println("Found: " + found);
                else System.out.println("Stock not found!");
            }

            case "delete" -> {
                System.out.print("Enter Stock Label to delete: ");
                String label = sc.nextLine();

                // Recursive BST delete
                bst.delete(label);

                System.out.println("Stock deleted successfully (if it existed)!");
            }

            case "sort" -> {
                System.out.println("\nStocks sorted by Stock Label:");

                // Convert HashMap values to List for Merge Sort
                List<Stock> stockList = new ArrayList<>(mapManager.getAllStocks());

                // Merge sort by Stock Label
                List<Stock> sorted = StockSorter.mergeSort(stockList);

                for (Stock s : sorted) System.out.println(s);
            }

            default -> {
                System.out.println("This is not a valid action!");
            }
        }
    }
}
