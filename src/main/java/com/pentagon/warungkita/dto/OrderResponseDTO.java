package com.pentagon.warungkita.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long orderId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;
    private String ekspedisiName;
    private Number total;
    private String fullName;
    private String email;
    private String userName;
    private String address;
    private String phoneNum;
    private String profilPicture;

    @Override
    public String toString() {
        return "OrderResponseDTO{" +
                "orderId=" + orderId +
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