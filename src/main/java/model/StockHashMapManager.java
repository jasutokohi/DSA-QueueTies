package model;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StockHashMapManager {
    private Map<String, Stock> stockMap;

    public StockHashMapManager() {
        stockMap = new HashMap<>();
    }

    public void addStock(Stock stock) {
        stockMap.put(stock.getStockLabel().toLowerCase(), stock);
    }

    public Stock searchStock(String stockLabel) {
        return stockMap.get(stockLabel.toLowerCase());
    }

    public void deleteStock(String stockLabel) {
        stockMap.remove(stockLabel.toLowerCase());
    }

    public Collection<Stock> getAllStocks() {
        return stockMap.values();
    }
}

