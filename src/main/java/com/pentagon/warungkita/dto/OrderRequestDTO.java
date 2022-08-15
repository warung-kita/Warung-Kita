package com.pentagon.warungkita.dto;

import com.pentagon.warungkita.model.Ekspedisi;
import com.pentagon.warungkita.model.Order;
import com.pentagon.warungkita.model.OrderProduct;
import com.pentagon.warungkita.model.Users;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDTO {
    private Long orderId;
    private List<OrderProduct> orderProduct;
    private Date orderDate;
    private Ekspedisi ekspedisiId;
    private Integer total;
    private Users userId;

    public Order convertToEntity(){
        return Order.builder()
//                .orderId(this.orderId)
                .orderProduct(this.orderProduct)
                .orderDate(this.orderDate)
                .ekspedisiId(this.ekspedisiId)
                .total(this.total)
                .userId(this.userId)
                .build();
    }
}
