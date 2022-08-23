package com.pentagon.warungkita.service;

import com.pentagon.warungkita.dto.CategoriesRequestDTO;
import com.pentagon.warungkita.model.Categories;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CategoriesService {
    ResponseEntity<Object> getAll();
    ResponseEntity<Object> getCategoriesById(Long categoriesId);
    ResponseEntity<Object> createCategories(CategoriesRequestDTO categoriesRequestDTO);
    ResponseEntity<Object> updateCategories(Long categoriesId, CategoriesRequestDTO categoriesRequestDTO);
    ResponseEntity<Object> deleteCategories(Long categoriesId);

}
