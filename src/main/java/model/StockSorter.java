package model;
import java.util.ArrayList;
import java.util.List;

public class StockSorter {

    // Public method to sort a List
    public static List<BSTMotorPHInventory> mergeSort(List<BSTMotorPHInventory> stocks) {
        if (stocks.size() <= 1) return stocks;

        int mid = stocks.size() / 2;
        List<BSTMotorPHInventory> left = mergeSort(new ArrayList<>(stocks.subList(0, mid)));
        List<BSTMotorPHInventory> right = mergeSort(new ArrayList<>(stocks.subList(mid, stocks.size())));

        return merge(left, right);
    }

    // Merge two sorted lists
    private static List<BSTMotorPHInventory> merge(List<BSTMotorPHInventory> left, List<BSTMotorPHInventory> right) {
        List<BSTMotorPHInventory> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).stockLabel.compareToIgnoreCase(right.get(j).stockLabel) <= 0) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }

        while (i < left.size()) result.add(left.get(i++));
        while (j < right.size()) result.add(right.get(j++));

        return result;
    }
}