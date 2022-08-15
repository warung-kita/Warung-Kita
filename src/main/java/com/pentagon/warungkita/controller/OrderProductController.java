package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.OrderProductRequestDTO;
import com.pentagon.warungkita.dto.OrderProductResponseDTO;
import com.pentagon.warungkita.dto.OrderProductResponsePOST;
import com.pentagon.warungkita.exception.ResourceAlreadyExistException;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.OrderProduct;
import com.pentagon.warungkita.model.Product;
import com.pentagon.warungkita.model.ProductStatus;
import com.pentagon.warungkita.model.Users;
import com.pentagon.warungkita.repository.OrderProductRepo;
import com.pentagon.warungkita.repository.ProductRepo;
import com.pentagon.warungkita.repository.ProductStatusRepo;
import com.pentagon.warungkita.repository.UsersRepo;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.security.service.UserDetailsImpl;
import com.pentagon.warungkita.service.OrderProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/pentagon/warung-kita")
@RestController
@AllArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearer-key")
@Tag(name = "8.Order Product")
public class OrderProductController {

    private static final Logger logger = LogManager.getLogger(OrderProductController.class);
    private OrderProductService orderProductService;
//    private UsersRepo usersRepo;
    private ProductRepo productRepo;
    private OrderProductRepo orderProductRepo;
    private ProductStatusRepo productStatusRepo;

    /*
     * Get all Data Order products table
     * Menggunakan ResponseDTO
     * */

    @GetMapping("/list/order-products")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> orderProductList() {
        try {
            List<OrderProduct> orderList = orderProductService.getAll();
            List<OrderProductResponseDTO> orderMaps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Order Product     ====================");
            for (OrderProduct dataOrder : orderList) {
                Map<String, Object> order = new HashMap<>();
                order.put("OrderProductID       : ", dataOrder.getOrderProductId());
                order.put("ProductID : ", dataOrder.getOrderProductId());
//                order.put("SKU    : ", dataOrder.getSku());
//                order.put("ProductName    : ", dataOrder.getProductName());
//                order.put("Description : ", dataOrder.getDescription());
//                order.put("Price : ", dataOrder.getPrice());
                logger.info("OrderProductID       : " + dataOrder.getOrderProductId());
                logger.info("ProductID : " + dataOrder.getProductId());
//                logger.info("SKU    : " + dataOrder.getSku());
//                logger.info("ProductName    : " + dataOrder.getProductName());
//                logger.info("Description : " + dataOrder.getDescription());
//                logger.info("Price : " + dataOrder.getPrice());
                OrderProductResponseDTO orderResponseDTO = dataOrder.convertToResponse();
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

    @GetMapping("/list/order-products/{orderProductId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> OrderListById(@PathVariable Long orderProductId) {
        try {
            Optional<OrderProduct> orderList = orderProductService.getOrderProductById(orderProductId);
            OrderProduct orderProduct = orderList.get();
            OrderProductResponseDTO orderResponseDTO = orderProduct.convertToResponse();
            logger.info("==================== Logger Start Get Order Product By ID ====================");
            logger.info(orderResponseDTO);
            logger.info("==================== Logger End Get Order Product By ID =================");
            return ResponseHandler.generateResponse("Success Get By Id", HttpStatus.OK, orderResponseDTO);
        } catch (ResourceNotFoundException e) {
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "ID not found");
        }
    }

    @PostMapping("/list/order-products")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> saveOrderProduct(@RequestBody OrderProductRequestDTO orderProductRequestDTO, ProductStatus productStatus) {
        try {

                Product product = productRepo.findById(orderProductRequestDTO.getProduct().getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
                OrderProduct orderProduct = OrderProduct.builder()
                        .productId(orderProductRequestDTO.getProduct())
                        .quantity(orderProductRequestDTO.getQuantity())
                        .build();
                Integer totalPrice = product.getRegularPrice() * orderProductRequestDTO.getQuantity();
                orderProduct.setSubtotal(totalPrice);


                if(product.getQuantity() < orderProduct.getQuantity()){
                    throw new ResourceNotFoundException("stok kurang");
                }

                Integer newQty = product.getQuantity() - orderProductRequestDTO.getQuantity();
                product.setQuantity(newQty);


                this.orderProductRepo.save(orderProduct);
                return ResponseHandler.generateResponse("Successfully  save Order", HttpStatus.CREATED, totalPrice);

        }catch(ResourceNotFoundException e){
                logger.error("------------------------------------");
                logger.error(e.getMessage());
                logger.error("------------------------------------");
                return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Bad Request!!");
            }
        }



    @PutMapping("/update/order-products/{orderProductId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> updateOrderProduct(@PathVariable Long orderProductId, @RequestBody OrderProductRequestDTO orderProductRequestDTO) {
        try {
            Product product = productRepo.findById(orderProductRequestDTO.getProduct().getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
            OrderProduct orderProduct = OrderProduct.builder()
                    .productId(orderProductRequestDTO.getProduct())
                    .quantity(orderProductRequestDTO.getQuantity())
                    .build();
            Integer totalPrice = product.getRegularPrice() * orderProductRequestDTO.getQuantity();
            orderProduct.setSubtotal(totalPrice);

            orderProduct.setOrderProductId(orderProductId);
            OrderProduct responseUpdate = orderProductService.updateOrderProduct(orderProduct);
            OrderProductResponseDTO responseDTO = responseUpdate.convertToResponse();
            logger.info("==================== Logger Start Update Order Product By ID ====================");
            logger.info(responseDTO);
            logger.info("==================== Logger End Update Order Product By ID =================");
            this.orderProductRepo.save(orderProduct);
            return ResponseHandler.generateResponse("Data Updated!", HttpStatus.CREATED, responseDTO);
        } catch (ResourceNotFoundException e) {
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Bad Request");
        }
    }

    @DeleteMapping("/delete/order-products/{orderProductId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> deleteOrderProduct(@PathVariable Long orderProductId){
        try {
            orderProductService.deleteOrderProductById(orderProductId);
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