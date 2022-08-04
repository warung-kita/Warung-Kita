package com.pentagon.warungkita.DTO;

import com.pentagon.warungkita.model.Order;
import com.pentagon.warungkita.model.OrderProduct;
import com.pentagon.warungkita.model.Product;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductRequestDTO {
    private Long orderProductId;
    private Order orderId;
    private Product productId;
    private String sku;
    private String productName;
    private String description;
    private Integer price;
    private Integer quantity;
    private Integer subtotal;

    public OrderProduct convertToEntity(){
        return OrderProduct.builder()
                .orderProductId(this.orderProductId)
                .orderId(this.orderId)
                .productId(this.productId)
                .sku(this.sku)
                .productName(this.productName)
                .description(this.description)
                .price(this.price)
                .quantity(this.quantity)
                .subtotal(this.subtotal)
                .build();
    }
}
