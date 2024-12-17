package org.example.coffeeservice;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<Product> products = new ArrayList<>();
    private final List<Extra> extras = new ArrayList<>();
    private boolean beverageAndSnackOrder = false;

    public void addProduct(Product product) {
        products.add(product);
        if (isSnack(product) && hasBeverageInCart()) {
            beverageAndSnackOrder = true;
        }
    }

    public void addExtra(Extra extra) {
        extras.add(extra);
    }

    public String generateReceipt() {
        double total = 0;
        StringBuilder receipt = new StringBuilder("Receipt:\n");

        total += generateProductReceipt(receipt);
        total += generateExtrasReceipt(receipt);
        total = applyBeverageAndSnackDiscount(receipt, total);

        receipt.append("Total: CHF ").append(String.format("%.2f", total)).append("\n");

        clearCart();
        return receipt.toString();
    }

    private double generateProductReceipt(StringBuilder receipt) {
        double total = 0;
        int beverageIndex = 0;

        for (Product product : products) {
            if (isBeverage(product)) {
                beverageIndex++;
                if (beverageIndex % 5 == 0) {
                    receipt.append(product.getName())
                            .append(": CHF 0.0 (5th Beverage Free)\n");
                    continue;
                }
            }
            receipt.append(product.getName())
                    .append(": CHF ")
                    .append(String.format("%.2f", product.getPrice()))
                    .append("\n");
            total += product.getPrice();
        }
        return total;
    }

    private double generateExtrasReceipt(StringBuilder receipt) {
        double total = 0;
        for (Extra extra : extras) {
            receipt.append(extra.getName())
                    .append(": CHF ")
                    .append(String.format("%.2f", extra.getPrice()))
                    .append("\n");
            total += extra.getPrice();
        }
        return total;
    }

    private double applyBeverageAndSnackDiscount(StringBuilder receipt, double total) {
        if (beverageAndSnackOrder && !extras.isEmpty()) {
            Extra freeExtra = extras.get(0);
            total -= freeExtra.getPrice();
            receipt.append("Discount applied: Free Extra - ")
                    .append(freeExtra.getName())
                    .append(": CHF ")
                    .append(String.format("%.2f", freeExtra.getPrice()))
                    .append("\n");
        }
        return total;
    }

    private boolean isBeverage(Product product) {
        return product == Product.COFFEE_SMALL || product == Product.COFFEE_MEDIUM
                || product == Product.COFFEE_LARGE || product == Product.ORANGE_JUICE;
    }

    private boolean isSnack(Product product) {
        return product == Product.BACON_ROLL;
    }

    private boolean hasBeverageInCart() {
        return products.stream().anyMatch(this::isBeverage);
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