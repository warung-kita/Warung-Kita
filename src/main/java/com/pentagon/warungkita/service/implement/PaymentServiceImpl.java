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
    public Optional<Payment> getPaymentById(Long Id) {
        return Optional.empty();
    }

    @Override
    public Payment createPayment(Payment payment) {
        return null;
    }

    @Override
    public void deletePaymentById(Long Id) {

    }

    @Override
    public Payment updatePayment(Payment payment) {
        return null;
    }

    @Override
    public Payment getReferenceById(Long Id) {
        return null;
    }
}
