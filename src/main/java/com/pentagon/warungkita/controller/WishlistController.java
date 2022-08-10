package com.pentagon.warungkita.controller;


import com.pentagon.warungkita.dto.WishlistRequestDTO;
import com.pentagon.warungkita.dto.WishlistResponseDTO;
import com.pentagon.warungkita.dto.WishlistResponsePOST;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Wishlist;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.service.WishlistService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/pentagon/warung-kita")
@AllArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "5.Wishlist")
public class WishlistController {

    private static final Logger logger = LogManager.getLogger(WishlistController.class);
    private WishlistService wishlistService;


    @GetMapping("/wishlist/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> findAllWishlist(){
        try{
            List<Wishlist> wishlists = wishlistService.getAllProductList();
            List<WishlistResponseDTO> productListmaps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Product List ====================");
            for(Wishlist dataresult: wishlists){
                WishlistResponseDTO wishlistResponseDTO = dataresult.convertToResponse();
                productListmaps.add(wishlistResponseDTO);
                logger.info("code :"+dataresult.getWishlistId());
                logger.info("User :"+dataresult.getUser() );
                logger.info("Product :"+dataresult.getProduct() );
                logger.info("------------------------------------");
            }
            logger.info("==================== Logger End  ====================");
            return ResponseHandler.generateResponse("Succes Get All", HttpStatus.OK,productListmaps);
        }catch (ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Table has no value");
        }
    }
    @GetMapping("/wishlist/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> getWishlistById(@PathVariable Long id){
        try {
            Optional<Wishlist> productList = wishlistService.getProductListById(id);
            Wishlist productListget = productList.get();
            WishlistResponseDTO result = productListget.convertToResponse();
            logger.info("======== Logger Start Find Product List with ID "+id+ "  ========");
            logger.info("User :"+result.getNamaUser() );
            logger.info("Product :"+result.getProduct().getProductName());
            logger.info("==================== Logger End =================");
            return ResponseHandler.generateResponse("Success Get By Id",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }
    @PostMapping("/wishlist/create")
    @PreAuthorize("hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> productListCreate(@RequestBody WishlistRequestDTO wishlistRequestDTO){
        try{
            if(wishlistRequestDTO.getProduct() == null || wishlistRequestDTO.getUser() == null){
                throw new ResourceNotFoundException("Product List must have product id and user id");
            }
            Wishlist wishlist = wishlistRequestDTO.convertToEntity();
            wishlistService.createProductList(wishlist);
            WishlistResponsePOST result = wishlist.convertToResponsePost();
            logger.info("======== Logger Start   ========");
            logger.info("User :"+ wishlist.getUser() );
            logger.info("Product :"+ wishlist.getProduct());
            logger.info("==================== Logger End =================");
            return ResponseHandler.generateResponse("Success Create Product List",HttpStatus.CREATED,result);
        }catch (Exception e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Failed Create Database");
        }
    }
    @PutMapping("/wishlist/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> produkListUpdate(@PathVariable Long id, @RequestBody WishlistRequestDTO wishlistRequestDTO){
        try {
            if(wishlistRequestDTO.getProduct() == null || wishlistRequestDTO.getUser() == null){
                throw new ResourceNotFoundException("Product List must have product id and user id");
            }
            Wishlist wishlist = wishlistRequestDTO.convertToEntity();
            wishlist.setWishlistId(id);
            Wishlist updateList = wishlistService.updateProductList(wishlist);
            WishlistResponseDTO results = updateList.convertToResponse();
            logger.info("======== Logger Start   ========");
            logger.info("User :"+results.getNamaUser());
            logger.info("Product :"+results.getProduct().getProductName());
            logger.info("==================== Logger End =================");
            return ResponseHandler.generateResponse("Success Update Product List",HttpStatus.CREATED,results);
        }catch (Exception e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Bad Request");
        }
    }
    @DeleteMapping("wishlist/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> deleteProductList(@PathVariable Long id){
        try {
            wishlistService.deleteProductListById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            logger.info("======== Logger Start   ========");
            logger.info("Product List deleted " + response);
            logger.info("==================== Logger End =================");
            return ResponseHandler.generateResponse("Success Delete Product List by ID",HttpStatus.OK,response);
        }catch(ResourceNotFoundException e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }

//    @PostMapping("product_list/find/{id}")
//    public ResponseEntity<Object> findWishListByUserId(@RequestBody Users users){
//        try {
//            List<ProductList> produkListByUserId = productListService.getWishlistByUsersId(users.getUserId());
//            List<ProductListResponseDTO> productListResponseDTOS = produkListByUserId.stream()
//                    .map(ProductList::convertToResponse)
//                    .collect(Collectors.toList());
//
//            logger.info("Success Query By User Id : " +productListResponseDTOS);
//
//            return ResponseHandler.generateResponse("Succes Query By User Id",HttpStatus.OK,productListResponseDTOS);
//        }catch (Exception e){
//
//            logger.error(e.getMessage());
//
//            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Failed Query By User Id");
//        }
//
//    }

    @GetMapping("/wishlist/username")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> findWishlistByUserName(@RequestParam String userName){
        List<Wishlist> test = wishlistService.findByUserUsernameContaining(userName);
//        List<WishlistResponseDTO> test2 = test.stream()
//                .map(Wishlist::convertToResponse)
//                .collect(Collectors.toList());
        return ResponseHandler.generateResponse("test",HttpStatus.OK,test);
    }
}
