package com.pentagon.warungkita.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListResponsePOST {
    private String sku;
    private String nama;
    private String deskripsi;
    //    private Long status;
    private Integer harga;
    private Integer jumlah;
    private String gambarProduct;

    @Override
    public String toString() {
        return "ProductListResponsePOST{" +
                "sku='" + sku + '\'' +
                ", nama='" + nama + '\'' +
                ", deskripsi='" + deskripsi + '\'' +
                ", harga=" + harga +
                ", jumlah=" + jumlah +
                ", gambarProduct='" + gambarProduct + '\'' +
                '}';
    }
}

