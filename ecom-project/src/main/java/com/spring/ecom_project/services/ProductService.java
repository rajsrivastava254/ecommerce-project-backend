package com.spring.ecom_project.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.spring.ecom_project.model.Product;
import com.spring.ecom_project.repository.ProductRepo;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    private static final String IMAGE_BASE_URL = "http://localhost:8080/api/images/";

    public Page<Product> getAllProducts(Pageable pageable) {
        return repo.findAll(pageable).map(this::updateImageUrl);
    }

    public Product getProductById(Long id) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));
        return toDTO(product);
    }

    // Search methods
    public Page<Product> searchByName(String name, Pageable pageable) {
        return repo.findByNameContainingIgnoreCase(name, pageable).map(this::updateImageUrl);
    }

    public Page<Product> searchByCategory(String category, Pageable pageable) {
        return repo.findByCategory(category, pageable).map(this::updateImageUrl);
    }

    public Page<Product> searchByBrand(String brand, Pageable pageable) {
        return repo.findByBrand(brand, pageable).map(this::updateImageUrl);
    }

    public Page<Product> searchByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return repo.findByPriceBetween(minPrice, maxPrice, pageable).map(this::updateImageUrl);
    }

    public Page<Product> searchByAvailability(Boolean availability, Pageable pageable) {
        return repo.findByAvailability(availability, pageable).map(this::updateImageUrl);
    }

    public Page<Product> advancedSearch(String name, String category, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return repo.findByNameContainingIgnoreCaseAndCategoryAndPriceBetween(name, category, minPrice, maxPrice, pageable)
                .map(this::updateImageUrl);
    }

    @Transactional
    public Product createProduct(Product dto) {
        Product product = toEntity(dto);
        // id should not be set for new product; JPA will handle it
        Product saved = repo.save(product);
        return toDTO(saved);
    }

    @Transactional
    public Product updateProduct(Long id, Product dto) {
        Product existing = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));
        // Update fields
        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setBrand(dto.getBrand());
        existing.setCategory(dto.getCategory());
        existing.setPrice(dto.getPrice());
        existing.setImageUrl(dto.getImageUrl());
        existing.setReleaseDate(dto.getReleaseDate());
        existing.setAvailability(dto.getAvailability() != null ? dto.getAvailability() : false);
        existing.setQuantity(dto.getQuantity());
        Product saved = repo.save(existing);
        return toDTO(saved);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
        }
        repo.deleteById(id);
    }

    private Product toDTO(Product product) {
        return new Product(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getBrand(),
            product.getCategory(),
            product.getPrice(),
            updateImageUrl(product).getImageUrl(),
            product.getReleaseDate(),
            product.getAvailability(),
            product.getQuantity()
        );
    }

    private Product toEntity(Product dto) {
        return new Product(
            dto.getId(),
            dto.getName(),
            dto.getDescription(),
            dto.getBrand(),
            dto.getCategory(),
            dto.getPrice(),
            dto.getImageUrl(),
            dto.getReleaseDate(),
            dto.getAvailability() != null ? dto.getAvailability() : false,
            dto.getQuantity()
        );
    }

    private Product updateImageUrl(Product product) {
        if (product.getImageUrl() != null && !product.getImageUrl().startsWith("http")) {
            product.setImageUrl(IMAGE_BASE_URL + product.getImageUrl());
        }
        return product;
    }
}