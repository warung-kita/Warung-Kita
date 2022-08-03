package com.pentagon.warungkita.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListResponseDTO {
    private String sku;
    private String nama;
    private String deskripsi;
//    private Long status;
    private Integer harga;
    private Integer jumlah;
    private String gambarProduct;
    private String namaUser;
    private String alamat;
    private String nomorHandphone;

    @Override
    public String toString() {
        return "ProductListResponseDTO{" +
                "sku='" + sku + '\'' +
                ", nama='" + nama + '\'' +
                ", deskripsi='" + deskripsi + '\'' +

                ", harga=" + harga +
                ", jumlah=" + jumlah +
                ", gambarProduct='" + gambarProduct + '\'' +
                ", namaUser=" + namaUser +
                ", alamat='" + alamat + '\'' +
                ", nomorHandphone='" + nomorHandphone + '\'' +
                '}';
    }
}
