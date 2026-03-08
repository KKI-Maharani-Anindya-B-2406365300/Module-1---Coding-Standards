package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    private Order order;
    private Payment payment;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Sampo");
        product.setProductQuantity(1);
        products.add(product);

        order = new Order("order-1", products, 1708560000L, "Maharani");

        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        payment = new Payment(order, "VOUCHER_CODE", paymentData);
    }

    @Test
    void testAddPayment() {
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);

        assertEquals("VOUCHER_CODE", result.getMethod());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusSuccessUpdatesOrderStatus() {
        Payment result = paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", result.getStatus());
        assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    void testSetStatusRejectedUpdatesOrderStatus() {
        Payment result = paymentService.setStatus(payment, "REJECTED");

        assertEquals("REJECTED", result.getStatus());
        assertEquals("FAILED", order.getStatus());
    }

    @Test
    void testGetPayment() {
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());

        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);

        doReturn(payments).when(paymentRepository).findAll();

        List<Payment> results = paymentService.getAllPayments();

        assertEquals(1, results.size());
    }
}