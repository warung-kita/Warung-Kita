package com.pentagon.warungkita.repository;

import com.pentagon.warungkita.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
