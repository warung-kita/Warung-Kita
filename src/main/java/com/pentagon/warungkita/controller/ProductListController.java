package com.pentagon.warungkita.controller;


import com.pentagon.warungkita.dto.ProductListRequestDTO;
import com.pentagon.warungkita.dto.ProductListResponseDTO;
import com.pentagon.warungkita.dto.ProductListResponsePOST;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.ProductList;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.service.ProductListService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teamD/v1")
@AllArgsConstructor
public class ProductListController {

    private ProductListService productListService;


    @GetMapping("/product_list/all")
    public ResponseEntity<Object> findAllProductList(){
        try{
            List<ProductList> productLists = productListService.getAllProductList();
            List<ProductListResponseDTO> productListmaps = new ArrayList<>();
            for(ProductList dataresult:productLists){
                ProductListResponseDTO productListResponseDTO = dataresult.convertToResponse();
                productListmaps.add(productListResponseDTO);
            }
            return ResponseHandler.generateResponse("Succes Get All", HttpStatus.OK,productListmaps);
        }catch (ResourceNotFoundException e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Table has no value");
        }
    }
    @GetMapping("/product_list/{id}")
    public ResponseEntity<Object> getProductListById(@PathVariable Long id){
        try {
            Optional<ProductList> productList = productListService.getProductListById(id);
            ProductList productListget = productList.get();
            ProductListResponseDTO result = productListget.convertToResponse();
            return ResponseHandler.generateResponse("Success Get By Id",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }
    @PostMapping("/product_list/create")
    public ResponseEntity<Object> productListCreate(@RequestBody ProductListRequestDTO productListRequestDTO){
        try{
            if(productListRequestDTO.getProduct() == null || productListRequestDTO.getUser() == null){
                throw new ResourceNotFoundException("Product List must have product id and user id");
            }
            ProductList productList = productListRequestDTO.convertToEntity();
            productListService.createProductList(productList);
            ProductListResponsePOST result = productList.convertToResponsePost();
            return ResponseHandler.generateResponse("Success Create Product List",HttpStatus.CREATED,result);
        }catch (Exception e){

            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Failed Create Database");
        }
    }
    @PutMapping("/product_list/update/{id}")
    public ResponseEntity<Object> produkListUpdate(@PathVariable Long id, @RequestBody ProductListRequestDTO productListRequestDTO){
        try {
            if(productListRequestDTO.getProduct() == null || productListRequestDTO.getUser() == null){
                throw new ResourceNotFoundException("Product List must have product id and user id");
            }
            ProductList productList = productListRequestDTO.convertToEntity();
            productList.setProductListId(id);
            ProductList updateList = productListService.updateProductList(productList);
            ProductListResponseDTO results = updateList.convertToResponse();
            return ResponseHandler.generateResponse("Success Update Booking",HttpStatus.CREATED,results);
        }catch (Exception e){

            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Bad Request");
        }
    }
    @DeleteMapping("product_list/delete/{id}")
    public ResponseEntity<Object> deleteProductList(@PathVariable Long id){
        try {
            productListService.deleteProductListById(id);
            Boolean result = Boolean.TRUE;
            return ResponseHandler.generateResponse("Success Delete Booking by ID",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }
}
