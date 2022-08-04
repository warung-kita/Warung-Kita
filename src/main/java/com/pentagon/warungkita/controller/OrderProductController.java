package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.OrderProductRequestDTO;
import com.pentagon.warungkita.dto.OrderProductResponseDTO;
import com.pentagon.warungkita.dto.OrderProductResponsePOST;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.OrderProduct;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.service.OrderProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/pentagon/warung-kita")
@RestController
@AllArgsConstructor
@Slf4j
public class OrderProductController {
    private OrderProductService orderProductService;

    /*
     * Get all Data Order products table
     * Menggunakan ResponseDTO
     * */

    @GetMapping("/list/order-products")
    public ResponseEntity<Object> orderProductList() {
        try {
            List<OrderProduct> orderList = orderProductService.getAll();
            List<OrderProductResponseDTO> orderMaps = new ArrayList<>();
            for (OrderProduct dataOrder : orderList) {
                Map<String, Object> order = new HashMap<>();
                OrderProductResponseDTO orderResponseDTO = dataOrder.convertToResponse();
                orderMaps.add(orderResponseDTO);
            }
            return ResponseHandler.generateResponse("Successfully  getAll data!", HttpStatus.OK, orderMaps);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Table not found");
        }
    }

    @GetMapping("/list/order-products/{orderProductId}")
    public ResponseEntity<Object> OrderListById(@PathVariable Long orderProductId) {
        try {
            Optional<OrderProduct> orderList = orderProductService.getOrderProductById(orderProductId);
            OrderProduct orderProduct = orderList.get();
            OrderProductResponseDTO orderResponseDTO = orderProduct.convertToResponse();
            return ResponseHandler.generateResponse("Success Get By Id", HttpStatus.OK, orderResponseDTO);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "ID not found");
        }
    }

    @PostMapping("/list/order-products")
    public ResponseEntity<Object> saveOrderProduct(@RequestBody OrderProductRequestDTO orderProductRequestDTO) {
        try {
            OrderProduct orderProductSave = orderProductRequestDTO.convertToEntity();
            OrderProduct orderProduct = orderProductService.saveOrderProduct(orderProductSave);
            OrderProductResponsePOST responsePOST = orderProduct.convertToResponsePOST();
            return ResponseHandler.generateResponse("Successfully  save Order", HttpStatus.CREATED, responsePOST);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Bad Request!!");
        }
    }

    @PutMapping("/update/order-products/{orderProductId}")
    public ResponseEntity<Object> updateOrderProduct(@PathVariable Long orderProductId, @RequestBody OrderProductRequestDTO orderProductRequestDTO) {
        try {
            OrderProduct orderProduct = orderProductRequestDTO.convertToEntity();
            orderProduct.setOrderProductId(orderProductId);
            OrderProduct responseUpdate = orderProductService.updateOrderProduct(orderProduct);
            OrderProductResponseDTO responseDTO = responseUpdate.convertToResponse();
            return ResponseHandler.generateResponse("Data Updated!", HttpStatus.CREATED, responseDTO);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Bad Request");
        }
    }

    @DeleteMapping("/delete/order-products/{orderProductId}")
    public ResponseEntity<Object> deleteOrderProduct(@PathVariable Long orderProductId){
        try {
            orderProductService.deleteOrderProductById(orderProductId);
            Boolean result = Boolean.TRUE;
            return ResponseHandler.generateResponse("Success Delete Booking by ID", HttpStatus.OK, result);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found!");
        }
    }
}