package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/create")
    public String createOrderPage() {
        return "order-create";
    }

    @GetMapping("/history")
    public String orderHistoryForm() {
        return "order-history-form";
    }

    @PostMapping("/history")
    public String orderHistoryList(@RequestParam("author") String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        return "order-history-list";
    }

    @GetMapping("/pay/{orderId}")
    public String orderPayPage(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "order-pay-form";
    }

    @PostMapping("/pay/{orderId}")
    public String payOrder(@PathVariable String orderId,
                           @RequestParam("method") String method,
                           @RequestParam Map<String, String> requestParams,
                           Model model) {
        Order order = orderService.findById(orderId);

        Map<String, String> paymentData = new HashMap<>();

        if ("VOUCHER_CODE".equals(method)) {
            paymentData.put("voucherCode", requestParams.get("voucherCode"));
        } else if ("BANK_TRANSFER".equals(method)) {
            paymentData.put("bankName", requestParams.get("bankName"));
            paymentData.put("referenceCode", requestParams.get("referenceCode"));
        } else {
            throw new IllegalArgumentException("Unsupported payment method");
        }

        var payment = paymentService.addPayment(order, method, paymentData);
        model.addAttribute("paymentId", payment.getId());
        return "order-pay-result";
    }
}