package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }
    @Test
    void testCreateProductWithoutIdGeneratesId() {
        Product product = new Product();
        product.setProductName("Soap");
        product.setProductQuantity(10);

        Product result = productRepository.create(product);

        assertNotNull(result.getProductId());
        assertFalse(result.getProductId().isBlank());
    }
    @Test
    void testCreateProductWithBlankIdGeneratesId() {
        Product product = new Product();
        product.setProductId(" ");
        product.setProductName("Soap");
        product.setProductQuantity(10);

        Product result = productRepository.create(product);

        assertNotNull(result.getProductId());
        assertFalse(result.getProductId().isBlank());
    }
    @Test
    void testUpdateProductNotFoundReturnsNull() {
        Product updated = new Product();
        updated.setProductName("Updated");
        updated.setProductQuantity(99);

        Product result = productRepository.update("missing", updated);

        assertNull(result);
    }
    @Test
    void testUpdateProductWithNullReturnsNull() {
        Product result = productRepository.update("some-id", null);
        assertNull(result);
    }
    @Test
    void testFindByIdSuccess() {
        Product product = new Product();
        product.setProductId("id-123");
        product.setProductName("Soap");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product result = productRepository.findById("id-123");

        assertNotNull(result);
        assertEquals("id-123", result.getProductId());
    }
    @Test
    @SuppressWarnings("unchecked")
    void findById_shouldSkipStoredProductWhenProductIdIsNull() throws Exception {
        ProductRepository repo = new ProductRepository();

        java.lang.reflect.Field field = ProductRepository.class.getDeclaredField("productData");
        field.setAccessible(true);
        java.util.List<Product> productData = (java.util.List<Product>) field.get(repo);

        Product p = new Product();
        p.setProductId(null);
        p.setProductName("A");
        p.setProductQuantity(1);

        productData.add(p);

        assertNull(repo.findById("anything"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void deleteById_shouldReturnFalse_whenStoredProductIdIsNull() throws Exception {
        ProductRepository repo = new ProductRepository();

        java.lang.reflect.Field field = ProductRepository.class.getDeclaredField("productData");
        field.setAccessible(true);
        java.util.List<Product> productData = (java.util.List<Product>) field.get(repo);

        Product p = new Product();
        p.setProductId(null);
        p.setProductName("A");
        p.setProductQuantity(1);

        productData.add(p);

        assertFalse(repo.deleteById("anything"));
    }
    @Test
    void testFindByIdFindsMatchingProductAfterSkippingNonMatchingProduct() {
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Soap");
        product1.setProductQuantity(10);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Shampoo");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        Product result = productRepository.findById("id-2");

        assertNotNull(result);
        assertEquals("id-2", result.getProductId());
        assertEquals("Shampoo", result.getProductName());
    }

}