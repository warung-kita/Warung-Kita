package com.pentagon.warungkita.dto;

import com.pentagon.warungkita.model.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistResponseDTO {
    private Product product;

    private String namaUser;
    private String alamat;
    private String nomorHandphone;

    @Override
    public String toString() {
        return "ProductListResponseDTO{" +
                "product=" + product +
                ", namaUser='" + namaUser + '\'' +
                ", alamat='" + alamat + '\'' +
                ", nomorHandphone='" + nomorHandphone + '\'' +
                '}';
    }
}
