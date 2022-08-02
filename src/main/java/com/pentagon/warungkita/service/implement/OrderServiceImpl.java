package com.pentagon.warungkita.service.implement;

import com.pentagon.warungkita.repository.OrderRepo;
import com.pentagon.warungkita.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepo orderRepo;
}
