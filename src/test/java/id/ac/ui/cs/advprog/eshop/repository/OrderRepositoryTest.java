package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderRepositoryTest {

    private OrderRepository orderRepository;
    private Order order;

    @BeforeEach
    void setUp() {
        orderRepository = new OrderRepository();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Sampo");
        product.setProductQuantity(1);
        products.add(product);

        order = new Order("order-1", products, 1708560000L, "Maharani");
    }

    @Test
    void testSaveNewOrder() {
        Order result = orderRepository.save(order);

        assertNotNull(result);
        assertEquals("order-1", result.getId());
    }

    @Test
    void testSaveExistingOrderUpdatesData() {
        orderRepository.save(order);

        Order updatedOrder = new Order(
                "order-1",
                order.getProducts(),
                order.getOrderTime(),
                order.getAuthor(),
                "SUCCESS"
        );

        Order result = orderRepository.save(updatedOrder);

        assertEquals("SUCCESS", result.getStatus());
        assertEquals("SUCCESS", orderRepository.findById("order-1").getStatus());
        assertEquals(1, orderRepository.findAllByAuthor("Maharani").size());
    }

    @Test
    void testFindByIdFound() {
        orderRepository.save(order);

        Order result = orderRepository.findById("order-1");

        assertNotNull(result);
        assertEquals("order-1", result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        Order result = orderRepository.findById("missing");

        assertNull(result);
    }

    @Test
    void testFindAllByAuthorFound() {
        orderRepository.save(order);

        List<Order> result = orderRepository.findAllByAuthor("Maharani");

        assertEquals(1, result.size());
        assertEquals("Maharani", result.get(0).getAuthor());
    }

    @Test
    void testSaveUpdatesExistingOrder() {
        orderRepository.save(order);

        Order updated = new Order(
                order.getId(),
                order.getProducts(),
                order.getOrderTime(),
                order.getAuthor(),
                "SUCCESS"
        );

        orderRepository.save(updated);

        Order result = orderRepository.findById(order.getId());

        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testFindAllByAuthorMultipleOrders() {
        orderRepository.save(order);

        Order order2 = new Order(
                "order-2",
                order.getProducts(),
                order.getOrderTime(),
                "Maharani"
        );

        orderRepository.save(order2);

        List<Order> result = orderRepository.findAllByAuthor("Maharani");

        assertEquals(2, result.size());
    }

    @Test
    void testFindAllByAuthorEmpty() {
        List<Order> result = orderRepository.findAllByAuthor("Nobody");

        assertTrue(result.isEmpty());
    }
    @Test
    void testSaveUpdatesExistingOrderNotInFirstPosition() {
        Order firstOrder = new Order("order-1", order.getProducts(), order.getOrderTime(), "Maharani");
        Order secondOrder = new Order("order-2", order.getProducts(), order.getOrderTime(), "Maharani");

        orderRepository.save(firstOrder);
        orderRepository.save(secondOrder);

        Order updatedSecond = new Order("order-2", order.getProducts(), order.getOrderTime(), "Maharani", "SUCCESS");
        orderRepository.save(updatedSecond);

        Order result = orderRepository.findById("order-2");

        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
    }
    @Test
    void testFindAllByAuthorIgnoresDifferentAuthor() {
        Order order1 = new Order("order-1", order.getProducts(), order.getOrderTime(), "Maharani");
        Order order2 = new Order("order-2", order.getProducts(), order.getOrderTime(), "Budi");

        orderRepository.save(order1);
        orderRepository.save(order2);

        List<Order> result = orderRepository.findAllByAuthor("Maharani");

        assertEquals(1, result.size());
        assertEquals("order-1", result.get(0).getId());
    }

}