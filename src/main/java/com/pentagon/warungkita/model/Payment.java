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
@Table(name = "cc_transactions")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Sales_order sales_order;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "transdate")
    private LocalDate datePay;

    @Column(name = "processor")
    private String processor;

    @Column(name = "processor_trans_id")
    private String processor_trans_id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "cc_num")
    private String cc_num;

    @Column(name = "cc_type")
    private String cc_type;

    @Column(name = "response")
    private String response;


    }
}
