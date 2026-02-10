package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {
    private final List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public List<Product> findAll() {
        return new ArrayList<>(productData);
    }

    public Product findById(String productId) {
        for (Product p : productData) {
            if (p.getProductId() != null && p.getProductId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

    public boolean update(Product updated) {
        if (updated == null || updated.getProductId() == null) return false;

        for (int i = 0; i < productData.size(); i++) {
            if (updated.getProductId().equals(productData.get(i).getProductId())) {
                productData.set(i, updated);
                return true;
            }
        }
        return false;
    }

    public boolean deleteById(String productId) {
        return productData.removeIf(p ->
                p.getProductId() != null && p.getProductId().equals(productId)
        );
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
        Product existing = findById(id);
        if (existing == null) return null;

        existing.setProductName(updatedProduct.getProductName());
        existing.setProductQuantity(updatedProduct.getProductQuantity());
        return existing;
    }

    public boolean deleteById(String id) {
        Product existing = findById(id);
        if (existing == null) return false;

        return productData.remove(existing);
    }
}

