package com.pentagon.warungkita.dto;

import com.pentagon.warungkita.model.Users;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersRequestDTO {

    private String role;
    private Long userId;
    private String fullName;
    private String username;
    private String address;
    private String password;
    private String phoneNum;
    private String profilPicture;
    private String active;

    public Users convertToEntity(){
        return Users.builder()
//                .roles(this.role)
                .userId(this.userId)
                .fullName(this.fullName)
                .username(this.username)
                .address(this.address)
                .password(this.password)
                .phoneNum(this.phoneNum)
                .profilPicture(this.profilPicture)
//                .active(this.active)
                .build();
    }
}
