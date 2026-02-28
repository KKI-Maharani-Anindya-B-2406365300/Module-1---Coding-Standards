package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        if (product.getProductId() == null || product.getProductId().isBlank()) {
            product.setProductId(UUID.randomUUID().toString());
        }
        return productRepository.create(product);
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> it = productRepository.findAll();
        List<Product> allProducts = new ArrayList<>();
        it.forEachRemaining(allProducts::add);
        return allProducts;
    }

    @Override
    public Product findById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public void update(String id, Product product) {
        productRepository.update(id, product);
    }

    @Override
    public void deleteProductById(String id) {
        productRepository.deleteById(id);
    }
}