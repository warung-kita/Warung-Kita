package com.pentagon.warungkita.service.implement;

import com.pentagon.warungkita.dto.PaymentRequestDTO;
import com.pentagon.warungkita.dto.PaymentResponseDTO;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Order;
import com.pentagon.warungkita.model.Payment;
import com.pentagon.warungkita.repository.OrderRepo;
import com.pentagon.warungkita.repository.PaymentRepo;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.security.service.UserDetailsImpl;
import com.pentagon.warungkita.service.PaymentService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.pentagon.warungkita.model.Enum.BankList.*;
import static com.pentagon.warungkita.model.Enum.PaymentResponse.PAYMENT_SUCCES;
import static com.pentagon.warungkita.model.Enum.PaymentResponse.WAITING;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepo paymentRepo;
    @Autowired
    OrderRepo orderRepo;
    private static final Logger logger = LogManager.getLogger(PaymentServiceImpl.class);

    @Override
    public List<Payment> getAllPayment() {
        List<Payment> payments = paymentRepo.findAll();
        if(payments.isEmpty()){
            throw new ResourceNotFoundException("Data is Empty");
        }
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
        return this.paymentRepo.findAll();
    }

    @Override
    public ResponseEntity<Object> getPaymentById(Long Id) throws ResourceNotFoundException {

        Optional<Payment> payment = paymentRepo.findById(Id);
        if(payment.isEmpty()){
            throw new ResourceNotFoundException("Booking not exist with id " + Id);
        }
        try {
            Payment paymentget = payment.get();
            PaymentResponseDTO result = paymentget.convertToResponse();
            logger.info("======== Logger Start Find Product List with ID "+Id+ "  ========");
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

    @Override
    public ResponseEntity<Object> createPayment(PaymentRequestDTO paymentRequestDTO) {
        try{
            if(paymentRequestDTO.getOrder() == null ){
                throw new ResourceNotFoundException("Payment must have order id");
            }
            Payment payment = paymentRequestDTO.convertToEntity();
            Optional <Order> order = orderRepo.findById(paymentRequestDTO.getOrder().getOrderId());
            Integer amount = paymentRequestDTO.getAmount();
            if(paymentRequestDTO.getNamaBank() == BANK_SYARIAH_INDONESIA){
                payment.setCcNum("023143213");
            } else if (paymentRequestDTO.getNamaBank()==BANK_BRI){
                payment.setCcNum("12342342");
            } else if (paymentRequestDTO.getNamaBank() == BANK_BNI){
                payment.setCcNum("45234234");
            }else if (paymentRequestDTO.getNamaBank()==BANK_PERMATA){
                payment.setCcNum("3423124143");
            }else if (paymentRequestDTO.getNamaBank()==BANK_BCA){
                payment.setCcNum("67547645456");
            }else {
                payment.setCcNum("8568568568");
            }
            if(order.get().getTotal().equals(amount)){
                payment.setResponse(PAYMENT_SUCCES);
            } else {
                payment.setResponse(WAITING);
                paymentRepo.save(payment);
                PaymentResponseDTO result = payment.convertToResponse();
                return ResponseHandler.generateResponse("Your Amount is not enough", HttpStatus.BAD_GATEWAY,result);
            }
            paymentRepo.save(payment);
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

    @Override
    public ResponseEntity<Object> deletePaymentById(Long id) throws ResourceNotFoundException{
        Optional<Payment> optionalPayment = paymentRepo.findById(id);
        if(optionalPayment.isEmpty()){
            throw new ResourceNotFoundException("Payment not exist with id " + id);
        }
        try {
            paymentRepo.deleteById(id);
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

    @Override
    public ResponseEntity<Object> updatePayment(Long id, PaymentRequestDTO paymentRequestDTO)throws ResourceNotFoundException {
        Optional<Payment> optionalPayment = paymentRepo.findById(id);
        if(optionalPayment.isEmpty()){
            throw new ResourceNotFoundException("Booking not exist with id " + id);
        }
        try {
            if(paymentRequestDTO.getOrder() == null ){
                throw new ResourceNotFoundException("Payment must have order id");
            }
            Payment payment = paymentRequestDTO.convertToEntity();
            payment.setPaymentId(id);
            Payment updatePayment = paymentRepo.save(payment);
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

    @Override
    public Payment getReferenceById(Long Id) {
        return null;
    }

    @Override
    public ResponseEntity<Object>  findByOrderUserIdUsernameContaining() {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Payment> payments = paymentRepo.findByOrderUserIdUsernameContaining(userDetails.getUsername());
        if(payments.isEmpty()){
            throw new ResourceNotFoundException("User not have Histori Transaksi ");
        }
        List<PaymentResponseDTO> paymentsList = new ArrayList<>();
        for(Payment dataresult:payments){
            PaymentResponseDTO paymentResponseDTO = dataresult.convertToResponse();
            paymentsList.add(paymentResponseDTO);
        }
        return ResponseHandler.generateResponse("payment",HttpStatus.OK, paymentsList);
    }
}
