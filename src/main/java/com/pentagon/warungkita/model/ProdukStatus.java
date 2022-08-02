package com.pentagon.warungkita.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table (name = "product_statuses")
public class ProdukStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productStatusId;
    private String name;
}