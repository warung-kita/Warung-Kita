package com.pentagon.warungkita.service;

import com.pentagon.warungkita.model.Wishlist;

import java.util.List;
import java.util.Optional;

public interface WishlistService {
    List<Wishlist> getAllProductList();
    Optional<Wishlist> getProductListById(Long Id);
    Wishlist createProductList(Wishlist wishlist);
    void deleteProductListById(Long Id);
    Wishlist updateProductList(Wishlist wishlist);
    Wishlist getReferenceById (Long Id);
//    List<ProductList> getWishlistByUserId(String name);


    List<Wishlist> findByUserUsernameContaining(String userName);
}
