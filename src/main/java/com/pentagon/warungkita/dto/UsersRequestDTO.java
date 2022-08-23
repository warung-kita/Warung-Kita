package com.pentagon.warungkita.dto;

import com.pentagon.warungkita.model.Roles;
import com.pentagon.warungkita.model.Users;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersRequestDTO {

    private String fullName;
    private String username;
    private String email;
    private String address;
    private String password;
    private String phoneNum;
    private String profilPicture;

    private List<Roles> role;


    public Users convertToEntity(){
        return Users.builder()
                .fullName(this.fullName)
                .username(this.username)
                .email(this.email)
                .address(this.address)
                .password(this.password)
                .phoneNum(this.phoneNum)
                .profilPicture(this.profilPicture)
                .roles(this.role)
                .build();
    }
}
