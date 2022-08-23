package com.pentagon.warungkita.service.implement;

import com.pentagon.warungkita.controller.OrderProductController;
import com.pentagon.warungkita.dto.OrderProductRequestDTO;
import com.pentagon.warungkita.dto.OrderProductResponsePOST;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.OrderProduct;
import com.pentagon.warungkita.model.Product;
import com.pentagon.warungkita.model.ProductStatus;
import com.pentagon.warungkita.repository.OrderProductRepo;
import com.pentagon.warungkita.repository.ProductRepo;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.security.service.UserDetailsImpl;
import com.pentagon.warungkita.service.OrderProductService;
import com.pentagon.warungkita.service.ProductStatusService;
import com.pentagon.warungkita.service.UsersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OrderProductImpl implements OrderProductService {

    private OrderProductRepo orderProductRepo;
    private ProductRepo productRepo;
    private ProductStatusService productStatusService;
    UsersService usersService;
    private static final Logger logger = LogManager.getLogger(OrderProductController.class);

    @Override
    public List<OrderProduct> getAll() {
        List<OrderProduct> orderProductList = this.orderProductRepo.findAll();
        if (orderProductList.isEmpty()) {
            log.error("No order found");
            throw new ResourceNotFoundException("No order found");
        }
        return this.orderProductRepo.findAll();
    }

    @Override
    public Optional<OrderProduct> getOrderProductById(Long id) throws ResourceNotFoundException {
        Optional<OrderProduct> optionalOrderProduct = this.orderProductRepo.findById(id);
        if (optionalOrderProduct.isEmpty()) {
            log.error("No order found");
            throw new ResourceNotFoundException("Order not found with id " + id);
        }
        return this.orderProductRepo.findById(id);
    }

//    @Override
//    public OrderProduct saveOrderProduct(OrderProduct orderProduct) {
//        return this.orderProductRepo.save(orderProduct);
//    }

    @Override
    public ResponseEntity<Object> saveOrderProduct(@RequestBody OrderProductRequestDTO orderProductRequestDTO) {
        try {


            /**
             * Logic subtotal on Order Product
             * */
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Optional <Product> product1 = productRepo.findById(orderProductRequestDTO.getProduct().getProductId());
            if(product1.get().getUsers().getUserId().equals(userDetails.getUserId()) ){
                throw new ResourceNotFoundException("Can't add your own product");
            }
            if(orderProductRequestDTO.getQuantity()==0){
                throw new ResourceNotFoundException("Quantity can't 0");
            }


            Product product = productRepo.findById(orderProductRequestDTO.getProduct().getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
            OrderProduct orderProduct = OrderProduct.builder()
                    .productId(orderProductRequestDTO.getProduct())
                    .quantity(orderProductRequestDTO.getQuantity())
                    .build();
            Integer totalPrice = product.getRegularPrice() * orderProductRequestDTO.getQuantity();
            orderProduct.setSubtotal(totalPrice);

            /**
             * Logic if Qty of Product less then Qty of Order Product
             * */
            if(product.getQuantity() < orderProduct.getQuantity()){
                throw new ResourceNotFoundException("stok kurang");
            }
            /**
             * Update Qty Product
             * */
            Integer newQty = product.getQuantity() - orderProductRequestDTO.getQuantity();
            product.setQuantity(newQty);
            /**
             * Update if qty product 0 set to Sold Out
             * */
            if(newQty==0){
                ProductStatus psSoldOut = productStatusService.getProductStatusBy(2L).get();
                product.setProductStatusId(psSoldOut);
            }

            this.orderProductRepo.save(orderProduct);

            OrderProductResponsePOST orderProductResponsePOST = orderProduct.convertToResponsePOST();
            return ResponseHandler.generateResponse("Successfully  save Order", HttpStatus.CREATED, orderProductResponsePOST);


        }catch(ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Bad Request!!");
        }
    }

    @Override
    public OrderProduct updateOrderProduct(OrderProduct orderProduct) {
        Optional<OrderProduct> updatedOrder = this.orderProductRepo.findById(orderProduct.getOrderProductId());
        if (updatedOrder.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with id " + orderProduct.getOrderProductId());
        }
        return this.orderProductRepo.save(orderProduct);
    }

    @Override
    public void deleteOrderProductById(Long id) {
        Optional<OrderProduct> updatedOrder = this.orderProductRepo.findById(id);
        if (updatedOrder.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with id " + id);
        }
        OrderProduct orderProduct = orderProductRepo.getReferenceById(id);
        this.orderProductRepo.delete(orderProduct);
    }
}
