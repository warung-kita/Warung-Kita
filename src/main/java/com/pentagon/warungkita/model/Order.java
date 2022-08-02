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

    @OneToMany
    @JoinColumn(name = "id")
    private List<Ekspedisi> ekspedisi;

    private Number totalOrder;

    @ManyToOne
    @JoinColumn(name = "id")
    private Users userId;

    @Override
    public String toString() {
        return "SalesOrder{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", ekspedisi=" + ekspedisi +
                ", totalOrder=" + totalOrder +
                ", userId=" + userId +
                '}';
    }
}
