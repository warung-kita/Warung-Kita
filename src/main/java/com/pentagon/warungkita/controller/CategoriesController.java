package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.CategoriesRequestDTO;
import com.pentagon.warungkita.service.CategoriesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pentagon/warung-kita")
@AllArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "06.Categories")
public class CategoriesController {
    private final CategoriesService categoriesService;
    private static final Logger logger = LogManager.getLogger(CategoriesController.class);
    /**
     * Get All Categories
     * @return
     */
    @GetMapping("/categories/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> findAll() {
        return categoriesService.getAll();
    }

    /**
     * Get Categories By ID
     * @param categoriesId
     * @return
     */
    @GetMapping("/categories/{categoriesId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> getCategoriesById(@PathVariable Long categoriesId){
        return categoriesService.getCategoriesById(categoriesId);
    }

    /**
     * Add New Categories
     * @param categoriesRequestDTO
     * @return
     */
    @PostMapping("/categories/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> createCategories(@RequestBody CategoriesRequestDTO categoriesRequestDTO){
        return categoriesService.createCategories(categoriesRequestDTO);
    }

    /**
     * Edit Categories Name, Find by ID
     * @param categoriesId
     * @param categoriesRequestDTO
     * @return
     */
    @PutMapping("/categories/update/{categoriesId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> updateCategories(@PathVariable Long categoriesId, @RequestBody CategoriesRequestDTO categoriesRequestDTO){
        return categoriesService.updateCategories(categoriesId, categoriesRequestDTO);
    }

    /**
     * Delete Existing Categories by ID
     * @param categoriesId
     * @return
     */
    @DeleteMapping("categories/delete/{categoriesId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteCategories(@PathVariable Long categoriesId){
        return categoriesService.deleteCategories(categoriesId);
    }
}