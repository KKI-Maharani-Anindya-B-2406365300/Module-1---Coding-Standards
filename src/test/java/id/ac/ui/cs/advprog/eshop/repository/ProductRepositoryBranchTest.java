package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryBranchTest {

    @Test
    void findById_shouldReturnNull_whenStoredProductIdIsNull() {
        ProductRepository repo = new ProductRepository();

        Product p = new Product();
        p.setProductId(null);
        p.setProductName("A");
        p.setProductQuantity(1);
        repo.create(p);

        assertNull(repo.findById("anything"));
    }

    @Test
    void update_shouldReturnNull_whenUpdatedProductIsNull() {
        ProductRepository repo = new ProductRepository();
        assertNull(repo.update("any-id", null));
    }

    @Test
    void update_shouldReturnNull_whenProductNotFound() {
        ProductRepository repo = new ProductRepository();

        Product updated = new Product();
        updated.setProductName("X");
        updated.setProductQuantity(10);

        assertNull(repo.update("missing", updated));
    }

    @Test
    void deleteById_shouldReturnFalse_whenStoredProductIdIsNull() {
        ProductRepository repo = new ProductRepository();

        Product p = new Product();
        p.setProductId(null);
        p.setProductName("A");
        p.setProductQuantity(1);
        repo.create(p);

        assertFalse(repo.deleteById("anything"));
    }

    @Test
    void deleteById_shouldReturnFalse_whenIdNotFound() {
        ProductRepository repo = new ProductRepository();
        assertFalse(repo.deleteById("missing-id"));
    }
}
