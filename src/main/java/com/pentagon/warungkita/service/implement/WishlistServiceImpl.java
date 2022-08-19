package com.pentagon.warungkita.service.implement;

import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Wishlist;
import com.pentagon.warungkita.repository.WishlistRepo;
import com.pentagon.warungkita.service.WishlistService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private WishlistRepo wishlistRepo;

    @Override
    public List<Wishlist> getAllProductList() {
        List<Wishlist> optionalWishlist = wishlistRepo.findAll();
        if(optionalWishlist.isEmpty()){
            throw new ResourceNotFoundException("Data is Empty");
        }
        return this.wishlistRepo.findAll();
    }

    @Override
    public Optional<Wishlist> getProductListById(Long Id) throws ResourceNotFoundException {
        Optional<Wishlist> optionalProductList = wishlistRepo.findById(Id);
        if(optionalProductList.isEmpty()){
            throw new ResourceNotFoundException("Wishlist not exist with id " + Id);
        }
        return this.wishlistRepo.findById(Id);
    }

    @Override
    public Wishlist createProductList(Wishlist wishlist) {
        return this.wishlistRepo.save(wishlist);
    }

    @Override
    public void deleteProductListById(Long Id) throws ResourceNotFoundException{
        Optional<Wishlist> optionalProductList = wishlistRepo.findById(Id);
        if(optionalProductList.isEmpty()){
            throw new ResourceNotFoundException("Wishlist not exist with id " + Id);
        }
        Wishlist wishlist = wishlistRepo.getReferenceById(Id);
        this.wishlistRepo.delete(wishlist);
    }

    @Override
    public Wishlist updateProductList(Wishlist wishlist) throws ResourceNotFoundException{
        Optional<Wishlist> optionalProductList = wishlistRepo.findById(wishlist.getWishlistId());
        if(optionalProductList.isEmpty()){
            throw new ResourceNotFoundException("Wishlist not exist with id " + wishlist.getWishlistId());
        }
        return this.wishlistRepo.save(wishlist);
    }

    @Override
    public Wishlist getReferenceById(Long Id) {
        return null;
    }

    @Override
    public List<Wishlist> findByUserUsernameContaining(String userName) {
        List<Wishlist> wishlists = wishlistRepo.findByUserUsernameContaining(userName);
        if(wishlists.isEmpty()){
            throw new ResourceNotFoundException("User not have Wishlist");
        }
        return this.wishlistRepo.findByUserUsernameContaining(userName);
    }

}
