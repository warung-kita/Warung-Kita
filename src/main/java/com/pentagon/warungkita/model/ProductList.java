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
    private Users user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductListResponseDTO convertToResponse(){
       return ProductListResponseDTO.builder()
               .product(this.getProduct())
//               .sku(this.getProduct().getSku())
//               .nama(this.getProduct().getProductName())
//               .deskripsi(this.getProduct().getDescription())
//               .status(this.getProduct().getProductStatusId().getProductStatusId())
//               .harga(this.getProduct().getRegularPrice())
//               .jumlah(this.getProduct().getQuantity())
//               .gambarProduct(this.getProduct().getProductPicture())
               .namaUser(this.getUser().getFullName())
               .alamat(this.getUser().getAddress())
               .nomorHandphone(this.getUser().getPhoneNum())
               .build();
    }

    public ProductListResponsePOST convertToResponsePost(){
        return ProductListResponsePOST.builder()
                .user_id(this.getUser().getUserId())
                .product_id(this.getProduct().getProductId())
                .build();
    }



   @Override
   public String toString() {
      return "ProductList{" +
              "productListId=" + productListId +
              ", user=" + user +
              ", product=" + product +
              '}';
   }
}
