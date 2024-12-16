package org.example.coffeeservice;

public enum Product {
    COFFEE_SMALL(2.50),
    COFFEE_MEDIUM(3.00),
    COFFEE_LARGE(3.50),
    BACON_ROLL(4.50),
    ORANGE_JUICE(3.95);

    private final double price;

    Product(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name();
    }
}
