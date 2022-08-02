package com.pentagon.warungkita.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;

    @ManyToOne
    @JoinColumn(name = "id")
    private Order orderId;

    @ManyToOne
    @JoinColumn(name = "id")
    private Product product;

    private String sku;
    private String productName;
    private Number price;
    private Integer quantity;
    private Number subtotal;


}
