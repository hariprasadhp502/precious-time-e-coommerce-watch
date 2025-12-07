package com.precious.watchapp.service;

import com.precious.watchapp.model.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(Long id);
    Product getProductById(Long id);
    List<Product> getAllProducts();
}
