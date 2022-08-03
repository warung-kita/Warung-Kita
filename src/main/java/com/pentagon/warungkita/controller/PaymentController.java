package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.*;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Payment;
import com.pentagon.warungkita.model.ProductList;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.service.PaymentService;
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
public class PaymentController {

    private PaymentService paymentService;

    @GetMapping("/payment/all")
    public ResponseEntity<Object> findAll(){
        try{
            List<Payment> payments = paymentService.getAllPayment();
            List<PaymentResponseDTO> paymentsList = new ArrayList<>();
            for(Payment dataresult:payments){
                PaymentResponseDTO paymentResponseDTO = dataresult.convertToResponse();
                paymentsList.add(paymentResponseDTO);
            }
            return ResponseHandler.generateResponse("Succes Get All", HttpStatus.OK,paymentsList);
        }catch (ResourceNotFoundException e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Table has no value");

        }
    }
    @GetMapping("/payment/{id}")
    public ResponseEntity<Object> getPaymentById(@PathVariable Long id){
        try {
            Optional<Payment> payment = paymentService.getPaymentById(id);
            Payment paymentget = payment.get();
            PaymentResponseDTO result = paymentget.convertToResponse();
            return ResponseHandler.generateResponse("Success Get By Id",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }
    @PostMapping("/payment/create")
    public ResponseEntity<Object> paymentCreate(@RequestBody PaymentRequestDTO paymentRequestDTO){
        try{
            if(paymentRequestDTO.getOrder() == null ){
                throw new ResourceNotFoundException("Product List must have product id and user id");
            }
            Payment payment = paymentRequestDTO.convertToEntity();
            paymentService.createPayment(payment);
            PaymentResponseDTO result = payment.convertToResponse();
            return ResponseHandler.generateResponse("Success Create Product List",HttpStatus.CREATED,result);
        }catch (Exception e){

            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Failed Create Database");
        }
    }
    @PutMapping("/payment/update/{id}")
    public ResponseEntity<Object> paymentUpdate(@PathVariable Long id, @RequestBody PaymentRequestDTO paymentRequestDTO){
        try {
            if(paymentRequestDTO.getOrder() == null ){
                throw new ResourceNotFoundException("Product List must have product id and user id");
            }
            Payment payment = paymentRequestDTO.convertToEntity();
            payment.setPaymentId(id);
            Payment updatePayment = paymentService.updatePayment(payment);
            PaymentResponseDTO results = updatePayment.convertToResponse();
            return ResponseHandler.generateResponse("Success Update Booking",HttpStatus.CREATED,results);
        }catch (Exception e){

            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Bad Request");
        }
    }
    @DeleteMapping("payment/delete/{id}")
    public ResponseEntity<Object> deletePayment(@PathVariable Long id){
        try {
            paymentService.deletePaymentById(id);
            Boolean result = Boolean.TRUE;
            return ResponseHandler.generateResponse("Success Delete Booking by ID",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }
}
