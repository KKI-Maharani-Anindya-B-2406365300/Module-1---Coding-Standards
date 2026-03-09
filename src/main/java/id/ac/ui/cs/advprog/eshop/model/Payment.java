package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;
    private Order order;

    public Payment(Order order, String method, Map<String, String> paymentData) {
        this.id = UUID.randomUUID().toString();
        this.order = order;
        this.method = method;
        this.paymentData = paymentData;

        if ("VOUCHER_CODE".equals(method)) {
            this.status = validateVoucherCode(paymentData.get("voucherCode"));
        } else if ("BANK_TRANSFER".equals(method)) {
            this.status = validateBankTransfer(
                    paymentData.get("bankName"),
                    paymentData.get("referenceCode")
            );
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setStatus(String status) {
        if ("SUCCESS".equals(status) || "REJECTED".equals(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private String validateVoucherCode(String voucherCode) {
        if (isValidVoucherCode(voucherCode)) {
            return "SUCCESS";
        }
        return "REJECTED";
    }

    private String validateBankTransfer(String bankName, String referenceCode) {
        if (bankName == null || bankName.isEmpty() ||
                referenceCode == null || referenceCode.isEmpty()) {
            return "REJECTED";
        }
        return "SUCCESS";
    }

    private boolean isValidVoucherCode(String voucherCode) {
        if (voucherCode == null) {
            return false;
        }

        if (voucherCode.length() != 16) {
            return false;
        }

        if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }

        int digitCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }

        return digitCount == 8;
    }
}