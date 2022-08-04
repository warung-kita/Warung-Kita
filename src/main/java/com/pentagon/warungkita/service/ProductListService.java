package com.pentagon.warungkita.service;

import com.pentagon.warungkita.model.ProductList;

import java.util.List;
import java.util.Optional;

public interface ProductListService {
    List<ProductList> getAllProductList();
    Optional<ProductList> getProductListById(Long Id);
    ProductList createProductList(ProductList productList);
    void deleteProductListById(Long Id);
    ProductList updateProductList(ProductList productList);
    ProductList getReferenceById (Long Id);
}
