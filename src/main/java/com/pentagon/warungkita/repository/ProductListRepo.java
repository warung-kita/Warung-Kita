package com.pentagon.warungkita.repository;

import com.pentagon.warungkita.model.ProductList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductListRepo extends JpaRepository<ProductList, Long> {
//    @Query("Select p from ProdukList p where p.user.username like %:username%")
//    public List<ProductList> getBookingByFilmName(@Param("name")String name);
}
