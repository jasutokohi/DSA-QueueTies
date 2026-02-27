package dao;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import model.MotorStock;
import java.io.*;
import java.util.*;

public class MotorPHInventoryDao {

    private static final String FILE_PATH = "files/MotorPHInventory.csv";
    private static final String[] HEADERS = {"Date Entered", "Stock Label", "Brand", "Engine Number", "Status"};


    // Add a single stock to CSV
    public static void addStock(MotorStock stock) {
        try {
            File file = new File(FILE_PATH);

            // Ensure folder exists
            File folder = file.getParentFile();
            if (!folder.exists()) folder.mkdir();

            // Check if file exists
            boolean fileExists = file.exists();

            try (CSVWriter writer = new CSVWriter(new FileWriter(file, true),
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END)) {

                // Write headers if file is new
                if (!fileExists) {
                    writer.writeNext(HEADERS);
                }

                // Write the new stock row
                String[] row = {
                        stock.getDateEntered(),
                        stock.getStockLabel(),
                        stock.getBrand(),
                        stock.getEngineNumber(),
                        stock.getStatus()
                };
                writer.writeNext(row);  // CSVWriter handles the line break automatically
            }

            System.out.println("Stock saved to CSV successfully!");
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }

    public static List<MotorStock> loadStocks(){
        List<MotorStock> stockList = new ArrayList<>();

        try{
            //Ensure folder exists
            File folder = new File("files");
            if(!folder.exists()){
                folder.mkdir();
            }

            File file = new File(FILE_PATH);

            //create file with headers if doesn't exist
            if(!file.exists()){
                try(CSVWriter writer = new CSVWriter(new FileWriter(file,true))) {
                    writer.writeNext(HEADERS, false);
                }
                return stockList;
            }
            //Read CSV
            try(CSVReader reader = new CSVReader(new FileReader(file))){
                List<String[]> rows = reader.readAll();

                //Skip header row then parse remaining rows
                for(int i = 1; i < rows.size(); i++){
                    String[] row = rows.get(i);
                    if(row.length >= 5){
                        MotorStock stock = new MotorStock(
                        row[0].trim(),
                        row[1].trim(),
                        row[2].trim(),
                        row[3].trim(),
                        row[4].trim()
                        );
                        stockList.add(stock);
                    }
                }
            }
        }catch(Exception e){
            System.err.println("Error reading from CSV: " + e.getMessage());
        }

        return stockList;
    }

    public static void saveStocks(List<MotorStock> stockList) {
        File file = new File(FILE_PATH);
        try {
            // Ensure folder exists
            File folder = new File("files");
            if (!folder.exists()) {
                folder.mkdir();
            }

            try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
                // Write header
                writer.writeNext(HEADERS, false);

                // Write each stock row
                for (MotorStock stock : stockList) {
                    String[] row = {
                            stock.getDateEntered().trim(),
                            stock.getStockLabel().trim(),
                            stock.getBrand().trim(),
                            stock.getEngineNumber().trim(),
                            stock.getStatus().trim()
                    };
                    writer.writeNext(row, false);
                }
            }

        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }

}

