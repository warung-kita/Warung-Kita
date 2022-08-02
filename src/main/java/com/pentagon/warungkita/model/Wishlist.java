package com.pentagon.warungkita.model;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "wishlist")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishListId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users users;

    @OneToMany
    @JoinColumn(name = "productId")
    private Product product;

    @Override
    public String toString() {
        return "Wishlist{" +
                "wishListId=" + wishListId +
                ", users=" + users +
                ", product=" + product +
                '}';
    }
}
