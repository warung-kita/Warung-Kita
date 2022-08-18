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
    private String fullName;
    private String email;
    private String username;
    private String address;
    private String profilPicture;
    private String phoneNum;

    @Override
    public String toString() {
        return "UsersResponseDTO{" +
                "role=" + role +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", profilPicture='" + profilPicture + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }
}
