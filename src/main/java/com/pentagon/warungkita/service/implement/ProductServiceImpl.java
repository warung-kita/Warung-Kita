package com.pentagon.warungkita.service.implement;

import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Product;
import com.pentagon.warungkita.repository.ProductRepo;
import com.pentagon.warungkita.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;


    @Override
    public List<Product> getAll() {
        List<Product> product = productRepo.findAll();
        if(product.isEmpty()){
            throw new ResourceNotFoundException("Product Not Exist");
        }
        return this.productRepo.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long productId) throws ResourceNotFoundException {
        Optional<Product> product = productRepo.findById(productId);
        if(product.isEmpty()){
            throw new ResourceNotFoundException("Product not exist with id " + productId);
        }
        return this.productRepo.findById(productId);
    }

    @Override
    public Product createProduct(Product product) {
        return this.productRepo.save(product);
    }

    @Override
    public Product updateProduct(Product product) throws ResourceNotFoundException {
        Optional<Product> optionalProduct = productRepo.findById(product.getProductId());
        if(optionalProduct.isEmpty()){
            throw new ResourceNotFoundException("Product not exist with id " + product.getProductId());
        }
        return this.productRepo.save(product);
    }

    @Override
    public void deleteProduct(Long productId) throws ResourceNotFoundException{
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if(optionalProduct.isEmpty()){
            throw new ResourceNotFoundException("Product not exist with id " + productId);
        }
        Product product = productRepo.getReferenceById(productId);
        this.productRepo.delete(product);
    }

    @Override
    public List<Product> findByProductNameContaining(String productName) {
        List<Product> products = productRepo.findByProductNameContaining(productName);

        return this.productRepo.findByProductNameContaining(productName);
    }

    @Override
    public List<Product> findByUsersUsernameContaining(String userName) {
        List<Product> products = productRepo.findByUsersUsernameContaining(userName);
        return this.productRepo.findByUsersUsernameContaining(userName);
    }
}
