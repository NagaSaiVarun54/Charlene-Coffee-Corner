package org.example.coffeeservice;
public enum Extra {
    EXTRA_MILK(0.30),
    FOAMED_MILK(0.50),
    SPECIAL_ROAST(0.90);

    private final double price;

    Extra(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name(); // This will return the enum name (e.g., "EXTRA_MILK")
    }
}
