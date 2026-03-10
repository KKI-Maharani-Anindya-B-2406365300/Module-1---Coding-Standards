package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private List<Product> products;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Sampo");
        product.setProductQuantity(1);
        products.add(product);
    }

    @Test
    void testCreateOrderDefaultStatus() {
        Order order = new Order("order-1", products, 1708560000L, "Maharani");

        assertEquals("order-1", order.getId());
        assertEquals("Maharani", order.getAuthor());
        assertEquals("WAITING_PAYMENT", order.getStatus());
    }

    @Test
    void testCreateOrderWithExplicitStatus() {
        Order order = new Order("order-1", products, 1708560000L, "Maharani", "SUCCESS");

        assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    void testCreateOrderWithEmptyProductsThrowsException() {
        List<Product> emptyProducts = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> {
            new Order("order-1", emptyProducts, 1708560000L, "Maharani");
        });
    }

    @Test
    void testSetStatusValid() {
        Order order = new Order("order-1", products, 1708560000L, "Maharani");

        order.setStatus("FAILED");

        assertEquals("FAILED", order.getStatus());
    }

    @Test
    void testSetStatusInvalidThrowsException() {
        Order order = new Order("order-1", products, 1708560000L, "Maharani");

        assertThrows(IllegalArgumentException.class, () -> {
            order.setStatus("MEOW");
        });
    }

}