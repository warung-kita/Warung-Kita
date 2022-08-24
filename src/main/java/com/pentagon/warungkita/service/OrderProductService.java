package com.pentagon.warungkita.service;

import com.pentagon.warungkita.dto.OrderProductRequestDTO;
import com.pentagon.warungkita.model.OrderProduct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface OrderProductService {
    ResponseEntity<Object> OrderListById(Long orderProductId);
    ResponseEntity<Object> saveOrderProduct (OrderProductRequestDTO orderProductRequestDTO);
    ResponseEntity<Object> orderProductList();
    ResponseEntity<Object> updateOrderProduct(Long orderProductId,OrderProductRequestDTO orderProductRequestDTO);
    ResponseEntity<Object> deleteOrderProduct(@PathVariable Long orderProductId);
}
