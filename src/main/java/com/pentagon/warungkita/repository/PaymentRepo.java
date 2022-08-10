package com.pentagon.warungkita.repository;

import com.pentagon.warungkita.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
    public List<Payment> findByOrderUserIdUsernameContaining(String userName);

}
