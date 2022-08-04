package com.pentagon.warungkita.DTO;

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
}
