package com.pentagon.warungkita.dto;

import com.pentagon.warungkita.model.Categories;
import com.pentagon.warungkita.model.Photo;
import com.pentagon.warungkita.model.Product;
import com.pentagon.warungkita.model.ProductStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDTO {
    private String sku;
    private String productName;
    private List<Categories> categories;
    private String description;
    private ProductStatus productStatusId;
    private Integer regularPrice;
    private Integer quantity;
    private List<Photo> productPicture;

    public Product convertToEntity(){
        return Product.builder()
                .sku(this.sku)
                .productName(this.productName)
                .categories(this.categories)
                .description(this.description)
                .productStatusId(this.productStatusId)
                .regularPrice(this.regularPrice)
                .quantity(this.quantity)
                .productPicture(this.productPicture)
                .build();
    }
}
