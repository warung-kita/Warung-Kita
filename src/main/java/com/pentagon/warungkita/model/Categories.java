package com.pentagon.warungkita.model;

import lombok.*;
import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "categories")
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoriesId;


    private String name;

    @Override
    public String toString() {
        return "Categories{" +
                "categoriesId=" + categoriesId +
                ", name='" + name + '\'' +
                '}';
    }
}
