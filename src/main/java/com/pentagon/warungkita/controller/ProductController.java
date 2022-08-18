package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.ProductRequestDTO;
import com.pentagon.warungkita.dto.ProductResponseDTO;
import com.pentagon.warungkita.dto.ProductResponsePOST;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.*;
import com.pentagon.warungkita.repository.PhotoRepo;
import com.pentagon.warungkita.repository.ProductRepo;
import com.pentagon.warungkita.repository.UsersRepo;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.security.service.UserDetailsImpl;
import com.pentagon.warungkita.service.ProductService;
import com.pentagon.warungkita.service.ReportService;
import com.pentagon.warungkita.service.UsersService;
import com.pentagon.warungkita.service.implement.UsersServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pentagon/warung-kita")
@AllArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "03.Products")
public class ProductController {
    private final ProductService productService;
    private final ProductRepo productRepo;

    private final UsersService usersService;

    private static final Logger logger = LogManager.getLogger(ProductController.class);
    private ReportService reportService;
    @Autowired
    private HttpServletResponse response;
    /**
     * Get All Product
     * @return
     */
    @GetMapping("/product/all")
    public ResponseEntity<Object> findAllProduct() {
        try{
            List<Product> products = productService.getAll();
            List<ProductResponseDTO> productMaps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Product     ====================");
            for(Product dataResult:products){
                ProductResponseDTO productResponseDTO = dataResult.convertToResponse();
                productMaps.add(productResponseDTO);
                logger.info("Produk ID       : " + dataResult.getProductId());
                logger.info("SKU             : " + dataResult.getSku());
                logger.info("Nama Produk     : " + dataResult.getProductName());
                logger.info("Kategori        : " + dataResult.getCategories());
                logger.info("Deskripsi       : " + dataResult.getDescription());
                logger.info("Status          : " + dataResult.getProductStatusId());
                logger.info("Harga           : " + dataResult.getRegularPrice());
                logger.info("Stok            : " + dataResult.getQuantity());
                logger.info("Gambar          : " + dataResult.getProductPicture());
                logger.info("----------------------------------------------------------");
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Object> getProductById(@PathVariable Long productId){
        try {
            Optional<Product> product = productService.getProductById(productId);
            Product productGet = product.get();
            ProductResponseDTO result = productGet.convertToResponse();
            logger.info("==================== Logger Start Get By ID Product     ====================");
            logger.info("Produk ID     : " + productGet.getProductId());
            logger.info("SKU           : " + productGet.getSku());
            logger.info("Nama Produk   : " + productGet.getProductName());
            logger.info("Kategori      : " + productGet.getCategories());
            logger.info("Deskripsi     : " + productGet.getDescription());
            logger.info("Status        : " + productGet.getProductStatusId());
            logger.info("Harga         : " + productGet.getRegularPrice());
            logger.info("Stok          : " + productGet.getQuantity());
            logger.info("Gambar        : " + productGet.getProductPicture());
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
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Object> createProduct(@RequestBody ProductRequestDTO productRequestDTO){
        try{

            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional <Users> users = usersService.getUserById(userDetails.getUserId());


            /**
             * Logic to complete fill all field request
             * */

            if(productRequestDTO.getProductName().isEmpty() && productRequestDTO.getCategories().isEmpty() && productRequestDTO.getQuantity() != null
                    && productRequestDTO.getSku().isEmpty() && productRequestDTO.getProductStatusId() != null && productRequestDTO.getRegularPrice() != null){
                throw new ResourceNotFoundException("Please Input All Field");
            }

            Product product = Product.builder()
                    .sku(productRequestDTO.getSku())
                    .productName(productRequestDTO.getProductName())
                    .categories(productRequestDTO.getCategories())
                    .description(productRequestDTO.getDescription())
                    .regularPrice(productRequestDTO.getRegularPrice())
                    .quantity(productRequestDTO.getQuantity())
                    .productStatusId(productRequestDTO.getProductStatusId())
                    .productPicture(productRequestDTO.getProductPicture())
                    .users(users.get())
                    .build();


            List<Product> products = productRepo.findByUsersUserId(userDetails.getUserId());
            List<Photo> photos = productRequestDTO.getProductPicture();
            List<Categories> categories = productRequestDTO.getCategories();
            Integer countProduct = products.size();
            Integer countPhoto = photos.size();
            Integer countCategories = categories.size();
            /**
             * Logic 1 User only can post product max 4 post
             * */
            if (countProduct >= 4){
                throw new ResourceNotFoundException("tidak boleh posting lagi");
            }
            /**
             * Logic 1 post just add max 4 categories on 1 item
             * */
            if (countCategories > 4) {
                throw new ResourceNotFoundException("categories max 4");
            }
            /**
             * Logic 1 post just add max 4 photos on 1 item
             * */
            if (countPhoto > 4) {
                throw new ResourceNotFoundException("Maximum Photo is 4");
            }
            productService.createProduct(product);

            ProductResponsePOST result = product.convertToResponsePost();
            logger.info("==================== Logger Start Get New Add Product     ====================");
            logger.info("Produk ID     : " + product.getProductId());
            logger.info("SKU           : " + product.getSku());
            logger.info("Nama Produk   : " + product.getProductName());
            logger.info("Kategori      : " + product.getCategories());
            logger.info("Deskripsi     : " + product.getDescription());
            logger.info("Status        : " + product.getProductStatusId());
            logger.info("Harga         : " + product.getRegularPrice());
            logger.info("Stok          : " + product.getQuantity());
            logger.info("Gambar        : " + product.getProductPicture());
            logger.info("==================== Logger Start Get New Add Product     ====================");
            return ResponseHandler.generateResponse("Successfully Add Product",HttpStatus.CREATED,result);
        }catch (Exception e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Failed Create Product");
        }
    }

    /**
     * Edit Product Data
     * @param productId
     *
     */
    @PutMapping("/product/update/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Object> updateProduct(@PathVariable Long
                                                            productId, @RequestBody ProductRequestDTO productRequestDTO){
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional <Users> users = usersService.getUserById(userDetails.getUserId());

            if(productRequestDTO.getProductName().isEmpty() && productRequestDTO.getCategories().isEmpty() && productRequestDTO.getQuantity() != null
                    && productRequestDTO.getSku().isEmpty() && productRequestDTO.getProductStatusId() != null && productRequestDTO.getRegularPrice() != null){
                throw new ResourceNotFoundException("Please Input All Field");
            }

            Product product = Product.builder()
                    .sku(productRequestDTO.getSku())
                    .productName(productRequestDTO.getProductName())
                    .categories(productRequestDTO.getCategories())
                    .description(productRequestDTO.getDescription())
                    .regularPrice(productRequestDTO.getRegularPrice())
                    .quantity(productRequestDTO.getQuantity())
                    .productStatusId(productRequestDTO.getProductStatusId())
                    .productPicture(productRequestDTO.getProductPicture())
                    .users(users.get())
                    .build();


            List<Product> products = productRepo.findByUsersUserId(userDetails.getUserId());
            List<Photo> photos = productRequestDTO.getProductPicture();
            List<Categories> categories = productRequestDTO.getCategories();
            Integer countProduct = products.size();
            Integer countPhoto = photos.size();
            Integer countCategories = categories.size();
            if (countProduct >= 4){
                throw new ResourceNotFoundException("tidak boleh posting lagi");
            }
            if (countCategories > 4) {
                throw new ResourceNotFoundException("categories max 4");
            }
            if (countPhoto > 4) {
                throw new ResourceNotFoundException("Maximum Photo is 4");
            }
            product.setProductId(productId);
            Product responseUpdate = productService.updateProduct(product);
            ProductResponseDTO responseDTO = responseUpdate.convertToResponse();
            logger.info("==================== Logger Start Get Update Product     ====================");
            logger.info("Produk ID     : " + product.getProductId());
            logger.info("SKU           : " + product.getSku());
            logger.info("Nama Produk   : " + product.getProductName());
            logger.info("Kategori      : " + product.getCategories());
            logger.info("Deskripsi     : " + product.getDescription());
            logger.info("Status        : " + product.getProductStatusId());
            logger.info("Harga         : " + product.getRegularPrice());
            logger.info("Stok          : " + product.getQuantity());
            logger.info("Gambar        : " + product.getProductPicture());
            logger.info("==================== Logger Start Get Update Product     ====================");
            return ResponseHandler.generateResponse("Successfully Update Product",HttpStatus.CREATED,responseDTO);
        }catch (ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Failed Update Product");
        }
    }

    /**
     * Delete Product By ID
     * @param productId
     * @return
     */
    @DeleteMapping("product/delete/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long productId){
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

    @GetMapping("/product/byProductName")
    public ResponseEntity<Object> findByProductName(@RequestParam String productName){
        List<Product> test = productService.findByProductNameContaining(productName);
        return ResponseHandler.generateResponse("test",HttpStatus.OK,test);
    }

    @GetMapping("/product/byUsername")
    public ResponseEntity<Object> findBySellerUsername(@RequestParam String username){
        List<Product> products = productService.findByUsersUsernameContaining(username);
        return ResponseHandler.generateResponse("product",HttpStatus.OK, products);
    }

    @GetMapping("/product/product_reports")
    public void getReservasiReportA() throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Dispositin", "attachment; filname=\"product_list.pdf\"");
        JasperPrint jasperPrint = reportService.generateJasperPrintProductList();
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

}