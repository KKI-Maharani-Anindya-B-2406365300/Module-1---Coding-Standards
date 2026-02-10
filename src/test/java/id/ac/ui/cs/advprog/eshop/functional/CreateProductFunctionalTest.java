package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int port;

    @Value("${app.baseUrl:http://localhost}")
    private String baseUrl;

    private String url;

    @BeforeEach
    void setUp() {
        url = baseUrl + ":" + port;
    }

    @Test
    void createProduct_userCanSeeNewProductInList(ChromeDriver driver) {
        String productName = "Sampo Cap Bambang";
        String productQuantity = "25";

        driver.get(url + "/product/list");

        driver.findElement(By.id("createProductBtn")).click();

        driver.findElement(By.id("nameInput")).sendKeys(productName);
        driver.findElement(By.id("quantityInput")).sendKeys(productQuantity);

        driver.findElement(By.id("submitBtn")).click();

        driver.get(url + "/product/list");

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains(productName));
        assertTrue(pageSource.contains(productQuantity));

    }
}
