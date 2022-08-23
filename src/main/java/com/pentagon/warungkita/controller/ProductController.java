package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.ProductRequestDTO;
import com.pentagon.warungkita.repository.ProductRepo;
import com.pentagon.warungkita.service.ProductService;
import com.pentagon.warungkita.service.ReportService;
import com.pentagon.warungkita.service.UsersService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
        return productService.getAllProduct();
    }

    /**
     * Get Product By ID
     * @param productId is ID from 1 Product you want to show
     * @return Message, HTTPStatus, And Data
     */
    @GetMapping("/product/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Object> getProductById(@PathVariable Long productId){
        return productService.getProductById(productId);
    }

    /**
     * Add New Product
     * @param productRequestDTO is for input needs
     * @return Message, HTTPStatus, And Data
     */
    @PostMapping("/product/add")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Object> createProduct(@RequestBody ProductRequestDTO productRequestDTO){
        return productService.createProduct(productRequestDTO);
    }

    /**
     * Edit Product Data
     * @param productId
     *
     */
    @PutMapping("/product/update/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Object> updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDTO productRequestDTO){
        return this.productService.updateProduct(productId, productRequestDTO);
    }

    /**
     * Delete Product By ID
     * @param productId
     * @return Message, HTTPStatus, And Data
     */
    @DeleteMapping("product/delete/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long productId){
        return productService.deleteProduct(productId);
    }

    /**
     * Search Product By Product Name
     * @param productName is something you want to show
     * @return Message, HTTPStatus, And Data
     */
    @GetMapping("/product/byProductName")
    public ResponseEntity<Object> findByProductName(@RequestParam String productName){
       return productService.findByProductNameContaining(productName);
    }

    /**
     * Search Product By Shop Name/ Seller Name
     * @param username is Seller Name/Shop Name
     * @return Message, HTTPStatus, And Data
     */
    @GetMapping("/product/byUsername")
    public ResponseEntity<Object> findBySellerUsername(@RequestParam String username){
        return productService.findByUsersUsernameContaining(username);
    }
    /**
     * Reports For All Existing Product
     * @throws Exception
     */
    @GetMapping("/product/product_reports")
    public void getReservasiReportA() throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Dispositin", "attachment; filname=\"product_list.pdf\"");
        JasperPrint jasperPrint = reportService.generateJasperPrintProductList();
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

    @GetMapping("/product/byCategories")
    public ResponseEntity<Object> findByCategories(@RequestParam String name){
        return productService.findByCategories(name);
    }

}