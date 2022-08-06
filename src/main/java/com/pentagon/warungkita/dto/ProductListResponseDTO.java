package com.pentagon.warungkita.dto;

import com.pentagon.warungkita.model.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListResponseDTO {
    private Product product;
//    private String sku;
//    private String nama;
//    private String deskripsi;
//    private Long status;
//    private Integer harga;
//    private Integer jumlah;
//    private String gambarProduct;
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
