package com.pentagon.warungkita.model;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "id")
    private Order sales_order;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datePay;

    private BigDecimal amount;

    private String ccNum;

    private String ccType;

    private String response;

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", sales_order=" + sales_order +
                ", datePay=" + datePay +
                ", amount=" + amount +
                ", ccNum='" + ccNum + '\'' +
                ", ccType='" + ccType + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}

