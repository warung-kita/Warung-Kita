package com.pentagon.warungkita.repository;

import com.pentagon.warungkita.model.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductStatusRepo extends JpaRepository<ProductStatus, Long> {


}
