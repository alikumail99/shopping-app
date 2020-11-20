package edu.depaul.se433.shoppingapp;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

public class ShoppingUnitTests {
    ShoppingCart freeShippingCart = new ShoppingCart(); // A cart with a total cost of 1001
    ShoppingCart withShippingCart = new ShoppingCart(); // A cart with a total cost of 1
    ShoppingCart invalidCart = new ShoppingCart();        // A cart with a value of -10
    PurchaseItem paper = new PurchaseItem("Paper", 10.0, 100);
    PurchaseItem ink = new PurchaseItem("ink", 1.0, 1);
    PurchaseItem negative = new PurchaseItem("Negative", -10.0, 1);

    @BeforeEach
    void setup() {
        freeShippingCart.addItem(ink);
        freeShippingCart.addItem(paper);

        withShippingCart.addItem(ink);
    }

    @Test
    @DisplayName("Total with sales tax and no shipping(set to standard")
    void freeShippingWithTax() {
        TotalCostCalculator total = new TotalCostCalculator();
        Bill bill = total.calculate(freeShippingCart, "IL", ShippingType.STANDARD);
        assertEquals(bill.total(), 1061.06);
    }

    @Test
    @DisplayName("Total without sales tax and no shipping (set to Standard)")
    void freeShippingNoTax() {
        TotalCostCalculator total = new TotalCostCalculator();
        Bill bill = total.calculate(freeShippingCart, "WA", ShippingType.STANDARD);
        assertEquals(bill.total(), 1001);
    }

    @Test
    @DisplayName("Total with sales tax and no shipping (set to nextDay)")
    void freeNextDayShippingWithTax() {
        TotalCostCalculator total = new TotalCostCalculator();
        Bill bill = total.calculate(freeShippingCart, "IL", ShippingType.NEXT_DAY);
        assertEquals(bill.total(), 1061.06);
    }

    @Test
    @DisplayName("Total without sales tax and no shipping (set to nextDay")
    void freeNextDayShippingNoTax() {
        TotalCostCalculator total = new TotalCostCalculator();
        Bill bill = total.calculate(freeShippingCart, "WA", ShippingType.NEXT_DAY);
        assertEquals(bill.total(), 1001);
    }

    @Test
    @DisplayName("Total without sales tax and shipping")
    void chargeShippingNoTax() {
        TotalCostCalculator total = new TotalCostCalculator();
        Bill bill = total.calculate(withShippingCart, "WA", ShippingType.STANDARD);
        assertEquals(bill.total(), 11.0);
    }

    @Test
    @DisplayName("Total with sales tax and shipping")
    void chargeShippingWithTax() {
        TotalCostCalculator total = new TotalCostCalculator();
        Bill bill = total.calculate(withShippingCart, "IL", ShippingType.STANDARD);
        assertEquals(bill.total(), 11.06);
    }

    @Test
    @DisplayName("Total without sales tax and shipping (set to NextDay)")
    void chargeNextDayShippingNoTax() {
        TotalCostCalculator total = new TotalCostCalculator();
        Bill bill = total.calculate(withShippingCart, "WA", ShippingType.NEXT_DAY);
        assertEquals(bill.total(), 26.0);
    }

    @Test
    @DisplayName("Total with sales tax and shipping (set to nextDay")
    void chargeNextDayShippingWithTax() {
        TotalCostCalculator total = new TotalCostCalculator();
        Bill bill = total.calculate(withShippingCart, "IL", ShippingType.NEXT_DAY);
        assertEquals(bill.total(), 26.06);
    }

    @Test
    @DisplayName("Invalid total of cart should return 1.1 for error")
    void invalidCartTotal() {
        try {
            TotalCostCalculator total = new TotalCostCalculator();
            Bill bill = total.calculate(invalidCart, "IL", ShippingType.STANDARD);
            assertEquals(1.1, bill.total());
        } catch (Exception e) {
            TotalCostCalculator total = new TotalCostCalculator();
            Bill bill = total.calculate(invalidCart, "IL", ShippingType.STANDARD);
            double error = 1.1;
            assertEquals(error, bill.total());
        }
    }
    @Test
    @DisplayName("Invalid State should return 1.1 for error")
    void invalidStateTotal() {
        try {
            TotalCostCalculator total = new TotalCostCalculator();
            Bill bill = total.calculate(withShippingCart, "UAE", ShippingType.STANDARD);
            assertEquals(1.1, bill.total());
        } catch (Exception e) {
            TotalCostCalculator total = new TotalCostCalculator();
            Bill bill = total.calculate(invalidCart, "UAE", ShippingType.STANDARD);
            double error = 1.1;
            assertEquals(error, bill.total());
        }
    }
}
