package com.pentagon.warungkita.model;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "sku")
    private String sku;

    @Column(name = "name")
    private String productName;

    @Column(name = "description")
    private String description;

    @OneToMany
    @Column(name = "id")
    private ProdukStatus productStatusId;

    @Column(name = "regular_price")
    private Integer regularPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToMany(fetch = EAGER)
    private Collection<Categories> categories = new ArrayList<>();

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", sku='" + sku + '\'' +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", productStatusId=" + productStatusId +
                ", regularPrice=" + regularPrice +
                ", quantity=" + quantity +
                ", categories=" + categories +
                '}';
    }
}
