package com.pentagon.warungkita.model;

import com.pentagon.warungkita.dto.OrderResponseDTO;
import com.pentagon.warungkita.dto.OrderResponsePOST;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;
    @ManyToOne
    @JoinColumn(name = "ekspedisi_id")

    private Ekspedisi ekspedisiId;
    private Integer total;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userId;

    public OrderResponseDTO convertToResponse(){
        return OrderResponseDTO.builder()
                .orderId(this.orderId)
                .orderDate(this.orderDate)
                .ekspedisiName(this.ekspedisiId.getName())
                .total(this.total)
                .fullName(this.userId.getFullName())
                .userName(this.userId.getUsername())
                .email(this.userId.getEmail())
                .address(this.userId.getAddress())
                .phoneNum(this.userId.getPhoneNum())
                .profilPicture(this.userId.getProfilPicture())
                .build();
    }

    public OrderResponsePOST convertToResponsePOST(){
        return OrderResponsePOST.builder()
                .orderDate(this.orderDate)
                .ekspedisiId(this.ekspedisiId.getEkspedisiId())
                .total(this.total)
                .userId(this.userId.getUserId())
                .build();
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", ekspedisiId=" + ekspedisiId +
                ", total=" + total +
                ", userId=" + userId +
                '}';
    }
}
