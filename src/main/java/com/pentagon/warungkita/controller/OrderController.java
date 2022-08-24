package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.OrderRequestDTO;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/pentagon/warung-kita")
@RestController
@AllArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearer-key")
@Tag(name = "07. Order")
public class OrderController {

    OrderService orderService;

    /*Get All Data dari Order Table
     * Untuk Penampilan Data Bisa Menggunakan ResponseDTO
     * */
    @GetMapping("/list/order")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> OrderList(){
        return orderService.getAll();
    }

    @GetMapping("/list/order/{orderId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> OrderListById(@PathVariable Long orderId){
        return orderService.getOrderById(orderId);
    }

    /*
     *save(create) order baru untuk order table
     *throws ResourceNotFoundException jika data tidak ditemukan
     * membuat RequestDTO
     * */
    @PostMapping("/save/order")
    @PreAuthorize("hasAuthority('ROLE_BUYER')")
    @Transactional
    public ResponseEntity<Object> saveOrder(@RequestBody OrderRequestDTO orderRequestDTO) throws ResourceNotFoundException {
        return orderService.saveOrder(orderRequestDTO);
    }

    /*
     *update order baru untuk order table
     *throws ResourceNotFoundException jika data tidak ditemukan
     * membuat RequestDTO
     * */
    @PutMapping("/update/order")
    @PreAuthorize("hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> updateOrder( @RequestBody OrderRequestDTO orderRequestDTO){
        return orderService.updateOrder(orderRequestDTO);
    }

    @DeleteMapping("/delete/order/{orderId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> deleteOrder(@PathVariable Long orderId){
        return orderService.deleteOrderById(orderId);
    }
}