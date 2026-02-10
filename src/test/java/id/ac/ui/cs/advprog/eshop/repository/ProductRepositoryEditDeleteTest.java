package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryEditDeleteTest {

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    void deleteProduct_positiveScenario_productRemoved() {
        Product p = new Product();
        p.setProductId("id-1");
        p.setProductName("Indomie");
        p.setProductQuantity(10);
        productRepository.create(p);

        boolean deleted = productRepository.deleteById("id-1");
        assertTrue(deleted);

        assertNull(productRepository.findById("id-1"));
    }

    @Test
    void deleteProduct_negativeScenario_idNotFound_shouldFailGracefully() {
        boolean deleted = productRepository.deleteById("not-exist");
        assertFalse(deleted);
    }

    @Test
    void editProduct_positiveScenario_productUpdated() {
        Product p = new Product();
        p.setProductId("id-2");
        p.setProductName("Teh Botol");
        p.setProductQuantity(5);
        productRepository.create(p);

        Product updated = new Product();
        updated.setProductId("id-2");
        updated.setProductName("Teh Botol Sosro");
        updated.setProductQuantity(99);

        Product result = productRepository.update("id-2", updated);
        assertNotNull(result);
        assertEquals("Teh Botol Sosro", result.getProductName());
        assertEquals(99, result.getProductQuantity());

        Product saved = productRepository.findById("id-2");
        assertNotNull(saved);
        assertEquals("Teh Botol Sosro", saved.getProductName());
        assertEquals(99, saved.getProductQuantity());
    }

    @Test
    void editProduct_negativeScenario_idNotFound_shouldFailGracefully() {
        Product updated = new Product();
        updated.setProductId("not-exist");
        updated.setProductName("Anything");
        updated.setProductQuantity(1);

        Product result = productRepository.update("not-exist", updated);
        assertNull(result);
    }

    @Test
    void findAll_afterDelete_shouldNotContainDeletedProduct() {
        Product p1 = new Product();
        p1.setProductId("id-a");
        p1.setProductName("A");
        p1.setProductQuantity(1);

        Product p2 = new Product();
        p2.setProductId("id-b");
        p2.setProductName("B");
        p2.setProductQuantity(2);

        productRepository.create(p1);
        productRepository.create(p2);

        productRepository.deleteById("id-a");

        Iterator<Product> it = productRepository.findAll();
        while (it.hasNext()) {
            Product p = it.next();
            assertNotEquals("id-a", p.getProductId());
        }
    }
}
