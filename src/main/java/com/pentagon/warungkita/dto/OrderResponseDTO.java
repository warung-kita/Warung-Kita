package com.pentagon.warungkita.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pentagon.warungkita.model.OrderProduct;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long orderId;
    private List<OrderProduct> orderProductId;
//    private List<Long> orderProductId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;
    private String ekspedisiName;
    private Integer total;
    private String fullName;
    private String email;
    private String userName;
    private String address;
    private String phoneNum;
    private String profilPicture;

    @Override
    public String toString() {
        return "OrderResponseDTO{" +
//                "orderId=" + orderId +
                ", orderProductId=" + orderProductId +
                ", orderDate=" + orderDate +
                ", ekspedisiName='" + ekspedisiName + '\'' +
                ", total=" + total +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", profilPicture='" + profilPicture + '\'' +
                '}';
    }
}
