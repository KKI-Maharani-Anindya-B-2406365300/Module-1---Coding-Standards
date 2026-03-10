package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;
    private Payment payment;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Sampo");
        product.setProductQuantity(1);
        products.add(product);

        Order order = new Order("order-1", products, 1708560000L, "Maharani");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        payment = new Payment(order, "VOUCHER_CODE", paymentData);
    }

    @Test
    void testSavePayment() {
        Payment result = paymentRepository.save(payment);

        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testFindByIdIfFound() {
        paymentRepository.save(payment);

        Payment result = paymentRepository.findById(payment.getId());

        assertNotNull(result);
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testFindByIdIfNotFound() {
        Payment result = paymentRepository.findById("not-found");

        assertNull(result);
    }

    @Test
    void testFindAll() {
        paymentRepository.save(payment);

        List<Payment> result = paymentRepository.findAll();

        assertEquals(1, result.size());
    }
    @Test
    void testFindAllEmpty() {
        List<Payment> result = paymentRepository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    @Test
    void testFindAllEmptyRepository() {
        List<Payment> result = paymentRepository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSaveMultiplePayments() {
        paymentRepository.save(payment);

        Payment payment2 = new Payment(payment.getOrder(), "VOUCHER_CODE", Map.of("voucherCode", "ESHOP1111AAAA2222"));

        paymentRepository.save(payment2);

        List<Payment> result = paymentRepository.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void testFindByIdWhenMultiplePaymentsExist() {
        paymentRepository.save(payment);

        Payment payment2 = new Payment(payment.getOrder(), "VOUCHER_CODE", Map.of("voucherCode", "ESHOP1111AAAA2222"));
        paymentRepository.save(payment2);

        Payment result = paymentRepository.findById(payment2.getId());

        assertEquals(payment2.getId(), result.getId());
    }

}