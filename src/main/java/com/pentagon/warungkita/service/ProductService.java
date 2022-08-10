package com.pentagon.warungkita.service;

import com.pentagon.warungkita.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAll();
    Optional<Product> getProductById(Long productId);
    Product createProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(Long productId);
    List<Product> findByProductNameContaining(String productName);
    List<Product> findByUsersUsernameContaining(String userName);
}
