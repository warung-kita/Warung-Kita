package com.pentagon.warungkita.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductResponsePOST {
    private Long productId;
    private Integer subtotal;

    @Override
    public String toString() {
        return "OrderProductResponsePOST{" +
                "productId=" + productId +
                ", subtotal=" + subtotal +
                '}';
    }
}
