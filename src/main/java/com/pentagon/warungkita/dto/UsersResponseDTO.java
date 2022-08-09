package com.pentagon.warungkita.dto;

import com.pentagon.warungkita.model.Roles;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersResponseDTO {

    private List<Roles> role;
    private Long userId;
    private String fullName;
    private String email;
    private String username;
    private String password;
    private String address;
    private String profilPicture;
    private String phoneNum;
    private boolean active;

    @Override
    public String toString() {
        return "UsersResponseDTO{" +
                "role=" + role +
                ", userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", profilPicture='" + profilPicture + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", active=" + active +
                '}';
    }
}
