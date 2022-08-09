package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.PaymentRequestDTO;
import com.pentagon.warungkita.dto.PaymentResponseDTO;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Payment;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.service.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/pentagon/warung-kita")
@AllArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "9.Payment")
public class PaymentController {

    private static final Logger logger = LogManager.getLogger(PaymentController.class);
    private PaymentService paymentService;

    @GetMapping("/payment/all")
    public ResponseEntity<Object> findAll(){
        try{
            List<Payment> payments = paymentService.getAllPayment();
            List<PaymentResponseDTO> paymentsList = new ArrayList<>();
            logger.info("==================== Logger Start Get All Payment ====================");
            for(Payment dataresult:payments){
                PaymentResponseDTO paymentResponseDTO = dataresult.convertToResponse();
                paymentsList.add(paymentResponseDTO);
                logger.info("code     :"+dataresult.getPaymentId());
                logger.info("Order Id :"+dataresult.getOrder().getOrderId() );
                logger.info("Amount   :"+dataresult.getAmount() );
                logger.info("Date     :"+dataresult.getDatePay() );
                logger.info("------------------------------------");
            }
            logger.info("==================== Logger End  ====================");
            return ResponseHandler.generateResponse("Succes Get All", HttpStatus.OK,paymentsList);
        }catch (ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Table has no value");

        }
    }
    @GetMapping("/payment/{id}")
    public ResponseEntity<Object> getPaymentById(@PathVariable Long id){
        try {
            Optional<Payment> payment = paymentService.getPaymentById(id);
            Payment paymentget = payment.get();
            PaymentResponseDTO result = paymentget.convertToResponse();
            logger.info("======== Logger Start Find Product List with ID "+id+ "  ========");
            logger.info("code     :"+paymentget.getPaymentId());
            logger.info("Order Id :"+paymentget.getOrder().getOrderId() );
            logger.info("Amount   :"+paymentget.getAmount() );
            logger.info("Date     :"+paymentget.getDatePay() );
            logger.info("==================== Logger End =================");
            return ResponseHandler.generateResponse("Success Get By Id",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }
    @PostMapping("/payment/create")
    public ResponseEntity<Object> paymentCreate(@RequestBody PaymentRequestDTO paymentRequestDTO){
        try{
            if(paymentRequestDTO.getOrder() == null ){
                throw new ResourceNotFoundException("Payment must have order id");
            }
            Payment payment = paymentRequestDTO.convertToEntity();
            paymentService.createPayment(payment);
            PaymentResponseDTO result = payment.convertToResponse();
            logger.info("======== Logger Start   ========");
            logger.info("code     :"+payment.getPaymentId());
            logger.info("Order Id :"+payment.getOrder().getOrderId() );
            logger.info("Amount   :"+payment.getAmount() );
            logger.info("Date     :"+payment.getDatePay() );
            logger.info("=========  Logger End ============");
            return ResponseHandler.generateResponse("Success Create Payment",HttpStatus.CREATED,result);
        }catch (Exception e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Failed Create Database");
        }
    }
    @PutMapping("/payment/update/{id}")
    public ResponseEntity<Object> paymentUpdate(@PathVariable Long id, @RequestBody PaymentRequestDTO paymentRequestDTO){
        try {
            if(paymentRequestDTO.getOrder() == null ){
                throw new ResourceNotFoundException("Payment must have order id");
            }
            Payment payment = paymentRequestDTO.convertToEntity();
            payment.setPaymentId(id);
            Payment updatePayment = paymentService.updatePayment(payment);
            PaymentResponseDTO results = updatePayment.convertToResponse();
            logger.info("======== Logger Start   ========");
            logger.info("code     :"+payment.getPaymentId());
            logger.info("Order Id :"+payment.getOrder().getOrderId() );
            logger.info("Amount   :"+payment.getAmount() );
            logger.info("Date     :"+payment.getDatePay() );
            logger.info("=========  Logger End ============");
            return ResponseHandler.generateResponse("Success Update Payment",HttpStatus.CREATED,results);
        }catch (Exception e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Bad Request");
        }
    }
    @DeleteMapping("payment/delete/{id}")
    public ResponseEntity<Object> deletePayment(@PathVariable Long id){
        try {
            paymentService.deletePaymentById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            logger.info("======== Logger Start   ========");
            logger.info("Payment deleted " + response);
            logger.info("======== Logger End   ==========");
            return ResponseHandler.generateResponse("Success Delete Payment by ID",HttpStatus.OK,response);
        }catch(ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }
}
