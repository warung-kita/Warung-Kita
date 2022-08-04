package com.pentagon.warungkita.service;

import com.pentagon.warungkita.model.ProductStatus;

import java.util.List;
import java.util.Optional;

public interface ProductStatusService {
    List<ProductStatus> getAll();
    ProductStatus createProductStatus(ProductStatus productStatus);
    Optional<ProductStatus> getProductStatusById(Long Id);
    void deleteProductStatusById(Long Id);
    ProductStatus updateProductStatus(ProductStatus productStatus);
}
