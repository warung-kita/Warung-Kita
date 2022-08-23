package com.pentagon.warungkita.service;

import com.pentagon.warungkita.dto.OrderProductRequestDTO;
import com.pentagon.warungkita.model.OrderProduct;
import com.pentagon.warungkita.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

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
