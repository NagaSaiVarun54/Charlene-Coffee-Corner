package org.example.coffeeservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        assertTrue(receipt.contains("COFFEE_SMALL: CHF 2.5"));
        assertTrue(receipt.contains("BACON_ROLL: CHF 4.5"));
        assertTrue(receipt.contains("Total: CHF 7.0"));
    }

    @Test
    public void testGenerateReceiptWithFreeBeverageDiscount() {
        cart.addProduct(Product.COFFEE_SMALL);
        cart.addProduct(Product.COFFEE_MEDIUM);
        cart.addProduct(Product.COFFEE_LARGE);
        cart.addProduct(Product.ORANGE_JUICE);
        cart.addProduct(Product.COFFEE_SMALL);

        String receipt = cart.generateReceipt();

        assertTrue(receipt.contains("COFFEE_SMALL: CHF 0.0 (5th Beverage Free)"));
        assertTrue(receipt.contains("Total: CHF 12.95"));
    }


    @Test
    public void testGenerateReceiptWithFreeBeverageAndExtra() {
        cart.addProduct(Product.COFFEE_SMALL);
        cart.addProduct(Product.COFFEE_MEDIUM);
        cart.addProduct(Product.COFFEE_LARGE);
        cart.addProduct(Product.ORANGE_JUICE);
        cart.addProduct(Product.COFFEE_SMALL);

        cart.addProduct(Product.BACON_ROLL);
        cart.addExtra(Extra.FOAMED_MILK);

        String receipt = cart.generateReceipt();

        assertTrue(receipt.contains("COFFEE_SMALL: CHF 0.0 (5th Beverage Free)"));
        assertTrue(receipt.contains("BACON_ROLL: CHF 4.5"));
        assertTrue(receipt.contains("Discount applied: Free Extra - FOAMED_MILK: CHF 0.5"));
        assertTrue(receipt.contains("FOAMED_MILK: CHF 0.5"));
        assertTrue(receipt.contains("Total: CHF 17.45"));
    }


    @Test
    public void testGenerateReceiptWithFreeExtraDiscount() {
        cart.addProduct(Product.COFFEE_SMALL);
        cart.addProduct(Product.BACON_ROLL);
        cart.addExtra(Extra.EXTRA_MILK);

        String receipt = cart.generateReceipt();

        assertTrue(receipt.contains("Discount applied: Free Extra - EXTRA_MILK: CHF 0.3"));
        assertTrue(receipt.contains("Total: CHF 7.0"));
    }

    @Test
    public void testCartClear() {
        cart.addProduct(Product.COFFEE_SMALL);
        cart.addProduct(Product.BACON_ROLL);
        cart.addExtra(Extra.EXTRA_MILK);

        cart.clearCart();

        assertTrue(cart.getProducts().isEmpty(), "Products should be empty after clear");
        assertTrue(cart.getExtras().isEmpty(), "Extras should be empty after clear");
    }

    @Test
    public void testAddProductAndExtra() {
        cart.addProduct(Product.COFFEE_SMALL);
        cart.addExtra(Extra.SPECIAL_ROAST);

        String receipt = cart.generateReceipt();

        assertTrue(receipt.contains("COFFEE_SMALL: CHF 2.5"));
        assertTrue(receipt.contains("SPECIAL_ROAST: CHF 0.9"));
        assertTrue(receipt.contains("Total: CHF 3.4"));
    }

    @Test
    public void testInvalidInput() {
        String receipt = cart.generateReceipt();
        assertTrue(receipt.contains("Total: CHF 0.0"), "Total price should be 0 when nothing is added");
    }

    @Test
    public void testBeveageAndSnackTogether() {
        cart.addProduct(Product.COFFEE_SMALL);
        cart.addProduct(Product.BACON_ROLL);
        cart.addExtra(Extra.FOAMED_MILK);

        String receipt = cart.generateReceipt();

        assertTrue(receipt.contains("Discount applied: Free Extra - FOAMED_MILK: CHF 0.5"));
    }

}
