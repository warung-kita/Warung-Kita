package com.pentagon.warungkita.repository;

import com.pentagon.warungkita.model.OrderProduct;
import com.pentagon.warungkita.model.Product;
import com.pentagon.warungkita.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderProductRepo extends JpaRepository<OrderProduct, Long> {

//boolean existsByUserAndProduct( Product product);
}
