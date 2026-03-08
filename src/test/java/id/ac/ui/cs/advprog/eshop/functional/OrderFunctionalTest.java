package id.ac.ui.cs.advprog.eshop.functional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderFunctionalTest {

    @Autowired
    MockMvc mvc;

    @BeforeEach
    void setUp() {
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
}