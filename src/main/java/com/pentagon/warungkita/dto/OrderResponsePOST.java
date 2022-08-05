package com.pentagon.warungkita.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponsePOST {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;
    private Long ekspedisiId;
    private Number total;
    private Long userId;

    @Override
    public String toString() {
        return "OrderResponsePOST{" +
                "orderDate=" + orderDate +
                ", ekspedisiId=" + ekspedisiId +
                ", total=" + total +
                ", userId=" + userId +
                '}';
    }
}
