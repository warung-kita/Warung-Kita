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
    private SalesOrder salesOrder;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "transdate")
    private LocalDate datePay;

    @Column(name = "processor")
    private String processor;

    @Column(name = "processor_trans_id")
    private String processorTransId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "cc_num")
    private String ccNum;

    @Column(name = "cc_type")
    private String ccType;

    @Column(name = "response")
    private String response;

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", salesOrder=" + salesOrder +
                ", datePay=" + datePay +
                ", processor='" + processor + '\'' +
                ", processorTransId='" + processorTransId + '\'' +
                ", amount=" + amount +
                ", ccNum='" + ccNum + '\'' +
                ", ccType='" + ccType + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}


