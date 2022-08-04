package com.pentagon.warungkita.model;

import com.pentagon.warungkita.DTO.OrderProductResponseDTO;
import com.pentagon.warungkita.DTO.OrderProductResponsePOST;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "order_products")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order orderId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;
    private String sku;
    private String productName;
    private String description;
    private Integer price;
    private Integer quantity;
    private Integer subtotal;


    public OrderProductResponseDTO convertToResponse(){
        return OrderProductResponseDTO.builder()
                .orderProductId(this.orderProductId)
                .productId(this.productId.getProductId())
                .orderId(this.orderId.getOrderId())
                .sku(this.sku)
                .productName(this.productName)
                .description(this.description)
                .price(this.price)
                .quantity(this.quantity)
                .subtotal(this.subtotal)
                .orderDate(this.orderId.getOrderDate())
                .ekspedisiName(this.orderId.getEkspedisiId().getName())
                .fullName(this.orderId.getUserId().getFullName())
                .email(this.orderId.getUserId().getEmail())
                .userName(this.orderId.getUserId().getUsername())
                .address(this.orderId.getUserId().getAddress())
                .phoneNum(this.orderId.getUserId().getPhoneNum())
                .profilPicture(this.orderId.getUserId().getProfilPicture())
                .build();
    }

    public OrderProductResponsePOST convertToResponsePOST(){
        return OrderProductResponsePOST.builder()
                .orderId(this.orderId.getOrderId())
                .productId(this.productId.getProductId())
                .sku(this.sku)
                .productName(this.productName)
                .description(this.description)
                .price(this.price)
                .quantity(this.quantity)
                .subtotal(this.subtotal)
                .build();
    }
    @Override
    public String toString() {
        return "OrderProduct{" +
                "orderProductId=" + orderProductId +
                ", orderId=" + orderId +
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
