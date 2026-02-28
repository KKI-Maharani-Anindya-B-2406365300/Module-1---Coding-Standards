package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    private ProductServiceImpl service;
    private ProductRepository repo;

    @BeforeEach
    void setUp() {
        repo = new ProductRepository();
        service = new ProductServiceImpl(repo);
    }

    @Test
    void create_shouldGenerateId_whenIdIsNull() {
        Product p = new Product();
        p.setProductId(null);
        p.setProductName("A");
        p.setProductQuantity(10);

        Product created = service.create(p);

        assertNotNull(created.getProductId());
        assertFalse(created.getProductId().isBlank());
        assertNotNull(repo.findById(created.getProductId()));
    }

    @Test
    void create_shouldGenerateId_whenIdIsBlank() {
        Product p = new Product();
        p.setProductId("   ");
        p.setProductName("B");
        p.setProductQuantity(5);

        Product created = service.create(p);

        assertNotNull(created.getProductId());
        assertFalse(created.getProductId().isBlank());
    }

    @Test
    void create_shouldKeepId_whenIdAlreadyExists() {
        Product p = new Product();
        p.setProductId("fixed-id");
        p.setProductName("C");
        p.setProductQuantity(1);

        Product created = service.create(p);

        assertEquals("fixed-id", created.getProductId());
        assertNotNull(repo.findById("fixed-id"));
    }

    @Test
    void update_shouldUpdateNameAndQuantity_whenProductExists() {
        Product p = new Product();
        p.setProductId("id-1");
        p.setProductName("Old");
        p.setProductQuantity(1);
        service.create(p);

        Product updated = new Product();
        updated.setProductId("id-1"); // optional, but safe
        updated.setProductName("New");
        updated.setProductQuantity(99);

        service.update("id-1", updated);

        Product result = service.findById("id-1");
        assertNotNull(result);
        assertEquals("New", result.getProductName());
        assertEquals(99, result.getProductQuantity());
    }

    @Test
    void deleteProductById_shouldRemoveProduct_whenProductExists() {
        Product p = new Product();
        p.setProductId("id-2");
        p.setProductName("X");
        p.setProductQuantity(2);
        service.create(p);

        service.deleteProductById("id-2");

        assertNull(service.findById("id-2"));
    }

    @Test
    void deleteProductById_shouldNotCrash_whenProductDoesNotExist() {
        assertDoesNotThrow(() -> service.deleteProductById("unknown"));
    }

    @Test
    void findAll_shouldReturnAllProducts() {
        Product p1 = new Product();
        p1.setProductId("id-10");
        p1.setProductName("A");
        p1.setProductQuantity(1);

        Product p2 = new Product();
        p2.setProductId("id-20");
        p2.setProductName("B");
        p2.setProductQuantity(2);

        service.create(p1);
        service.create(p2);

        var list = service.findAll();

        assertEquals(2, list.size());
        assertEquals("id-10", list.get(0).getProductId());
        assertEquals("id-20", list.get(1).getProductId());
    }

    @Test
    void findById_shouldReturnNull_whenNotFound() {
        assertNull(service.findById("does-not-exist"));
    }
}