package com.pentagon.warungkita.dto;

import com.pentagon.warungkita.model.Order;
import com.pentagon.warungkita.model.Payment;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestDTO {
    private Order order;
    private LocalDate datePay;
    private BigDecimal amount;
    private String ccNum;
    private String ccType;
    private String response;

    public Payment convertToEntity(){
        return Payment.builder()
                .order(this.order)
                .datePay(this.datePay)
                .amount(this.amount)
                .ccNum(this.ccNum)
                .ccType(this.ccType)
                .response(this.response)
                .build();
    }
}
