package id.ac.ui.cs.advprog.eshop.functional;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderFunctionalTest {

    @MockitoBean
    OrderService orderService;

    @MockitoBean
    PaymentService paymentService;

    @Autowired
    MockMvc mvc;

    private Order order;
    private Payment payment;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Sampo");
        product.setProductQuantity(1);
        products.add(product);

        order = new Order("test-order-id", products, 1708560000L, "Maharani");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        payment = new Payment(order, "VOUCHER_CODE", paymentData);

        when(orderService.findById("test-order-id")).thenReturn(order);
        when(orderService.findAllByAuthor("Maharani")).thenReturn(List.of(order));
        when(paymentService.addPayment(order, "VOUCHER_CODE", paymentData)).thenReturn(payment);

        Map<String, String> bankTransferData = new HashMap<>();
        bankTransferData.put("bankName", "BCA");
        bankTransferData.put("referenceCode", "REF12345");
        when(paymentService.addPayment(order, "BANK_TRANSFER", bankTransferData)).thenReturn(payment);
    }

    @Test
    void testGetCreateOrderPage() throws Exception {
        mvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Create Order")));
    }

    @Test
    void testGetOrderHistoryPage() throws Exception {
        mvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Order History")));
    }

    @Test
    void testPostOrderHistoryPage() throws Exception {
        mvc.perform(post("/order/history")
                        .param("author", "Maharani"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Order History List")));
    }

    @Test
    void testGetOrderPayPage() throws Exception {
        mvc.perform(get("/order/pay/test-order-id"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pay Order")));
    }

    @Test
    void testPostPayOrderVoucher() throws Exception {
        mvc.perform(post("/order/pay/test-order-id")
                        .param("method", "VOUCHER_CODE")
                        .param("voucherCode", "ESHOP1234ABC5678"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Payment Result")));
    }

    @Test
    void testPostPayOrderBankTransfer() throws Exception {
        mvc.perform(post("/order/pay/test-order-id")
                        .param("method", "BANK_TRANSFER")
                        .param("bankName", "BCA")
                        .param("referenceCode", "REF12345"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Payment Result")));
    }
    @Test
    void testPostPayOrderWithInvalidMethod() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            mvc.perform(post("/order/pay/test-order-id")
                    .param("method", "INVALID_METHOD"));
        });

        Throwable cause = exception.getCause();
        Assertions.assertNotNull(cause);
        Assertions.assertInstanceOf(IllegalArgumentException.class, cause);
        Assertions.assertEquals("Unsupported payment method", cause.getMessage());
    }

}