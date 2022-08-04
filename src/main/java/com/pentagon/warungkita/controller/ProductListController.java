package com.pentagon.warungkita.controller;


import com.pentagon.warungkita.dto.ProductListRequestDTO;
import com.pentagon.warungkita.dto.ProductListResponseDTO;
import com.pentagon.warungkita.dto.ProductListResponsePOST;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.ProductList;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.service.ProductListService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/pentagon/warung-kita")
@AllArgsConstructor
@Slf4j
public class ProductListController {

    private static final Logger logger = LogManager.getLogger(ProductListController.class);
    private ProductListService productListService;


    @GetMapping("/product_list/all")
    public ResponseEntity<Object> findAllProductList(){
        try{
            List<ProductList> productLists = productListService.getAllProductList();
            List<ProductListResponseDTO> productListmaps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Product List ====================");
            for(ProductList dataresult:productLists){
                ProductListResponseDTO productListResponseDTO = dataresult.convertToResponse();
                productListmaps.add(productListResponseDTO);
                logger.info("code :"+dataresult.getProductListId());
                logger.info("User :"+dataresult.getUser() );
                logger.info("Product :"+dataresult.getProduct() );
                logger.error("------------------------------------");
            }
            logger.info("==================== Logger End  ====================");
            return ResponseHandler.generateResponse("Succes Get All", HttpStatus.OK,productListmaps);
        }catch (ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Table has no value");
        }
    }
    @GetMapping("/product_list/{id}")
    public ResponseEntity<Object> getProductListById(@PathVariable Long id){
        try {
            Optional<ProductList> productList = productListService.getProductListById(id);
            ProductList productListget = productList.get();
            ProductListResponseDTO result = productListget.convertToResponse();
            logger.info("======== Logger Start Find Product List with ID "+id+ "  ========");
            logger.info("User :"+result.getNamaUser() );
            logger.info("Product :"+result.getNama() );
            logger.info("==================== Logger End =================");
            return ResponseHandler.generateResponse("Success Get By Id",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
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
            logger.info("======== Logger Start   ========");
            logger.info("User :"+productList.getUser() );
            logger.info("Product :"+productList.getProduct());
            logger.info("==================== Logger End =================");
            return ResponseHandler.generateResponse("Success Create Product List",HttpStatus.CREATED,result);
        }catch (Exception e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
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
            logger.info("======== Logger Start   ========");
            logger.info("User :"+results.getNamaUser());
            logger.info("Product :"+results.getNama());
            logger.info("==================== Logger End =================");
            return ResponseHandler.generateResponse("Success Update Booking",HttpStatus.CREATED,results);
        }catch (Exception e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Bad Request");
        }
    }
    @DeleteMapping("product_list/delete/{id}")
    public ResponseEntity<Object> deleteProductList(@PathVariable Long id){
        try {
            productListService.deleteProductListById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            logger.info("======== Logger Start   ========");
            logger.info("Film deleted " + response);
            logger.info("==================== Logger End =================");
            return ResponseHandler.generateResponse("Success Delete Booking by ID",HttpStatus.OK,response);
        }catch(ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }
}
