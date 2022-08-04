package com.pentagon.warungkita.service;

import com.pentagon.warungkita.model.Payment;
import com.pentagon.warungkita.model.ProductList;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    List<Payment> getAllPayment();
    Optional<Payment> getPaymentById(Long Id);
    Payment createPayment(Payment payment);
    void deletePaymentById(Long Id);
    Payment updatePayment(Payment payment);
    Payment getReferenceById (Long Id);
}
