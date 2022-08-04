package com.pentagon.warungkita.service;

import com.pentagon.warungkita.model.Categories;

import java.util.List;
import java.util.Optional;

public interface CategoriesService {
    List<Categories> getAll();
    Optional<Categories> getCategoriesById(Long categoriesId);
    Categories createCategories(Categories categories);
    Categories updateCategories (Categories categories);
    void deleteCategories(Long categoriesId);
}
