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
    private Long id_akun;
    private String nama_lengkap;
    private String nama;
    private String email;
    private String alamat;
    private String sandi;
    private String nomor_tlp;
    private String foto;
    private boolean status;

    @Override
    public String toString() {
        return "UsersResponseDTO{" +
                "role=" + role +
                ", id_akun=" + id_akun +
                ", nama_lengkap='" + nama_lengkap + '\'' +
                ", nama='" + nama + '\'' +
                ", email='" + email + '\'' +
                ", alamat='" + alamat + '\'' +
                ", sandi='" + sandi + '\'' +
                ", nomor_tlp='" + nomor_tlp + '\'' +
                ", foto='" + foto + '\'' +
                ", status=" + status +
                '}';
    }
}
