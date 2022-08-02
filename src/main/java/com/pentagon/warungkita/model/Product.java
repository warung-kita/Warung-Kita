package com.pentagon.warungkita.model;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long productId;

    @Column(name = "sku")
    private String sku;

    @Column(name = "name")
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "product_status_id")
    private Integer productStatusId;

    @Column(name = "regular_price")
    private Integer regularPrice;

    @Column(name = "quantity")
    private Integer quantity;

}
