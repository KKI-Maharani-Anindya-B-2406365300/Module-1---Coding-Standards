package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private final List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    // TESTS EXPECT Iterator<Product>
    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String id) {
        for (Product p : productData) {
            if (p.getProductId() != null && p.getProductId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public Product update(String id, Product updatedProduct) {
        if (updatedProduct == null) return null;

        Product existing = findById(id);
        if (existing == null) return null;

        existing.setProductName(updatedProduct.getProductName());
        existing.setProductQuantity(updatedProduct.getProductQuantity());
        return existing;
    }

    public boolean deleteById(String id) {
        return productData.removeIf(p ->
                p.getProductId() != null && p.getProductId().equals(id)
        );
    }
}


