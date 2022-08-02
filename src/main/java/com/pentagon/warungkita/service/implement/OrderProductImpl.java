package com.pentagon.warungkita.service.implement;

import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Order;
import com.pentagon.warungkita.model.OrderProduct;
import com.pentagon.warungkita.repository.OrderProductRepo;
import com.pentagon.warungkita.repository.OrderRepo;
import com.pentagon.warungkita.service.OrderProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OrderProductImpl implements OrderProductService {

    private OrderProductRepo orderProductRepo;

    @Override
    public List<OrderProduct> getAll() {
        List<OrderProduct> orderProductList = this.orderProductRepo.findAll();
        if (orderProductList.isEmpty()) {
            log.error("No order found");
            throw new ResourceNotFoundException("No order found");
        }
        return this.orderProductRepo.findAll();
    }

    @Override
    public Optional<OrderProduct> getOrderProductById(Long id) throws ResourceNotFoundException {
        Optional<OrderProduct> optionalOrderProduct = this.orderProductRepo.findById(id);
        if (optionalOrderProduct.isEmpty()) {
            log.error("No order found");
            throw new ResourceNotFoundException("Order not found with id " + id);
        }
        return this.orderProductRepo.findById(id);
    }

    @Override
    public OrderProduct saveOrderProduct(OrderProduct orderProduct) {
        return this.orderProductRepo.save(orderProduct);
    }

    @Override
    public OrderProduct updateOrderProduct(OrderProduct orderProduct) {
        Optional<OrderProduct> updatedOrder = this.orderProductRepo.findById(orderProduct.getOrderProductId());
        if (updatedOrder.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with id " + orderProduct.getOrderProductId());
        }
        return this.orderProductRepo.save(orderProduct);
    }

    @Override
    public void deleteOrderProductById(Long id) {
        Optional<OrderProduct> updatedOrder = this.orderProductRepo.findById(id);
        if (updatedOrder.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with id " + id);
        }
        OrderProduct orderProduct = orderProductRepo.getReferenceById(id);
        this.orderProductRepo.delete(orderProduct);
    }
}
