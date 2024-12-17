package org.example.coffeeservice;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> products = new ArrayList<>();
    private List<Extra> extras = new ArrayList<>();
    private boolean beverageAndSnackOrder = false;

    public void addProduct(Product product) {
        products.add(product);
        if (isSnack(product)) {
            if(beveragesInCart(products)){
            beverageAndSnackOrder = true;
            }
        }
    }

    public void addExtra(Extra extra) {
        extras.add(extra);
    }

    public String generateReceipt() {
        double total = 0;
        StringBuilder receipt = new StringBuilder("Receipt:\n");

        int beverageIndex = 0;

        for (Product product : products) {
            if (isBeverage(product)) {
                beverageIndex++;
                if (beverageIndex % 5 == 0) {
                    receipt.append(product.getName()).append(": CHF 0.0 (5th Beverage Free)\n");
                    continue;
                }
            }
            receipt.append(product.getName()).append(": CHF ").append(product.getPrice()).append("\n");
            total += product.getPrice();
        }

        for (Extra extra : extras) {
            receipt.append(extra.getName()).append(": CHF ").append(extra.getPrice()).append("\n");
            total += extra.getPrice();
        }

        if (beverageAndSnackOrder) {
            if (!extras.isEmpty()) {
                total -= extras.get(0).getPrice();
                receipt.append("Discount applied: Free Extra - ").append(extras.get(0).getName())
                        .append(": CHF ").append(extras.get(0).getPrice()).append("\n");
            }
        }

        receipt.append("Total: CHF ").append(total).append("\n");

        clearCart();

        return receipt.toString();
    }

    private boolean isBeverage(Product product) {
        return product == Product.COFFEE_SMALL || product == Product.COFFEE_MEDIUM
                || product == Product.COFFEE_LARGE || product == Product.ORANGE_JUICE;
    }
    private boolean beveragesInCart(List<Product> products) {
        for (Product product : products) {
            if (isBeverage(product)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSnack(Product product) {
                return product == Product.BACON_ROLL;

    }

    public void clearCart() {
        products.clear();
        extras.clear();
        beverageAndSnackOrder = false;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public List<Extra> getExtras() {
        return new ArrayList<>(extras);
    }
}
