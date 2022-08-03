package com.pentagon.warungkita.dto;

import com.pentagon.warungkita.model.Categories;
import com.pentagon.warungkita.model.Product;
import com.pentagon.warungkita.model.ProdukStatus;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDTO {

    private String sku;
    private String productName;
    private String description;
    private ProdukStatus productStatusId;
    private Integer regularPrice;
    private Integer quantity;
    private String productPicture;
    private List<Categories> categories;

    public Product convertToEntity(){
        return Product.builder()
                .sku(this.sku)
                .productName(this.productName)
                .description(this.description)
                .productStatusId(this.productStatusId)
                .regularPrice(this.regularPrice)
                .productPicture(this.productPicture)
                .categories(this.categories)
                .build();
    }
}
