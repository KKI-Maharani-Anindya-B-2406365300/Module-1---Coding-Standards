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
}

