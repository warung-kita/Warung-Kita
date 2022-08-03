package com.pentagon.warungkita.service.implement;

import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Categories;
import com.pentagon.warungkita.repository.CategoriesRepo;
import com.pentagon.warungkita.service.CategoriesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {
    private final CategoriesRepo categoriesRepo;

    /**
     * Get All Categories
     * @return categoriesRepo.findAll()
     */
    @Override
    public List<Categories> getAll() {
        List<Categories> categories = categoriesRepo.findAll();
        if(categories.isEmpty()){
            throw new ResourceNotFoundException("Categories Not Exist");
        }
        return this.categoriesRepo.findAll();
    }

    @Override
    public Optional<Categories> getCategoriesById(Long categoriesId) {
        Optional<Categories> categories = categoriesRepo.findById(categoriesId);
        if(categories.isEmpty()){
            throw new ResourceNotFoundException("Categories not exist with id " + categoriesId);
        }
        return this.categoriesRepo.findById(categoriesId);
    }

    @Override
    public Categories createCategories(Categories categories) {
        return this.categoriesRepo.save(categories);
    }

    @Override
    public Categories updateCategories(Categories categories) {
        Optional<Categories> optionalCategories = categoriesRepo.findById(categories.getCategoriesId());
        if(optionalCategories.isEmpty()){
            throw new ResourceNotFoundException("Booking not exist with id " + categories.getCategoriesId());
        }
        return this.categoriesRepo.save(categories);
    }

    @Override
    public void deleteCategories(Long categoriesId) {
        Optional<Categories> optionalCategories = categoriesRepo.findById(categoriesId);
        if(optionalCategories.isEmpty()){
            throw new ResourceNotFoundException("Booking not exist with id " + categoriesId);
        }
        Categories categories = categoriesRepo.getReferenceById(categoriesId);
        this.categoriesRepo.delete(categories);
    }
}
