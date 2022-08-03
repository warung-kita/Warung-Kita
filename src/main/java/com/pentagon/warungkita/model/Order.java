package com.pentagon.warungkita.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Date orderDate;

    @ManyToOne
    @JoinColumn(name = "ekspedisi_id")
    private Ekspedisi ekspedisi;

    private Integer totalOrder;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userId;

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", ekspedisi=" + ekspedisi +
                ", totalOrder=" + totalOrder +
                ", userId=" + userId +
                '}';
    }
}
