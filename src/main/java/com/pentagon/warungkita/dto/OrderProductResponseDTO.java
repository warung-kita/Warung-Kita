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
//    private Long order;
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private Date orderDate;
//    private String ekspedisiName;
//    private String fullName;
//    private String email;
//    private String userName;
//    private String address;
//    private String phoneNum;
//    private String profilPicture;

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
