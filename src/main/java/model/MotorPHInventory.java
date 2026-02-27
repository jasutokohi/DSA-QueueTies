package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Inventory system class
public class MotorPHInventory {
        MotorNode root;   // Root of the Binary Search Tree



        // INSERT INTO BST (Recursive)
        MotorNode insert(MotorNode node, MotorStock motorStock) {

            // If current position is empty, create new node
            if (node == null) {
                return new MotorNode(motorStock);
            }

            // Compare flavor alphabetically
            // If new flavor comes BEFORE current node
            if (motorStock.getEngineNumber().compareTo(node.data.getEngineNumber()) < 0) {

                // Insert into left subtree (recursive call)
                node.left = insert(node.left, motorStock);

            } else {

                // Otherwise insert into right subtree
                node.right = insert(node.right, motorStock);
            }

            // Return unchanged node pointer
            return node;
        }

        //Recursive delete
        MotorNode delete(MotorNode node, String engineNumber) {
            if (node == null) return null;

            int cmp = engineNumber.compareTo(node.data.getEngineNumber());

            if (cmp < 0) {
                node.left = delete(node.left, engineNumber);
            } else if (cmp > 0) {
                node.right = delete(node.right, engineNumber);
            } else {
                // Node found

                // Case 1: No child
                if (node.left == null && node.right == null) return null;

                // Case 2: One child
                if (node.left == null) return node.right;
                if (node.right == null) return node.left;

                // Case 3: Two children
                MotorNode successor = findMin(node.right);
                node.data = successor.data;
                node.right = delete(node.right, successor.data.getEngineNumber());
            }

            return node;
        }

        public void deleteByEngineNumber(String engineNumber) {
            MotorStock stockToDelete = engineMap.get(engineNumber);
            if (stockToDelete != null) removeFromMaps(stockToDelete);  // update HashMaps
            root = delete(root, engineNumber);                // call recursive delete internally
        }

        //Hashmaps for looking up engine or brand
        HashMap<String, MotorStock> engineMap  = new HashMap<>();
        HashMap<String, List<MotorStock>> brandMap = new HashMap<>();

        //Insert to both
        public void insertAndMap(MotorStock stock) {
            root = insert(root, stock);
            engineMap.put(stock.getEngineNumber(), stock);
        }

        //search by engine number
        public MotorStock searchByEngineNumber(String engineNumber) {
            return engineMap.get(engineNumber);
        }

        //Search by brand
        public List<MotorStock> searchByBrand(String brand) {
            return brandMap.getOrDefault(brand, new ArrayList<>());  // Returns empty list if brand not found
        }

        //If deleted it will remove from maps
        public void removeFromMaps(MotorStock stock) {
            engineMap.remove(stock.getEngineNumber());

            List<MotorStock> list = brandMap.get(stock.getBrand());
            if (list != null) {
                list.remove(stock);
                if (list.isEmpty()) {
                    brandMap.remove(stock.getBrand());
                }
            }
        }


        // INORDER TRAVERSAL (Sorted Print)
        public void inorder(MotorNode node) {

            // Base condition
            if (node != null) {

                // Visit left subtree first
                inorder(node.left);

                // Print current node
                System.out.println(node.data);

                // Visit right subtree
                inorder(node.right);
            }
        }


        // MERGE SORT (Sort by Brand)
        public List<MotorStock> mergeSortByBrand(List<MotorStock> list) {

            // Base case: if list has 1 or 0 elements, it's already sorted
            if (list.size() <= 1)
                return list;

            // Find middle index
            int mid = list.size() / 2;

            // Recursively sort left half
            List<MotorStock> left = mergeSortByBrand(list.subList(0, mid));

            // Recursively sort right half
            List<MotorStock> right = mergeSortByBrand(list.subList(mid, list.size()));

            // Merge sorted halves
            return mergeBrand(left, right);
        }

        public List<MotorStock> mergeBrand(List<MotorStock> left, List<MotorStock> right) {
            List<MotorStock> result = new ArrayList<>();

            int i = 0;  // Pointer for left list
            int j = 0;  // Pointer for right list

            while (i < left.size() && j < right.size()) {
                // Compare Brand lexicographically
                if (left.get(i).getBrand().compareToIgnoreCase(right.get(j).getBrand()) < 0) {
                    result.add(left.get(i));
                    i++;
                } else {
                    result.add(right.get(j));
                    j++;
                }
            }

            // Add remaining elements
            while (i < left.size()) {
                result.add(left.get(i++));
            }
            while (j < right.size()) {
                result.add(right.get(j++));
            }

            return result;
        }

    // MERGE SORT (Sort by Brand)
    public List<MotorStock> mergeSortByDate(List<MotorStock> list) {

        // Base case: if list has 1 or 0 elements, it's already sorted
        if (list.size() <= 1)
            return list;

        // Find middle index
        int mid = list.size() / 2;

        // Recursively sort left half
        List<MotorStock> left = mergeSortByDate(list.subList(0, mid));

        // Recursively sort right half
        List<MotorStock> right = mergeSortByDate(list.subList(mid, list.size()));

        // Merge sorted halves
        return mergeBrand(left, right);
    }

    public List<MotorStock> mergeByDate(List<MotorStock> left, List<MotorStock> right) {
        List<MotorStock> result = new ArrayList<>();

        int i = 0;  // Pointer for left list
        int j = 0;  // Pointer for right list

        while (i < left.size() && j < right.size()) {
            // Compare Brand lexicographically
            if (left.get(i).getDateEntered().compareToIgnoreCase(right.get(j).getDateEntered()) < 0) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }

        // Add remaining elements
        while (i < left.size()) {
            result.add(left.get(i++));
        }
        while (j < right.size()) {
            result.add(right.get(j++));
        }

        return result;
    }

        // Helper method to find the minimum node in a subtree
        private MotorNode findMin(MotorNode node) {
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }
}

