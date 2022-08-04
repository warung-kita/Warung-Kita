package com.pentagon.warungkita.service.implement;

import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Payment;
import com.pentagon.warungkita.repository.PaymentRepo;
import com.pentagon.warungkita.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepo paymentRepo;

    @Override
    public List<Payment> getAllPayment() {
        List<Payment> optionalPayment = paymentRepo.findAll();
        if(optionalPayment.isEmpty()){
            throw new ResourceNotFoundException("Data is Empty");
        }
        return this.paymentRepo.findAll();
    }

    @Override
    public Optional<Payment> getPaymentById(Long Id) throws ResourceNotFoundException {

        Optional<Payment> optionalPayment = paymentRepo.findById(Id);
        if(optionalPayment.isEmpty()){
            throw new ResourceNotFoundException("Booking not exist with id " + Id);
        }
        return this.paymentRepo.findById(Id);
    }

    @Override
    public Payment createPayment(Payment payment) {

        return this.paymentRepo.save(payment);
    }

    @Override
    public void deletePaymentById(Long Id) throws ResourceNotFoundException{
        Optional<Payment> optionalPayment = paymentRepo.findById(Id);
        if(optionalPayment.isEmpty()){
            throw new ResourceNotFoundException("Payment not exist with id " + Id);
        }
        Payment payment = paymentRepo.getReferenceById(Id);
        this.paymentRepo.delete(payment);
    }

    @Override
    public Payment updatePayment(Payment payment)throws ResourceNotFoundException {
        Optional<Payment> optionalPayment = paymentRepo.findById(payment.getPaymentId());
        if(optionalPayment.isEmpty()){
            throw new ResourceNotFoundException("Booking not exist with id " + payment.getPaymentId());
        }
        return this.paymentRepo.save(payment);
    }

    @Override
    public Payment getReferenceById(Long Id) {
        return null;
    }
}
