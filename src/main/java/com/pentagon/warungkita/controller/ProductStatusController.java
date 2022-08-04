package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.ProductStatus;
import com.pentagon.warungkita.repository.ProductStatusRepo;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.service.ProductStatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pentagon/warung-kita")
@AllArgsConstructor
public class ProductStatusController {

    private final ProductStatusService productStatusService;
    private final ProductStatusRepo productStatusRepo;

    @GetMapping("/product_status/all")
    public ResponseEntity<Object> getAllProductStatus() {
        try {
            List<ProductStatus> result = productStatusService.getAll();
            List<Map<String, Object>> maps = new ArrayList<>();
            for(ProductStatus productStatus : result){
                Map<String, Object> productStatusMaps = new HashMap<>();
                productStatusMaps.put("ID            ", productStatus.getProductStatusId());
                productStatusMaps.put("Name          ", productStatus.getName());
                maps.add(productStatusMaps);
            }
            return ResponseHandler.generateResponse("Successfully Get All Product Status!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, "Table Has No Value!");
        }
    }
    @GetMapping("/product_status/{Id}")
    public ResponseEntity<Object> getProductStatusById(@PathVariable Long Id) {
        try {
            ProductStatus productStatus = productStatusService.getProductStatusById(Id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product Status not exist with Id :" + Id));
            Map<String, Object> data = new HashMap<>();
            List<Map<String, Object>> maps = new ArrayList<>();
            data.put("ID            ", productStatus.getProductStatusId());
            data.put("Name          ", productStatus.getName());
            maps.add(data);
            return ResponseHandler.generateResponse("Successfully Get Product Status By ID!", HttpStatus.OK, maps);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found!" );
        }
    }

    @PostMapping("/product_status/create")
    public ResponseEntity <Object> createProductStatus(@RequestBody ProductStatus productStatus) {

        try {
            productStatusService.createProductStatus(productStatus);
            ProductStatus ekspedisiresult = productStatusService.createProductStatus(productStatus);
            Map<String, Object> map = new HashMap<>();
            List<Map<String, Object>> maps = new ArrayList<>();
            map.put("ID             ", ekspedisiresult.getProductStatusId());
            map.put("Username       ", ekspedisiresult.getName());
            maps.add(map);
            return ResponseHandler.generateResponse("Successfully Created Product Status!", HttpStatus.CREATED, maps);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Product Status Already Exist!");
        }
    }
    @PutMapping("/product_status/update/{Id}")
    public ResponseEntity<Object> updateProductStatus(@PathVariable Long Id, @RequestBody ProductStatus productStatusDetails){
        try {
            ProductStatus productStatus = productStatusService.getProductStatusById(Id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product Status not exist withId :" + Id));
            productStatus.setName(productStatusDetails.getName());
            ProductStatus updatedProductStatus = productStatusRepo.save(productStatus);
            return ResponseHandler.generateResponse("Successfully Updated Product Status!",HttpStatus.OK, productStatus);
        }catch(Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data Not Found!");
        }
    }

    @DeleteMapping("/product_status/delete/{Id}")
    public ResponseEntity<Object> deleteProductStatus(@PathVariable Long Id){
        try {
            productStatusService.deleteProductStatusById(Id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);;
            return ResponseHandler.generateResponse("Successfully Delete Ekspedisi! ", HttpStatus.OK, response);
        } catch (ResourceNotFoundException e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found!" );
        }
    }
}
