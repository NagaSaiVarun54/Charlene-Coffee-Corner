package org.example.coffeeservice;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> products = new ArrayList<>();
    private List<Extra> extras = new ArrayList<>();
    private int beverageCount = 0; // Counter to track beverages for discount
    private boolean beverageAndSnackOrder = false; // To track if a beverage and snack are ordered together

    // Method to add a product to the cart
    public void addProduct(Product product) {
        products.add(product);

        // Check if the product is a beverage (coffee or orange juice)
        if (isBeverage(product)) {
            beverageCount++;
        }

        // Check if a beverage and snack are ordered together
        if (hasSnackInCart()) {
            if(isBeverage(products)){
            beverageAndSnackOrder = true;
            }
        }
    }

    // Method to add an extra to the cart
    public void addExtra(Extra extra) {
        extras.add(extra);
    }

    // Method to generate the receipt
    public String generateReceipt() {
        double total = 0;
        StringBuilder receipt = new StringBuilder("Receipt:\n");

        int beverageIndex = 0; // To track which beverage is the 5th for discount

        // Add products (beverages and snacks)
        for (Product product : products) {
            if (isBeverage(product)) {
                beverageIndex++;
                if (beverageIndex % 5 == 0) {
                    receipt.append(product.getName()).append(": CHF 0.0 (5th Beverage Free)\n");
                    continue; // Skip adding this beverage's price to total
                }
            }
            receipt.append(product.getName()).append(": CHF ").append(product.getPrice()).append("\n");
            total += product.getPrice();
        }

        // Add extras
        for (Extra extra : extras) {
            receipt.append(extra.getName()).append(": CHF ").append(extra.getPrice()).append("\n");
            total += extra.getPrice();
        }

        // Apply discount for free extra if a beverage and snack were ordered together
        if (beverageAndSnackOrder) {
            // Apply discount: Free Extra (choose the first extra in the list)
            if (!extras.isEmpty()) {
                total -= extras.get(0).getPrice();
                receipt.append("Discount applied: Free Extra - ").append(extras.get(0).getName())
                        .append(": CHF ").append(extras.get(0).getPrice()).append("\n");
            }
        }

        receipt.append("Total: CHF ").append(total).append("\n");

        // Clear the cart after generating the receipt
        clearCart();

        return receipt.toString();
    }

    // Method to check if a product is a beverage
    private boolean isBeverage(Product product) {
        return product == Product.COFFEE_SMALL || product == Product.COFFEE_MEDIUM
                || product == Product.COFFEE_LARGE || product == Product.ORANGE_JUICE;
    }
    private boolean isBeverage(List<Product> products) {
        for (Product product : products) {
            if (isBeverage(product)) {
                return true;
            }
        }
        return false;
    }

    // Method to check if there is a snack (non-beverage) in the cart
    private boolean hasSnackInCart() {
        for (Product product : products) {
            if (product == Product.BACON_ROLL) {
                return true;
            }
        }
        return false;
    }

    // Method to clear the cart (reset the list)
    public void clearCart() {
        products.clear();
        extras.clear();
        beverageCount = 0; // Reset the beverage count
        beverageAndSnackOrder = false; // Reset the beverage and snack order check
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);  // Return a copy to avoid modification
    }

    public List<Extra> getExtras() {
        return new ArrayList<>(extras);  // Return a copy to avoid modification
    }
}
