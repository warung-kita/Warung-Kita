package com.pentagon.warungkita.model;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.LAZY;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String sku;

    private String productName;

    private String description;


    @ManyToOne
    @JoinColumn(name = "product_status_id")
    private ProductStatus productStatus;


    private Integer regularPrice;

    private Integer quantity;

    private String productPicture;

    @ManyToMany(fetch = LAZY)
    private Collection<Categories> categories = new ArrayList<>();

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", sku='" + sku + '\'' +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", productStatus=" + productStatus +
                ", regularPrice=" + regularPrice +
                ", quantity=" + quantity +
                ", productPicture='" + productPicture + '\'' +
                ", categories=" + categories +
                '}';
    }
}
