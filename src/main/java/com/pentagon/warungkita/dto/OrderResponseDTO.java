package com.pentagon.warungkita.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pentagon.warungkita.model.OrderProduct;
import com.pentagon.warungkita.model.Users;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long orderId;
    private List<OrderProductResponseDTO> orderProductId;
    //    private List<Long> orderProductId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;
    private String ekspedisiName;

    private Number total;
    private UsersResponsePOST user;
    //    private String fullName;
//    private String email;
//    private String userName;
//    private String address;
//    private String phoneNum;
//    private String profilPicture;
    public void setOrderProductId (List<OrderProduct> orderProduct) {
        List<OrderProductResponseDTO> responseDTOs = new ArrayList<>();
        this.orderProductId = responseDTOs;
        orderProduct.forEach(orderProducts -> {
            OrderProductResponseDTO orderProductResponseDTO = new OrderProductResponseDTO();
            orderProductResponseDTO.setOrderProductId(orderProducts.getOrderProductId());
            orderProductResponseDTO.setProductId(orderProducts.getProductId().getProductId());
            orderProductResponseDTO.setQuantity(orderProducts.getQuantity());
            orderProductResponseDTO.setSubtotal(orderProducts.getSubtotal());
            responseDTOs.add(orderProductResponseDTO);
        });


    }

    public void setUser (Users users){
        UsersResponsePOST usersResponsePOST = new UsersResponsePOST();
        this.user = usersResponsePOST;
        this.user.setEmail(users.getEmail());
        this.user.setUsername(users.getUsername());
    }

}