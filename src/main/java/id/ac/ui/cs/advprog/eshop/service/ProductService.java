package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.Iterator;

public interface ProductService {
    Product create(Product product);

    // TESTS EXPECT Iterator<Product>
    Iterator<Product> findAll();

    Product findById(String id);
    Product update(String id, Product product);
    boolean deleteById(String id);
}

