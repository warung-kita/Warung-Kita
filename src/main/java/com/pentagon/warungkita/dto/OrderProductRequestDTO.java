package com.pentagon.warungkita.dto;

import com.pentagon.warungkita.model.Order;
import com.pentagon.warungkita.model.OrderProduct;
import com.pentagon.warungkita.model.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductRequestDTO {
    private Long orderProductId;
    private Product productId;
    private Integer subtotal;

    public OrderProduct convertToEntity(){
        return OrderProduct.builder()
                .orderProductId(this.orderProductId)
                .productId(this.productId)
                .subtotal(this.subtotal)
                .build();
    }
}
