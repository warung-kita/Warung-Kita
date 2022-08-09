package com.pentagon.warungkita.repository;

import com.pentagon.warungkita.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductListRepo extends JpaRepository<ProductList, Long> {
//    @Query(value = "select * from product_list p where productListUsers.user_id =1", nativeQuery = true)
//    public List<ProductList> getWishlistByUserId(@Param("Id")Long Id);
//
//    List<ProductList> findAllByUserIdOrderByCreatedDateDesc(Long userId);
    public List<ProductList> findByUserRolesNameContaining(String name);
}
