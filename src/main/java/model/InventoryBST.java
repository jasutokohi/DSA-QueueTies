package model;

public class InventoryBST {
    private Stock root;

    public InventoryBST() {
        this.root = null;
    }

    // Recursive add
    public void add(Stock stock) {
        root = addRecursive(root, stock);
    }

    private Stock addRecursive(Stock node, Stock stock) {
        if (node == null) return stock; // root or leaf

        if (stock.getStockLabel().compareToIgnoreCase(node.getStockLabel()) < 0) {
            // left child logic (you may need a wrapper class with left/right)
        } else if (stock.getStockLabel().compareToIgnoreCase(node.getStockLabel()) > 0) {
            // right child logic
        }
        return node;
    }

    // Recursive Delete
    public void delete(String stockLabel) {
        root = deleteRecursive(root, stockLabel);
    }

    private BSTMotorPHInventory deleteRecursive(Stock node, String stockLabel) {
        if (node == null) return null;

        if (stockLabel.compareToIgnoreCase(node.stockLabel) < 0) {
            node.left = deleteRecursive(node.left, stockLabel);
        } else if (stockLabel.compareToIgnoreCase(node.stockLabel) > 0) {
            node.right = deleteRecursive(node.right, stockLabel);
        } else {
            // Node to delete found
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // Node with two children: get inorder successor
            BSTMotorPHInventory successor = findMin(node.right);
            node.stockLabel = successor.stockLabel;
            node.engineNumber = successor.engineNumber;
            node.brand = successor.brand;
            node.dateEntered = successor.dateEntered;
            node.status = successor.status;

            node.right = deleteRecursive(node.right, successor.stockLabel);
        }

        return node;
    }
    
    private BSTMotorPHInventory findMin(BSTMotorPHInventory node) {
        while (node.left != null) node = node.left;
        return node;
    }
}