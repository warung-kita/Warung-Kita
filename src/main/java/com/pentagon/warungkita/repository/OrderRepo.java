package com.pentagon.warungkita.repository;

import com.pentagon.warungkita.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {

}
