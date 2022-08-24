package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.OrderProductRequestDTO;
import com.pentagon.warungkita.dto.OrderProductResponseDTO;

import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.OrderProduct;
import com.pentagon.warungkita.model.Product;
import com.pentagon.warungkita.repository.OrderProductRepo;
import com.pentagon.warungkita.repository.ProductRepo;
import com.pentagon.warungkita.repository.ProductStatusRepo;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.service.OrderProductService;
import com.pentagon.warungkita.service.ProductStatusService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/pentagon/warung-kita")
@RestController
@AllArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearer-key")
@Tag(name = "08.Order Product")
public class OrderProductController {

    private static final Logger logger = LogManager.getLogger(OrderProductController.class);
    private OrderProductService orderProductService;
    private ProductRepo productRepo;
    private OrderProductRepo orderProductRepo;

    /*
     * Get all Data Order products table
     * Menggunakan ResponseDTO
     * */

    @GetMapping("/list/order-products")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> orderProductList() {
        return orderProductService.orderProductList();
    }

    @GetMapping("/list/order-products/{orderProductId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> OrderListById(@PathVariable Long orderProductId) {
       return orderProductService.OrderListById(orderProductId);
    }

    @PostMapping("/list/order-products")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> saveOrderProduct(@RequestBody OrderProductRequestDTO orderProductRequestDTO) {
        return orderProductService.saveOrderProduct(orderProductRequestDTO);
    }

    @PutMapping("/update/order-products/{orderProductId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> updateOrderProduct(@PathVariable Long orderProductId, @RequestBody OrderProductRequestDTO orderProductRequestDTO) {
        return orderProductService.updateOrderProduct(orderProductId, orderProductRequestDTO);
    }

    @DeleteMapping("/delete/order-products/{orderProductId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> deleteOrderProduct(@PathVariable Long orderProductId){
        return orderProductService.deleteOrderProduct(orderProductId);
    }
}