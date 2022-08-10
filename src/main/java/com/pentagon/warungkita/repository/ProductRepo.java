package com.pentagon.warungkita.repository;

import com.pentagon.warungkita.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {
//    @Query(value = "select p2.product_name , p2.quantity ,p2.regular_price , c.\"name\" from products p2\n" +
//            "inner join categories c on p2.product_id = c.categories_id \n" +
//            "where p2.product_name =?1 ",nativeQuery = true)
//    public List<Product> findProductByCategory(String product_name);
//    @Query(value = "select p2.quantity,p2.regular_price from products p2 where p2.product_name like %product_name%",nativeQuery = true)
//    public List<Product> findProductByCategory(@Param("product_name")String product_name);
    public List<Product> findByProductNameContaining(String productName);
    public List<Product> findByUsersUsernameContaining(String userName);
    public List<Product> findByUsersUserId(Long userId);

}
