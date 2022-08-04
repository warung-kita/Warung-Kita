package com.pentagon.warungkita.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pentagon.warungkita.model.Ekspedisi;
import com.pentagon.warungkita.model.Users;
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

}
