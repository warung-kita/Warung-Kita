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
    private Long orderId;
    private String sku;
    private String productName;
    private String description;
    private Number price;
    private Integer quantity;
    private Number subtotal;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;
    private String ekspedisiName;
    private String fullName;
    private String email;
    private String userName;
    private String address;
    private String phoneNum;
    private String profilPicture;


    @Override
    public String toString() {
        return "OrderProductResponseDTO{" +
                "orderProductId=" + orderProductId +
                ", productId=" + productId +
                ", orderId=" + orderId +
                ", sku='" + sku + '\'' +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                ", orderDate=" + orderDate +
                ", ekspedisiName='" + ekspedisiName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", profilPicture='" + profilPicture + '\'' +
                '}';
    }
}
