package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class EshopApplicationMainTest {

    @Test
    void main_shouldStartAndCloseContext() {
        ConfigurableApplicationContext ctx = SpringApplication.run(
                EshopApplication.class,
                "--spring.main.web-application-type=none"
        );
        assertNotNull(ctx);
        ctx.close();
    }
}