package com.pentagon.warungkita.DTO;

import com.pentagon.warungkita.model.Ekspedisi;
import com.pentagon.warungkita.model.Order;
import com.pentagon.warungkita.model.Users;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDTO {
    private Long orderId;
    private Date orderDate;
    private Ekspedisi ekspedisiId;
    private Integer total;
    private Users userId;

    public Order convertToEntity(){
        return Order.builder()
                .orderId(this.orderId)
                .orderDate(this.orderDate)
                .ekspedisiId(this.ekspedisiId)
                .total(this.total)
                .userId(this.userId)
                .build();
    }
}
