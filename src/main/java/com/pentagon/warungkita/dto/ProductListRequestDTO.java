package com.pentagon.warungkita.dto;

import com.pentagon.warungkita.model.Product;
import com.pentagon.warungkita.model.ProductList;
import com.pentagon.warungkita.model.Users;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListRequestDTO {
    private Users user;
    private Product product;

    public ProductList convertToEntity(){
        return ProductList.builder()
                .user(this.user)
                .product(this.product)
                .build();

    }

}
