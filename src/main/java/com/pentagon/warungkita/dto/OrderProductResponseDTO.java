package com.pentagon.warungkita.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductResponseDTO {
    private Long orderProductId;
    private Long productId;
    private  Integer quantity;
    private Integer subtotal;

    @Override
    public String toString() {
        return "OrderProductResponseDTO{" +
                "orderProductId=" + orderProductId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                '}';
    }
}
