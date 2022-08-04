package com.pentagon.warungkita.service.implement;

import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Order;
import com.pentagon.warungkita.repository.OrderRepo;
import com.pentagon.warungkita.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private OrderRepo orderRepo;

    @Override
    public List<Order> getAll() {
        List<Order> orderList = this.orderRepo.findAll();
        if (orderList.isEmpty()) {
            log.error("No order found");
            throw new ResourceNotFoundException("No order found");
        }
        return this.orderRepo.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) throws ResourceNotFoundException {
        Optional<Order> optionalOrder = this.orderRepo.findById(id);
        if (optionalOrder.isEmpty()) {
            log.error("No order found");
            throw new ResourceNotFoundException("Order not found with id " + id);
        }
        return this.orderRepo.findById(id);
    }

    @Override
    public Order saveOrder(Order order) {
        return this.orderRepo.save(order);
    }

    @Override
    public Order updateOrder(Order order) {
        Optional<Order> updatedOrder = this.orderRepo.findById(order.getOrderId());
        if (updatedOrder.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with id " + order.getOrderId());
        }
        return this.orderRepo.save(order);
    }

    @Override
    public void deleteOrderById(Long id) {
        Optional<Order> updatedOrder = this.orderRepo.findById(id);
        if (updatedOrder.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with id " + id);
        }
        Order order = orderRepo.getReferenceById(id);
        this.orderRepo.delete(order);
    }
}
