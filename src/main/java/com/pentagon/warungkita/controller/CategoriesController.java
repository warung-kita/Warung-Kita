package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.CategoriesRequestDTO;
import com.pentagon.warungkita.dto.CategoriesResponseDTO;
import com.pentagon.warungkita.dto.CategoriesResponsePOST;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Categories;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.service.CategoriesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pentagon/warung-kita")
@AllArgsConstructor
public class CategoriesController {
    private final CategoriesService categoriesService;

    /**
     * Get All Categories
     * @return
     */
    @GetMapping("/categories/all")
    public ResponseEntity<Object> findAll() {
        try{
            List<Categories> categories = categoriesService.getAll();
            List<CategoriesResponseDTO> categoriesMaps = new ArrayList<>();
            for(Categories dataResult:categories){
                CategoriesResponseDTO categoriesResponseDTO = dataResult.convertToResponse();
                categoriesMaps.add(categoriesResponseDTO);
            }
            return ResponseHandler.generateResponse("Successfully Get All Categories", HttpStatus.OK,categoriesMaps);
        }catch (ResourceNotFoundException e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Table has no value");
        }
    }

    /**
     * Get Categories By ID
     * @param categoriesId
     * @return
     */
    @GetMapping("/categories/{categoriesId}")
    public ResponseEntity<Object> getCategoriesById(@PathVariable Long categoriesId){
        try {
            Optional<Categories> categories = categoriesService.getCategoriesById(categoriesId);
            Categories categoriesGet = categories.get();
            CategoriesResponseDTO result = categoriesGet.convertToResponse();
            return ResponseHandler.generateResponse("Successfully Get Categories Id",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }

    /**
     * Add New Categories
     * @param categoriesRequestDTO
     * @return
     */
    @PostMapping("/categories/add")
    public ResponseEntity<Object> createCategories(@RequestBody CategoriesRequestDTO categoriesRequestDTO){
        try{
            if(categoriesRequestDTO.getName() == null){
                throw new ResourceNotFoundException("Please Add Categories Name");
            }
            Categories categories = categoriesRequestDTO.convertToEntity();
            categoriesService.createCategories(categories);
            CategoriesResponsePOST result = categories.convertToResponsePost();
            return ResponseHandler.generateResponse("Successfully Add Categories",HttpStatus.CREATED,result);
        }catch (Exception e){

            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Failed Create Categories");
        }
    }

    /**
     * Edit Categories Name, Find by ID
     * @param categoriesId
     * @param categoriesRequestDTO
     * @return
     */
    @PutMapping("/categories/update/{categoriesId}")
    public ResponseEntity<Object> updateCategories(@PathVariable Long categoriesId, @RequestBody CategoriesRequestDTO categoriesRequestDTO){
        try {
            if(categoriesRequestDTO.getName() == null){
                throw new ResourceNotFoundException("Please Add Categories Name");
            }
            Categories categories = categoriesRequestDTO.convertToEntity();
            categories.setCategoriesId(categoriesId);
            Categories updateCategories = categoriesService.updateCategories(categories);
            CategoriesResponseDTO results = categories.convertToResponse();
            return ResponseHandler.generateResponse("Successfully Update Categories",HttpStatus.CREATED,results);
        }catch (Exception e){

            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Bad Request");
        }
    }

    /**
     * Delete Existing Categories by ID
     * @param categoriesId
     * @return
     */
    @DeleteMapping("categories/delete/{categoriesId}")
    public ResponseEntity<Object> deleteCategories(@PathVariable Long categoriesId){
        try {
            categoriesService.deleteCategories(categoriesId);
            Boolean result = Boolean.TRUE;
            return ResponseHandler.generateResponse("Successfully Delete Categories",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }
}
