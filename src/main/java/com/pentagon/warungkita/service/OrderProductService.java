package com.pentagon.warungkita.service;

import com.pentagon.warungkita.model.OrderProduct;
import com.pentagon.warungkita.model.Product;

import java.util.List;
import java.util.Optional;

public interface OrderProductService {
    List<OrderProduct> getAll();
    Optional<OrderProduct> getOrderProductById(Long id);
    OrderProduct saveOrderProduct(OrderProduct orderProduct);
    OrderProduct updateOrderProduct(OrderProduct orderProduct);
    void deleteOrderProductById(Long id);
}
