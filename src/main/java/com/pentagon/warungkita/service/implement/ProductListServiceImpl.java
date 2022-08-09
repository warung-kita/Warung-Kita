package com.pentagon.warungkita.service.implement;

import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.ProductList;
import com.pentagon.warungkita.repository.ProductListRepo;
import com.pentagon.warungkita.service.ProductListService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductListServiceImpl implements ProductListService {

    private ProductListRepo productListRepo;

    @Override
    public List<ProductList> getAllProductList() {
        List<ProductList> optionalProductList = productListRepo.findAll();
        if(optionalProductList.isEmpty()){
            throw new ResourceNotFoundException("Data is Empty");
        }
        return this.productListRepo.findAll();
    }

    @Override
    public Optional<ProductList> getProductListById(Long Id) throws ResourceNotFoundException {
        Optional<ProductList> optionalProductList = productListRepo.findById(Id);
        if(optionalProductList.isEmpty()){
            throw new ResourceNotFoundException("Booking not exist with id " + Id);
        }
        return this.productListRepo.findById(Id);
    }

    @Override
    public ProductList createProductList(ProductList productList) {
        return this.productListRepo.save(productList);
    }

    @Override
    public void deleteProductListById(Long Id) throws ResourceNotFoundException{
        Optional<ProductList> optionalProductList = productListRepo.findById(Id);
        if(optionalProductList.isEmpty()){
            throw new ResourceNotFoundException("Product List not exist with id " + Id);
        }
        ProductList productList = productListRepo.getReferenceById(Id);
        this.productListRepo.delete(productList);
    }

    @Override
    public ProductList updateProductList(ProductList productList) throws ResourceNotFoundException{
        Optional<ProductList> optionalProductList = productListRepo.findById(productList.getProductListId());
        if(optionalProductList.isEmpty()){
            throw new ResourceNotFoundException("Booking not exist with id " + productList.getProductListId());
        }
        return this.productListRepo.save(productList);
    }

    @Override
    public ProductList getReferenceById(Long Id) {
        return null;
    }

//    @Override
//    public List<ProductList> getWishlistByUsersId(Long Id) {
//        List<ProductList> optionalProductList = productListRepo.getWishlistByUserId(Id);
//        if(optionalProductList.isEmpty()){
//            throw new ResourceNotFoundException("Wishlist not exist with Users Id " +Id);
//        }
//        return this.productListRepo.getWishlistByUserId(Id);
//    }
}
