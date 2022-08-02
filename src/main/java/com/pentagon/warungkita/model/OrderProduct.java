package com.pentagon.warungkita.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "order_product")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;

    @ManyToOne
    @JoinColumn(name = "id")
    private Order orderId;

    @OneToMany
    @JoinColumn(name = "id")
    private List<Product> products = new ArrayList<>();

    private String sku;
    private String productName;
    private String description;
    private Number price;
    private Integer quantity;
    private Number subtotal;

    @Override
    public String toString() {
        return "OrderProduct{" +
                "orderProductId=" + orderProductId +
                ", orderId=" + orderId +
                ", sku='" + sku + '\'' +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                '}';
    }
}
