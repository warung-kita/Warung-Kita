package com.pentagon.warungkita.model;


import com.pentagon.warungkita.dto.ProductListResponseDTO;
import com.pentagon.warungkita.dto.ProductListResponsePOST;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder

@Table(name = "product_list")
public class ProductList {
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productListId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductListResponseDTO convertToResponse(){
       return ProductListResponseDTO.builder()
               .sku(this.getProduct().getSku())
               .nama(this.getProduct().getProductName())
               .deskripsi(this.getProduct().getDescription())
               .status(this.getProduct().getProductStatus().getProductStatusId())
               .harga(this.getProduct().getRegularPrice())
               .jumlah(this.getProduct().getQuantity())
               .gambarProduct(this.getProduct().getProductPicture())
               .namaUser(this.getUsers().getFullName())
               .alamat(this.getUsers().getAddress())
               .nomorHandphone(this.getUsers().getPhoneNum())
               .build();
    }

    public ProductListResponsePOST convertToResponsePost(){
        return ProductListResponsePOST.builder()
                .sku(this.getProduct().getSku())
                .nama(this.getProduct().getProductName())
                .deskripsi(this.getProduct().getDescription())
                .status(this.getProduct().getProductStatus().getProductStatusId())
                .harga(this.getProduct().getRegularPrice())
                .jumlah(this.getProduct().getQuantity())
                .gambarProduct(this.getProduct().getProductPicture())
                .build();
    }



   @Override
   public String toString() {
      return "ProductList{" +
              "productListId=" + productListId +
              ", users=" + users +
              ", product=" + product +
              '}';
   }
}
