package org.example.coffeeservice;

import java.util.Scanner;

public class CoffeeShopApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cart cart = new Cart();

        while (true) {
            System.out.println("\nPlease select products (e.g., '1' for small coffee, '2' for bacon roll, '1, 2' for both small coffee and bacon roll): ");
            System.out.println("1. Small Coffee - CHF 2.50");
            System.out.println("2. Bacon Roll - CHF 4.50");
            System.out.println("3. Orange Juice - CHF 3.95");
            System.out.println("4. Large Coffee - CHF 3.50");
            System.out.println("5. Extra Milk - CHF 0.30");
            System.out.println("6. Foamed Milk - CHF 0.50");
            System.out.println("7. Special Roast - CHF 0.90");
            System.out.println("8. Finish and Generate Receipt");

            String input = scanner.nextLine().trim();

            if (input.equals("8")) {
                System.out.println(cart.generateReceipt());
                cart.clearCart();
                System.out.println("Cart has been cleared. Thank you for your order!\n");
                continue;
            }

            String[] selections = input.split(",");

            boolean validInput = true;
            for (String selection : selections) {
                selection = selection.trim();
                switch (selection) {
                    case "1":
                        cart.addProduct(Product.COFFEE_SMALL);
                        break;
                    case "2":
                        cart.addProduct(Product.BACON_ROLL);
                        break;
                    case "3":
                        cart.addProduct(Product.ORANGE_JUICE);
                        break;
                    case "4":
                        cart.addProduct(Product.COFFEE_LARGE);
                        break;
                    case "5":
                        cart.addExtra(Extra.EXTRA_MILK);
                        break;
                    case "6":
                        cart.addExtra(Extra.FOAMED_MILK);
                        break;
                    case "7":
                        cart.addExtra(Extra.SPECIAL_ROAST);
                        break;
                    default:
                        System.out.println("Invalid selection: " + selection);
                        validInput = false;
                        break;
                }
            }
            if (validInput) {
                System.out.println(cart.generateReceipt());
            } else {
                System.out.println("Please try again with valid options.");
            }
        }
    }
}
