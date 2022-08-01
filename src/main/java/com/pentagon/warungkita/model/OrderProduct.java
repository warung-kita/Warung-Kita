package com.pentagon.warungkita.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;
    private Long orderId;
    private String sku;
    private String productName;
    private String description;
    private Number price;
    private Integer quantity;
    private Number subtotal;
}
