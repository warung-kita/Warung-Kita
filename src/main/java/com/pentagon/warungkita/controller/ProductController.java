package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.*;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Product;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/pentagon/warung-kita")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    /**
     * Get All Product
     * @return
     */
    @GetMapping("/product/all")
    public ResponseEntity<Object> findAll() {
        try{
            List<Product> products = productService.getAll();
            List<ProductResponseDTO> productMaps = new ArrayList<>();
            for(Product dataResult:products){
                ProductResponseDTO productResponseDTO = dataResult.convertToResponse();
                productMaps.add(productResponseDTO);
            }
            return ResponseHandler.generateResponse("Successfully Get All Product", HttpStatus.OK,productMaps);
        }catch (ResourceNotFoundException e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Table has no value");
        }
    }

    /**
     * Get Product By ID
     * @param productId
     * @return
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<Object> getCategoriesById(@PathVariable Long productId){
        try {
            Optional<Product> product = productService.getProductById(productId);
            Product productGet = product.get();
            ProductResponseDTO result = productGet.convertToResponse();
            return ResponseHandler.generateResponse("Successfully Get Product Id",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }

    /**
     * Add New Product
     * @param productRequestDTO
     * @return
     */
    @PostMapping("/product/add")
    public ResponseEntity<Object> createCategories(@RequestBody ProductRequestDTO productRequestDTO){
        try{
            if(productRequestDTO.getProductName() == null || productRequestDTO.getCategories() == null || productRequestDTO.getQuantity() == null
            || productRequestDTO.getSku() == null || productRequestDTO.getProductStatusId() == null || productRequestDTO.getRegularPrice() == null){
                throw new ResourceNotFoundException("Please Input All Field");
            }
            Product product= productRequestDTO.convertToEntity();
            productService.createProduct(product);
            ProductResponsePOST result = product.convertToResponsePost();
            return ResponseHandler.generateResponse("Successfully Add Product",HttpStatus.CREATED,result);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Failed Create Categories");
        }
    }

    /**
     * Edit Product Data
     * @param productId
     * @param productRequestDTO
     * @return
     */
    @PutMapping("/product/update/{productId}")
    public ResponseEntity<Object> updateCategories(@PathVariable Long productId, @RequestBody ProductRequestDTO productRequestDTO){
        try {
            if(productRequestDTO.getProductName() == null || productRequestDTO.getCategories() == null || productRequestDTO.getQuantity() == null
                    || productRequestDTO.getSku() == null || productRequestDTO.getProductStatusId() == null || productRequestDTO.getRegularPrice() == null){
                throw new ResourceNotFoundException("Please Input All Field");
            }
            Product product = productRequestDTO.convertToEntity();
            product.setProductId(productId);
            Product updateProduct = productService.updateProduct(product);
            ProductResponseDTO results = product.convertToResponse();
            return ResponseHandler.generateResponse("Successfully Update Product",HttpStatus.CREATED,results);
        }catch (Exception e){

            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Bad Request");
        }
    }

    /**
     * Delete Product By ID
     * @param productId
     * @return
     */
    @DeleteMapping("product/delete/{productId}")
    public ResponseEntity<Object> deleteCategories(@PathVariable Long productId){
        try {
            productService.deleteProduct(productId);
            Boolean result = Boolean.TRUE;
            return ResponseHandler.generateResponse("Successfully Delete Product",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }
}
