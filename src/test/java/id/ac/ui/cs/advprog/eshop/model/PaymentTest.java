package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

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
    void testCreateVoucherPaymentSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);

        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreateVoucherPaymentRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALID");

        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateBankTransferSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123");

        Payment payment = new Payment(order, "BANK_TRANSFER", paymentData);

        assertEquals("BANK_TRANSFER", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreateBankTransferRejectedIfBankNameEmpty() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "REF123");

        Payment payment = new Payment(order, "BANK_TRANSFER", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateBankTransferRejectedIfReferenceCodeNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", null);

        Payment payment = new Payment(order, "BANK_TRANSFER", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);
        payment.setStatus("REJECTED");

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusInvalid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);

        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("MEOW"));
    }
    @Test
    void testCreateVoucherPaymentRejectedIfVoucherNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", null);

        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateVoucherPaymentRejectedIfWrongLength() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123");

        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateVoucherPaymentRejectedIfWrongPrefix() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "TOKO1234ABC5678");

        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateVoucherPaymentRejectedIfDigitCountNotEight() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12ABCD345EF");

        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherRejectedIfNull() {
        Map<String,String> data = new HashMap<>();
        data.put("voucherCode", null);

        Payment payment = new Payment(order,"VOUCHER_CODE",data);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherRejectedIfWrongLength() {
        Map<String,String> data = new HashMap<>();
        data.put("voucherCode","ESHOP123");

        Payment payment = new Payment(order,"VOUCHER_CODE",data);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherRejectedIfWrongPrefix() {
        Map<String,String> data = new HashMap<>();
        data.put("voucherCode","TOKO1234ABC5678");

        Payment payment = new Payment(order,"VOUCHER_CODE",data);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentWithUnsupportedMethodThrowsException() {
        Map<String, String> paymentData = new HashMap<>();

        assertThrows(IllegalArgumentException.class, () -> {
            new Payment(order, "CASH", paymentData);
        });
    }
    @Test
    void testCreateBankTransferRejectedIfBankNameNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", null);
        paymentData.put("referenceCode", "REF123");

        Payment payment = new Payment(order, "BANK_TRANSFER", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateBankTransferRejectedIfReferenceCodeEmpty() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "");

        Payment payment = new Payment(order, "BANK_TRANSFER", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }
    @Test
    void testVoucherRejectedIfDigitCountNotEight() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOPABCD1234567");

        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }
    @Test
    void testVoucherRejectedIfWrongPrefixButLengthIsCorrect() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ABCDE1234ABC5678"); // 16 chars, not ESHOP

        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherRejectedIfDigitCountIsNotEight() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOPABCD1234567"); // 16 chars, starts ESHOP, only 7 digits

        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }
}