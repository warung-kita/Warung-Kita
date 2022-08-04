package com.pentagon.warungkita.dto;

import com.pentagon.warungkita.model.Categories;
import com.pentagon.warungkita.model.ProductStatus;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponsePOST {
    private Long kodeProduk;
    private String sku;
    private String namaProduk;
    private List<Categories> kategori;
    private String deskripsi;
    private ProductStatus status;
    private Integer harga;
    private Integer stok;
    private String gambar;
    private String namaKategori;

    @Override
    public String toString() {
        return "ProductResponsePOST{" +
                "kodeProduk=" + kodeProduk +
                ", sku='" + sku + '\'' +
                ", namaProduk='" + namaProduk + '\'' +
                ", kategori=" + kategori +
                ", deskripsi='" + deskripsi + '\'' +
                ", status=" + status +
                ", harga=" + harga +
                ", stok=" + stok +
                ", gambar='" + gambar + '\'' +
                ", namaKategori='" + namaKategori + '\'' +
                '}';
    }
}
