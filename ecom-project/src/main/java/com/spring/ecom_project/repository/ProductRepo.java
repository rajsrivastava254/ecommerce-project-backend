package com.spring.ecom_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.spring.ecom_project.model.Product;
import java.math.BigDecimal;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>{
    
    // Search by name (case-insensitive)
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    // Search by category
    Page<Product> findByCategory(String category, Pageable pageable);
    
    // Search by brand
    Page<Product> findByBrand(String brand, Pageable pageable);
    
    // Search by price range
    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    
    // Search by availability
    Page<Product> findByAvailability(Boolean availability, Pageable pageable);
    
    // Combined search
    Page<Product> findByNameContainingIgnoreCaseAndCategoryAndPriceBetween(
        String name, String category, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
}
