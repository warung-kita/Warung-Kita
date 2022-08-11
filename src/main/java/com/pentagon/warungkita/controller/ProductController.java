package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.*;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Categories;
import com.pentagon.warungkita.model.Photo;
import com.pentagon.warungkita.model.Product;
import com.pentagon.warungkita.model.Users;
import com.pentagon.warungkita.repository.CategoriesRepo;
import com.pentagon.warungkita.repository.PhotoRepo;
import com.pentagon.warungkita.repository.ProductRepo;
import com.pentagon.warungkita.repository.UsersRepo;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.service.CategoriesService;
import com.pentagon.warungkita.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pentagon/warung-kita")
@AllArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "3.Products")
public class ProductController {
    private final ProductService productService;
    private final ProductRepo productRepo;
    private final PhotoRepo photoRepo;
    private final UsersRepo usersRepo;
    private static final Logger logger = LogManager.getLogger(ProductController.class);
    /**
     * Get All Product
     * @return
     */
    @GetMapping("/product/all")
    public ResponseEntity<Object> findAll() {
        try{
            List<Product> products = productService.getAll();
            List<ProductResponseDTO> productMaps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Product     ====================");
            for(Product dataResult:products){
                ProductResponseDTO productResponseDTO = dataResult.convertToResponse();
                productMaps.add(productResponseDTO);
                logger.info("Produk ID       : " + dataResult.getProductId());
                logger.info("SKU     : " + dataResult.getSku());
                logger.info("Nama Produk       : " + dataResult.getProductName());
                logger.info("Kategori     : " + dataResult.getCategories());
                logger.info("Deskripsi       : " + dataResult.getDescription());
                logger.info("Status     : " + dataResult.getProductStatusId());
                logger.info("Harga       : " + dataResult.getRegularPrice());
                logger.info("Stok     : " + dataResult.getQuantity());
                logger.info("Gambar       : " + dataResult.getProductPicture());
            }
            logger.info("==================== Logger Start Get All Product     ====================");
            return ResponseHandler.generateResponse("Successfully Get All Product", HttpStatus.OK,productMaps);
        }catch (ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Table has no value");
        }
    }

    /**
     * Get Product By ID
     * @param productId
     * @return
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<Object> getCategoriesById(@PathVariable Long productId){
        try {
            Optional<Product> product = productService.getProductById(productId);
            Product productGet = product.get();
            ProductResponseDTO result = productGet.convertToResponse();
            logger.info("==================== Logger Start Get By ID Product     ====================");
            logger.info("Produk ID       : " + productGet.getProductId());
            logger.info("SKU     : " + productGet.getSku());
            logger.info("Nama Produk       : " + productGet.getProductName());
            logger.info("Kategori     : " + productGet.getCategories());
            logger.info("Deskripsi       : " + productGet.getDescription());
            logger.info("Status     : " + productGet.getProductStatusId());
            logger.info("Harga       : " + productGet.getRegularPrice());
            logger.info("Stok     : " + productGet.getQuantity());
            logger.info("Gambar       : " + productGet.getProductPicture());
            logger.info("==================== Logger Start Get By ID Product     ====================");
            return ResponseHandler.generateResponse("Successfully Get Product Id",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }

    /**
     * Add New Product
     * @param productRequestDTO
     * @return
     */
    @PostMapping("/product/add")
    public ResponseEntity<Object> createCategories(@RequestBody ProductRequestDTO productRequestDTO){
        try{
            if(productRequestDTO.getProductName() == null || productRequestDTO.getCategories() == null || productRequestDTO.getQuantity() == null
                    || productRequestDTO.getSku() == null || productRequestDTO.getProductStatusId() == null || productRequestDTO.getRegularPrice() == null){
                throw new ResourceNotFoundException("Please Input All Field");
            }

            Users users = usersRepo.findById(productRequestDTO.getUserId()).orElseThrow();
            Product product= productRequestDTO.convertToEntity(users);

//            Photo photo = new Photo();
//            Photo photos = photoRepo.save(photo);
//            product.setProductPicture(photos);
            List<Product> products = productRepo.findByUsersUserId(productRequestDTO.getUserId());
            List<Categories> categories = productRequestDTO.getCategories();
            Integer count = products.size();
            Integer test = categories.size();
            if (count >= 4){
                throw new ResourceNotFoundException("tidak boleh posting lagi");
            }
            if (test > 4) {
                throw new ResourceNotFoundException("categories max 4");
            }
            productService.createProduct(product);


            ProductResponsePOST result = product.convertToResponsePost();
            logger.info("==================== Logger Start Get New Add Product     ====================");
            logger.info("Produk ID       : " + product.getProductId());
            logger.info("SKU     : " + product.getSku());
            logger.info("Nama Produk       : " + product.getProductName());
            logger.info("Kategori     : " + product.getCategories());
            logger.info("Deskripsi       : " + product.getDescription());
            logger.info("Status     : " + product.getProductStatusId());
            logger.info("Harga       : " + product.getRegularPrice());
            logger.info("Stok     : " + product.getQuantity());
            logger.info("Gambar       : " + product.getProductPicture());
            logger.info("==================== Logger Start Get New Add Product     ====================");
            return ResponseHandler.generateResponse("Successfully Add Product",HttpStatus.CREATED,result);
        }catch (Exception e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Failed Create Categories");
        }
    }

    /**
     * Edit Product Data
     * @param productId
     * @param productRequestDTO
     * @return
     */
    @PutMapping("/product/update/{productId}")
    public ResponseEntity<Object> updateCategories(@PathVariable Long productId, @RequestBody ProductRequestDTO productRequestDTO){
        try {
            if(productRequestDTO.getProductName() == null || productRequestDTO.getCategories() == null || productRequestDTO.getQuantity() == null
                    || productRequestDTO.getSku() == null || productRequestDTO.getProductStatusId() == null || productRequestDTO.getRegularPrice() == null){
                throw new ResourceNotFoundException("Please Input All Field");
            }

            Users users = usersRepo.findById(productRequestDTO.getUserId()).orElseThrow();
            Product product = productRequestDTO.convertToEntity(users);

            product.setProductId(productId);
            Product responseUpdate = productService.updateProduct(product);
            ProductResponseDTO responseDTO = responseUpdate.convertToResponse();
            ProductResponseDTO results = product.convertToResponse();
            logger.info("==================== Logger Start Get Update Product     ====================");
            logger.info("Produk ID       : " + product.getProductId());
            logger.info("SKU     : " + product.getSku());
            logger.info("Nama Produk       : " + product.getProductName());
            logger.info("Kategori     : " + product.getCategories());
            logger.info("Deskripsi       : " + product.getDescription());
            logger.info("Status     : " + product.getProductStatusId());
            logger.info("Harga       : " + product.getRegularPrice());
            logger.info("Stok     : " + product.getQuantity());
            logger.info("Gambar       : " + product.getProductPicture());
            logger.info("==================== Logger Start Get Update Product     ====================");
            return ResponseHandler.generateResponse("Successfully Update Product",HttpStatus.CREATED,responseDTO);
        }catch (Exception e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Bad Request");
        }
    }

    /**
     * Delete Product By ID
     * @param productId
     * @return
     */
    @DeleteMapping("product/delete/{productId}")
    public ResponseEntity<Object> deleteCategories(@PathVariable Long productId){
        try {
            productService.deleteProduct(productId);
            Boolean result = Boolean.TRUE;
            logger.info("==================== Logger Start Get Deleted Product     ====================");
            logger.info(result);
            logger.info("==================== Logger Start Get Deleted Product     ====================");
            return ResponseHandler.generateResponse("Successfully Delete Product",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }

    @GetMapping("/product/namelike")
    public ResponseEntity<Object> findByProductName(@RequestParam String productName){
        List<Product> test = productService.findByProductNameContaining(productName);
//        List<ProductResponseDTO> test2 = test.stream()
//                .map(Product::convertToResponse)
//                .collect(Collectors.toList());
//        logger.info(test2);
        return ResponseHandler.generateResponse("test",HttpStatus.OK,test);
    }

    @GetMapping("/product/username")
    public ResponseEntity<Object> findByUsername(@RequestParam String username){
        List<Product> products = productService.findByUsersUsernameContaining(username);
        return ResponseHandler.generateResponse("product",HttpStatus.OK, products);
    }
}