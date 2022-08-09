package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.OrderRequestDTO;
import com.pentagon.warungkita.dto.OrderResponseDTO;
import com.pentagon.warungkita.dto.OrderResponsePOST;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Order;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

 @RequestMapping("/pentagon/warung-kita")
@RestController
@AllArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearer-key")
@Tag(name = "7. Order")
public class OrderController {

    private static final Logger logger = LogManager.getLogger(OrderController.class);
    private OrderService orderService;


    /*Get All Data dari Order Table
    * Untuk Penampilan Data Bisa Menggunakan ResponseDTO
    * */
    @GetMapping("/list/order")
    public ResponseEntity<Object> OrderList(){
        try {
            List<Order> orderList = orderService.getAll();
            List<OrderResponseDTO> orderMaps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Order Product     ====================");

            for (Order dataOrder : orderList) {
                Map<String, Object> order = new HashMap<>();
                order.put("OrederID       : ", dataOrder.getOrderId());
                order.put("OrderDate : ", dataOrder.getOrderDate());
                order.put("EkspedisiID    : ", dataOrder.getEkspedisiId());
                order.put("Total    : ", dataOrder.getTotal());
                order.put("UserID : ", dataOrder.getUserId());
                logger.info("OrederID       : " + dataOrder.getOrderId());
                logger.info("OrderDate : " + dataOrder.getOrderDate());
                logger.info("EkspedisiID    : " + dataOrder.getEkspedisiId());
                logger.info("Total    : " + dataOrder.getTotal());
                logger.info("UserID : " + dataOrder.getUserId());
                OrderResponseDTO orderResponseDTO = dataOrder.convertToResponse();
                orderMaps.add(orderResponseDTO);
            }
            logger.info("==================== Logger Start Get All Order Product     ====================");
            return ResponseHandler.generateResponse("Successfully  getAll data!", HttpStatus.OK, orderMaps);
        } catch (ResourceNotFoundException e) {
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Table not found");
        }
    }

    @GetMapping("/list/order/{orderId}")
    public ResponseEntity<Object> OrderListById(@PathVariable Long orderId){
        try {
            Optional<Order> orderList = orderService.getOrderById(orderId);
            Order order = orderList.get();
            OrderResponseDTO orderResponseDTO = order.convertToResponse();
            logger.info("==================== Logger Start Get Order Product By ID ====================");
            logger.info(orderResponseDTO);
            logger.info("==================== Logger End Get Order Product By ID =================");
            return ResponseHandler.generateResponse("Success Get By Id",HttpStatus.OK,orderResponseDTO);
        } catch (ResourceNotFoundException e) {
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "ID not found");
        }
    }

    /*
    *save(create) order baru untuk order table
    *throws ResourceNotFoundException jika data tidak ditemukan
    * membuat RequestDTO
    * */
    @PostMapping("/save/order")
    public ResponseEntity<Object> saveOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        try {
            Order orderSave = orderRequestDTO.convertToEntity();
            Order order = orderService.saveOrder(orderSave);
            OrderResponsePOST orderResponsePOST = order.convertToResponsePOST();
            logger.info("==================== Logger Start Post Order Product ====================");
            logger.info(orderResponsePOST);
            logger.info("==================== Logger Start Post Order Product =================");
            return ResponseHandler.generateResponse("Successfully  save Order", HttpStatus.CREATED, orderResponsePOST);
        } catch (ResourceNotFoundException e) {
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Bad Request!!");
        }
    }

    /*
     *update order baru untuk order table
     *throws ResourceNotFoundException jika data tidak ditemukan
     * membuat RequestDTO
     * */
    @PutMapping("/update/order/{orderId}")
    public ResponseEntity<Object> updateOrder(@PathVariable Long orderId, @RequestBody OrderRequestDTO orderRequest){
        try {
            Order order = orderRequest.convertToEntity();
            order.setOrderId(orderId);
            Order responseUpdate = orderService.updateOrder(order);
            OrderResponseDTO responseDTO = responseUpdate.convertToResponse();
            logger.info("==================== Logger Start Update Order Product By ID ====================");
            logger.info(responseDTO);
            logger.info("==================== Logger End Update Order Product By ID =================");
            return ResponseHandler.generateResponse("Data Updated!", HttpStatus.CREATED, responseDTO);
        }catch (ResourceNotFoundException e) {
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Bad Request");
        }
    }

    @DeleteMapping("/delete/order/{orderId}")
    public ResponseEntity<Object> deleteOrder(@PathVariable Long orderId){
        try {
            orderService.deleteOrderById(orderId);
            Boolean result = Boolean.TRUE;
            logger.info("==================== Logger Start Delete Order Product By ID ====================");
            logger.info(result);
            logger.info("==================== Logger End Delete Order Product By ID =================");
            return ResponseHandler.generateResponse("Success Delete Booking by ID", HttpStatus.OK, result);
        } catch (ResourceNotFoundException e) {
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found!");
        }
    }


}
