package org.example.coffeeservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest {

    private Cart cart;

    @BeforeEach
    public void setUp() {
        cart = new Cart();
    }
    @Test
    public void testAddProduct() {
        cart.addProduct(Product.COFFEE_SMALL);
        cart.addProduct(Product.BACON_ROLL);

        assertEquals(2, cart.getProducts().size(), "Cart should contain 2 products");
    }

    @Test
    public void testAddExtra() {
        cart.addExtra(Extra.EXTRA_MILK);
        assertEquals(1, cart.getExtras().size(), "Cart should contain 1 extra");
    }

    @Test
    public void testGenerateReceiptWithOutDiscountSinceNoExtra() {
        cart.addProduct(Product.COFFEE_SMALL);
        cart.addProduct(Product.BACON_ROLL);

        String receipt = cart.generateReceipt();
        System.out.println(receipt);
        // Check for expected products and total price
        assertTrue(receipt.contains("COFFEE_SMALL: CHF 2.5"));
        assertTrue(receipt.contains("BACON_ROLL: CHF 4.5"));
        assertTrue(receipt.contains("Total: CHF 7.0"));
    }

    @Test
    public void testGenerateReceiptWithFreeBeverageDiscount() {
        // Add 5 beverages (including non-beverages)
        cart.addProduct(Product.COFFEE_SMALL);
        cart.addProduct(Product.COFFEE_MEDIUM);
        cart.addProduct(Product.COFFEE_LARGE);
        cart.addProduct(Product.ORANGE_JUICE);
        cart.addProduct(Product.COFFEE_SMALL);

        String receipt = cart.generateReceipt();
        System.out.println(receipt);

        assertTrue(receipt.contains("COFFEE_SMALL: CHF 0.0 (5th Beverage Free)"));
        assertTrue(receipt.contains("Total: CHF 12.95"));
    }


    @Test
    public void testGenerateReceiptWithFreeBeverageAndExtra() {
        // Add 5 beverages (including non-beverages)
        cart.addProduct(Product.COFFEE_SMALL);
        cart.addProduct(Product.COFFEE_MEDIUM);
        cart.addProduct(Product.COFFEE_LARGE);
        cart.addProduct(Product.ORANGE_JUICE);
        cart.addProduct(Product.COFFEE_SMALL);

        // Add snack and extra
        cart.addProduct(Product.BACON_ROLL);
        cart.addExtra(Extra.FOAMED_MILK);

        String receipt = cart.generateReceipt();
        System.out.println(receipt);
        // Check the receipt for the 5th beverage being free
        assertTrue(receipt.contains("COFFEE_SMALL: CHF 0.0 (5th Beverage Free)"));
        assertTrue(receipt.contains("BACON_ROLL: CHF 4.5"));
        assertTrue(receipt.contains("Discount applied: Free Extra - FOAMED_MILK: CHF 0.5"));
        assertTrue(receipt.contains("FOAMED_MILK: CHF 0.5"));
        assertTrue(receipt.contains("Total: CHF 17.45"));
    }


    @Test
    public void testGenerateReceiptWithFreeExtraDiscount() {
        // Add a beverage and snack
        cart.addProduct(Product.COFFEE_SMALL);
        cart.addProduct(Product.BACON_ROLL);
        cart.addExtra(Extra.EXTRA_MILK);

        String receipt = cart.generateReceipt();

        // Check the receipt for free extra discount
        assertTrue(receipt.contains("Discount applied: Free Extra - EXTRA_MILK: CHF 0.3"));
        assertTrue(receipt.contains("Total: CHF 7.0"));
    }

    @Test
    public void testCartClear() {
        // Add products and extras
        cart.addProduct(Product.COFFEE_SMALL);
        cart.addProduct(Product.BACON_ROLL);
        cart.addExtra(Extra.EXTRA_MILK);

        // Clear the cart
        cart.clearCart();

        // After clearing, the cart should be empty
        assertTrue(cart.getProducts().isEmpty(), "Products should be empty after clear");
        assertTrue(cart.getExtras().isEmpty(), "Extras should be empty after clear");
    }

    @Test
    public void testAddProductAndExtra() {
        // Add product and extra, then generate the receipt
        cart.addProduct(Product.COFFEE_SMALL);
        cart.addExtra(Extra.SPECIAL_ROAST);

        String receipt = cart.generateReceipt();

        assertTrue(receipt.contains("COFFEE_SMALL: CHF 2.5"));
        assertTrue(receipt.contains("SPECIAL_ROAST: CHF 0.9"));
        assertTrue(receipt.contains("Total: CHF 3.4"));
    }

    @Test
    public void testInvalidInput() {
        // Test for edge cases with an empty cart (nothing added)
        String receipt = cart.generateReceipt();
        assertTrue(receipt.contains("Total: CHF 0.0"), "Total price should be 0 when nothing is added");
    }

    @Test
    public void testBeveageAndSnackTogether() {
        // Test when both beverage and snack are added
        cart.addProduct(Product.COFFEE_SMALL);
        cart.addProduct(Product.BACON_ROLL);
        cart.addExtra(Extra.FOAMED_MILK);

        String receipt = cart.generateReceipt();

        // Check if the discount for free extra is applied
        assertTrue(receipt.contains("Discount applied: Free Extra - FOAMED_MILK: CHF 0.5"));
    }

}
