package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int port;

    @Value("${app.baseUrl:http://localhost}")
    private String baseUrl;

    private String url;
    private WebDriver driver;

    @BeforeEach
    void setUp() {
        url = baseUrl + ":" + port;

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void createProduct_userCanSeeNewProductInList() {
        String productName = "Sampo Cap Bambang";
        String productQuantity = "25";

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.get(url + "/product/list");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("createProductBtn"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameInput"))).sendKeys(productName);
        driver.findElement(By.id("quantityInput")).sendKeys(productQuantity);

        driver.findElement(By.id("submitBtn")).click();

        wait.until(ExpectedConditions.urlContains("/product/list"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String bodyText = driver.findElement(By.tagName("body")).getText();

        assertTrue(bodyText.contains(productName));
        assertTrue(bodyText.contains(productQuantity));
    }
}