package com.pentagon.warungkita.dto;

import com.pentagon.warungkita.model.Categories;
import com.pentagon.warungkita.model.Photo;
import com.pentagon.warungkita.model.ProductStatus;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO {
    private Long kodeProduk;
    private String sku;
    private String namaProduk;
    private List<Categories> kategori;
    private String deskripsi;
    private ProductStatus status;
    private Integer harga;
    private Integer stok;
    private List<Photo> gambar;

    @Override
    public String toString() {
        return "ProductResponseDTO{" +
                "Kode Produk = " + kodeProduk +
                ", SKU = '" + sku + '\'' +
                ", Nama Produk = '" + namaProduk + '\'' +
                ", Kategori = " + kategori +
                ", Deskripsi = '" + deskripsi + '\'' +
                ", Status = " + status +
                ", Harga = " + harga +
                ", Stok = " + stok +
                ", Gambar = " + gambar +
                '}';
    }
}