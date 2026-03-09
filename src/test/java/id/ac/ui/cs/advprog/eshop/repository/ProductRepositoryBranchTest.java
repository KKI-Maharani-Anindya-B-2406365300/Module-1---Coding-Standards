package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryBranchTest {

    @SuppressWarnings("unchecked")
    private List<Product> getProductData(ProductRepository repo) throws Exception {
        Field field = ProductRepository.class.getDeclaredField("productData");
        field.setAccessible(true);
        return (List<Product>) field.get(repo);
    }

    @Test
    void findById_shouldSkipStoredProductWhenProductIdIsNull() throws Exception {
        ProductRepository repo = new ProductRepository();

        Product p = new Product();
        p.setProductId(null);
        p.setProductName("A");
        p.setProductQuantity(1);

        getProductData(repo).add(p);

        assertNull(repo.findById("anything"));
    }

    @Test
    void deleteById_shouldReturnFalse_whenStoredProductIdIsNull() throws Exception {
        ProductRepository repo = new ProductRepository();

        Product p = new Product();
        p.setProductId(null);
        p.setProductName("A");
        p.setProductQuantity(1);

        getProductData(repo).add(p);

        assertFalse(repo.deleteById("anything"));
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
    void deleteById_shouldReturnFalse_whenIdNotFound() {
        ProductRepository repo = new ProductRepository();
        assertFalse(repo.deleteById("missing-id"));
    }
}