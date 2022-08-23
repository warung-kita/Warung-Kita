package com.pentagon.warungkita.service.implement;

import com.pentagon.warungkita.controller.ProductController;
import com.pentagon.warungkita.dto.ProductRequestDTO;
import com.pentagon.warungkita.dto.ProductResponseDTO;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Categories;
import com.pentagon.warungkita.model.Photo;
import com.pentagon.warungkita.model.Product;
import com.pentagon.warungkita.model.Users;
import com.pentagon.warungkita.repository.ProductRepo;
import com.pentagon.warungkita.repository.UsersRepo;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.security.service.UserDetailsImpl;
import com.pentagon.warungkita.service.ProductService;
import com.pentagon.warungkita.service.UsersService;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final UsersService usersService;
    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);

    /**
     * Get All Product Services and Logger
     * @return ProductRepo.findAll() /JPA Hibernate
     */
    @Override
    public ResponseEntity<Object> getAllProduct() {
        try {
            List<Product> products = productRepo.findAll();
            if (products.isEmpty()) {
                throw new ResourceNotFoundException("Data is Empty");
            }
            List<ProductResponseDTO> productList = new ArrayList<>();
            logger.info("==================== Logger Start Get All Product     ====================");
            for(Product dataResult:products){
                ProductResponseDTO productResponseDTO = dataResult.convertToResponse();
                productList.add(productResponseDTO);
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
            return ResponseHandler.generateResponse("Successfully Get All Product", HttpStatus.OK, productList);
        }catch(ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }

    /**
     *
     * @param productId is ID from 1 product
     * @return ProductRepo.findByID
     * @throws ResourceNotFoundException if something missing/errors
     */
    @Override
    public ResponseEntity<Object> getProductById(Long productId) throws ResourceNotFoundException {
        Optional<Product> product = productRepo.findById(productId);
        if(product.isEmpty()){
            throw new ResourceNotFoundException("Product not exist with id " + productId);
        }
        try {
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
            logger.info("==================== Logger End =================");
            return ResponseHandler.generateResponse("Success Get By Id",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }

    @Override
    public ResponseEntity<Object> createProduct(ProductRequestDTO productRequestDTO) {
        try{
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional <Users> users = usersService.getUserById(userDetails.getUserId());

            /*
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
            productRepo.save(product);

            ProductResponseDTO result = product.convertToResponse();
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

    @Override
    public ResponseEntity<Object> updateProduct(Long productId, ProductRequestDTO productRequestDTO) throws ResourceNotFoundException {
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
            Product responseUpdate = productRepo.save(product);
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

    @Override
    public ResponseEntity<Object> deleteProduct(Long productId) throws ResourceNotFoundException{
        try {
            productRepo.deleteById(productId);
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

    @Override
    public ResponseEntity<Object> findByProductNameContaining(String productName) {
        try {
            List<Product> products = productRepo.findByProductNameContaining(productName);
            List<ProductResponseDTO> productList = new ArrayList<>();
            for(Product dataResult:products) {
                ProductResponseDTO productResponseDTO = dataResult.convertToResponse();
                productList.add(productResponseDTO);
            }
            return ResponseHandler.generateResponse("test",HttpStatus.OK,productList);
        }catch (ResourceNotFoundException e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }

      }

    @Override
    public ResponseEntity<Object> findByUsersUsernameContaining(String username) {
        try {
            List<Product> products = productRepo.findByUsersUsernameContaining(username);
            List<ProductResponseDTO> productList = new ArrayList<>();
            for(Product dataResult:products) {
                ProductResponseDTO productResponseDTO = dataResult.convertToResponse();
                productList.add(productResponseDTO);
            }
            return ResponseHandler.generateResponse("Data Successfully Retrieved",HttpStatus.OK, productList);
        }catch (ResourceNotFoundException e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }

    @Override
    public List<Product> findByUsersUserId(Long userId) {
        List<Product> products = productRepo.findByUsersUserId(userId);
        return products;
    }


}
