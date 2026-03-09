package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    private Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Sampo");
        product.setProductQuantity(1);
        products.add(product);

        order = new Order("order-1", products, 1708560000L, "Maharani");
    }

    @Test
    void testCreateOrderSuccess() {
        when(orderRepository.findById(order.getId())).thenReturn(null);
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.createOrder(order);

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        verify(orderRepository).findById(order.getId());
        verify(orderRepository).save(order);
    }

    @Test
    void testCreateOrderDuplicateReturnsNull() {
        when(orderRepository.findById(order.getId())).thenReturn(order);

        Order result = orderService.createOrder(order);

        assertNull(result);
        verify(orderRepository).findById(order.getId());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void testUpdateStatusSuccess() {
        when(orderRepository.findById("order-1")).thenReturn(order);

        Order result = orderService.updateStatus("order-1", "SUCCESS");

        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
        assertEquals(order.getId(), result.getId());
        verify(orderRepository).findById("order-1");
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void testUpdateStatusOrderNotFound() {
        when(orderRepository.findById("not-found")).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> {
            orderService.updateStatus("not-found", "SUCCESS");
        });

        verify(orderRepository).findById("not-found");
        verify(orderRepository, never()).save(any());
    }

    @Test
    void testFindByIdFound() {
        when(orderRepository.findById("order-1")).thenReturn(order);

        Order result = orderService.findById("order-1");

        assertNotNull(result);
        assertEquals("order-1", result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(orderRepository.findById("missing")).thenReturn(null);

        Order result = orderService.findById("missing");

        assertNull(result);
    }

    @Test
    void testFindAllByAuthorFound() {
        List<Order> orders = List.of(order);
        when(orderRepository.findAllByAuthor("Maharani")).thenReturn(orders);

        List<Order> result = orderService.findAllByAuthor("Maharani");

        assertEquals(1, result.size());
        assertEquals("Maharani", result.get(0).getAuthor());
    }

    @Test
    void testFindAllByAuthorEmpty() {
        when(orderRepository.findAllByAuthor("Nobody")).thenReturn(List.of());

        List<Order> result = orderService.findAllByAuthor("Nobody");

        assertTrue(result.isEmpty());
    }
}