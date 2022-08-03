package com.pentagon.warungkita.repository;

import com.pentagon.warungkita.model.ProductList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductListRepo extends JpaRepository<ProductList, Long> {

}
