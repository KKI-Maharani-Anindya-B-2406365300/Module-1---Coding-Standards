package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
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
    public Iterator<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public Product update(String id, Product product) {
        return productRepository.update(id, product);
    }

    @Override
    public boolean deleteById(String id) {
        return productRepository.deleteById(id);
    }
}

