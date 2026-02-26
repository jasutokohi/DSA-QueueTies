package dao;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import model.Stock;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class MotorPHStocksDao {
    
    private static final String FILE_PATH = "files/MotorPHInventory.csv";
    private static final String[] HEADERS = {"Date Entered", "Stock Label", "Brand", "Engine Number", "Status"};

    public static List<Stock> loadStocks(){
            List<Stock> stockList = new ArrayList<>();
            
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
                        String dateEntered = row[0];
                        String stockLabel = row[1];
                        String brand = row[2];
                        String engineNumber = row[3]; 
                        String status = row[4];
                        
                        Stock stock = new Stock(dateEntered, stockLabel, brand, engineNumber, status);
                        stockList.add(stock);
                    }
                }
            }  
        }catch(Exception e){
            System.err.println("Error reading from CSV: " + e.getMessage());
        }
            
            return stockList;
    }
}
