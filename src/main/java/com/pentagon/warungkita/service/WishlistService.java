package com.pentagon.warungkita.service;

import com.pentagon.warungkita.dto.WishlistRequestDTO;
import com.pentagon.warungkita.model.Wishlist;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface WishlistService {
    ResponseEntity<Object> getAllWishlist();
    ResponseEntity<Object> getWishlistById(Long id);
    ResponseEntity<Object> createWishlist(WishlistRequestDTO wishlistRequestDTO);
    ResponseEntity<Object> deleteProductListById(Long id);
    Wishlist getReferenceById (Long Id);
    ResponseEntity<Object> findByUserUsernameContaining();
}
