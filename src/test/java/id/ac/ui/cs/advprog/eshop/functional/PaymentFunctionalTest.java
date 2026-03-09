package id.ac.ui.cs.advprog.eshop.functional;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentFunctionalTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    PaymentService paymentService;

    private Payment payment;
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

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        payment = new Payment(order, "VOUCHER_CODE", paymentData);

        when(paymentService.getPayment(payment.getId())).thenReturn(payment);
        when(paymentService.getAllPayments()).thenReturn(List.of(payment));
        when(paymentService.setStatus(payment, "SUCCESS")).thenReturn(payment);
    }

    @Test
    void testGetPaymentDetailForm() throws Exception {
        mvc.perform(get("/payment/detail"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Payment Detail")));
    }

    @Test
    void testGetPaymentDetailById() throws Exception {
        mvc.perform(get("/payment/detail/" + payment.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(payment.getId())));
    }

    @Test
    void testGetPaymentAdminList() throws Exception {
        mvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Payment Admin List")));
    }

    @Test
    void testGetPaymentAdminDetail() throws Exception {
        mvc.perform(get("/payment/admin/detail/" + payment.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(payment.getId())));
    }

    @Test
    void testPostSetStatus() throws Exception {
        mvc.perform(post("/payment/admin/set-status/" + payment.getId())
                        .param("status", "SUCCESS"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("SUCCESS")));
    }
}