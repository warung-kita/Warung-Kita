package com.pentagon.warungkita.service;

import com.pentagon.warungkita.dto.ProductRequestDTO;
import com.pentagon.warungkita.model.Product;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ResponseEntity<Object> getAllProduct();
    ResponseEntity<Object> getProductById(Long productId);
    ResponseEntity<Object> createProduct(ProductRequestDTO productRequestDTO);
    ResponseEntity<Object> updateProduct(Long productId, ProductRequestDTO productRequestDTO);
    ResponseEntity<Object> deleteProduct(Long productId);
    List<Product> findByProductNameContaining(String productName);
    List<Product> findByUsersUsernameContaining(String userName);

    List<Product> findByUsersUserId(Long userId);
}
