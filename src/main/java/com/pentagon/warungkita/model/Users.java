package com.pentagon.warungkita.model;

import com.pentagon.warungkita.dto.UsersResponseDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String fullName;
    private String username;
    private String password;
    private String address;
    private String profilPicture;
    private String phoneNum;
    private boolean active;
    @ManyToMany
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn (name = "user_id"),
    inverseJoinColumns = @JoinColumn (name = "role_id" ))

    private List<Roles> roles;

    public UsersResponseDTO convertToResponse(){
        return UsersResponseDTO.builder()
                .role(this.getRoles())
                .id_akun(this.userId)
                .nama_lengkap(this.fullName)
                .nama(this.username)
                .alamat(this.address)
                .sandi(this.password)
                .nomor_tlp(this.phoneNum)
                .foto(this.profilPicture)
                .status(this.active)
                .build();
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", profilPicture='" + profilPicture + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                '}';
    }
}
