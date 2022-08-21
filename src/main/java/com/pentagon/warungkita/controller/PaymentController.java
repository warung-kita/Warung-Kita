package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.PaymentRequestDTO;
import com.pentagon.warungkita.service.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pentagon/warung-kita")
@AllArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "09.Payment")
public class PaymentController {

    private PaymentService paymentService;

    @GetMapping("/payment/buyer_histori")
    @PreAuthorize("hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> buyerHistoriPayment() {
        return paymentService.findByOrderUserIdUsernameContaining();
        }

    @GetMapping("/payment/seller_histori")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Object> sellerHistoriPayment() {
        return paymentService.historiSeller();
    }

    @GetMapping("/payment/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> findAll(){
       return paymentService.getAllPayment();
        }

    @GetMapping("/payment/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> getPaymentById(@PathVariable Long id){
        return paymentService.getPaymentById(id);
        }

    @PostMapping("/payment/create")
    @PreAuthorize("hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> paymentCreate(@RequestBody PaymentRequestDTO paymentRequestDTO){
        return paymentService.createPayment(paymentRequestDTO);
        }

    @PutMapping("/payment/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> paymentUpdate(@PathVariable Long id, @RequestBody PaymentRequestDTO paymentRequestDTO){
       return this.paymentService.updatePayment(id, paymentRequestDTO);
        }

    @DeleteMapping("payment/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> deletePayment(@PathVariable Long id){
        return paymentService.deletePaymentById(id);
        }

    @DeleteMapping("payment/cancel/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> cancelPayment(@PathVariable Long id){
        return paymentService.cancelPaymnet(id);
    }
}
