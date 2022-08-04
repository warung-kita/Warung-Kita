package com.pentagon.warungkita.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductResponsePOST {
    private Long orderId;
    private Long productId;
    private String sku;
    private String productName;
    private String description;
    private Integer price;
    private Integer quantity;
    private Integer subtotal;


    @Override
    public String toString() {
        return "OrderProductResponsePOST{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", sku='" + sku + '\'' +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                '}';
    }
}
