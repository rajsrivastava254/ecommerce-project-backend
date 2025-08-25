package com.spring.ecom_project.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.spring.ecom_project.services.ProductService;
import com.spring.ecom_project.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "https://ecommerce-project-frontend-254.vercel.app")
@RequestMapping("/api")
@Tag(name = "Product Management", description = "APIs for managing products")
public class ProductController {

    @Autowired
    private ProductService service;

    @RequestMapping(value = {"", "/"})
    @Operation(summary = "Welcome endpoint", description = "Returns a welcome message")
    public String greet() {
        return "Hello Customers";
    }

    @GetMapping("/products")
    @Operation(summary = "Get all products", description = "Retrieve all products with pagination")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    public ResponseEntity<Page<Product>> getAllProducts(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return new ResponseEntity<>(service.getAllProducts(pageable), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = service.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // Search endpoints
    @GetMapping("/products/search/name")
    public ResponseEntity<Page<Product>> searchByName(
            @RequestParam String name,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return new ResponseEntity<>(service.searchByName(name, pageable), HttpStatus.OK);
    }

    @GetMapping("/products/search/category")
    public ResponseEntity<Page<Product>> searchByCategory(
            @RequestParam String category,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return new ResponseEntity<>(service.searchByCategory(category, pageable), HttpStatus.OK);
    }

    @GetMapping("/products/search/brand")
    public ResponseEntity<Page<Product>> searchByBrand(
            @RequestParam String brand,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return new ResponseEntity<>(service.searchByBrand(brand, pageable), HttpStatus.OK);
    }

    @GetMapping("/products/search/price")
    public ResponseEntity<Page<Product>> searchByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return new ResponseEntity<>(service.searchByPriceRange(minPrice, maxPrice, pageable), HttpStatus.OK);
    }

    @GetMapping("/products/search/availability")
    public ResponseEntity<Page<Product>> searchByAvailability(
            @RequestParam Boolean availability,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return new ResponseEntity<>(service.searchByAvailability(availability, pageable), HttpStatus.OK);
    }

    @GetMapping("/products/search/advanced")
    public ResponseEntity<Page<Product>> advancedSearch(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return new ResponseEntity<>(service.advancedSearch(name, category, minPrice, maxPrice, pageable), HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = service.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        Product updatedProduct = service.updateProduct(id, product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        try {
            Resource resource = new ClassPathResource("static/images/" + filename);
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }
            String contentType = filename.endsWith(".png") ? "image/png" :
                                 filename.endsWith(".jpg") || filename.endsWith(".jpeg") ? "image/jpeg" :
                                 "application/octet-stream";
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

