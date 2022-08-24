package com.pentagon.warungkita.service;

import com.pentagon.warungkita.dto.OrderProductRequestDTO;
import com.pentagon.warungkita.model.OrderProduct;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface OrderProductService {
    List<OrderProduct> getAll();
    Optional<OrderProduct> getOrderProductById(Long id);
//    OrderProduct saveOrderProduct(OrderProduct orderProduct);
    ResponseEntity<Object> saveOrderProduct (OrderProductRequestDTO orderProductRequestDTO);
    OrderProduct updateOrderProduct(OrderProduct orderProduct);
    void deleteOrderProductById(Long id);
}
