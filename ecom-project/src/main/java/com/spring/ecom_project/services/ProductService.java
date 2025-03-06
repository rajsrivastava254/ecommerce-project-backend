package com.spring.ecom_project.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.spring.ecom_project.model.Product;
import com.spring.ecom_project.repository.ProductRepo;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    private static final String IMAGE_BASE_URL = "http://localhost:8080/api/images/";

    public List<Product> getAllProducts() {
        return repo.findAll().stream()
                .map(this::updateImageUrl)
                .collect(Collectors.toList());
    }

    public Product getProductById(Long id) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));

        return updateImageUrl(product);
    }

    private Product updateImageUrl(Product product) {
        if (product.getImageUrl() != null && !product.getImageUrl().startsWith("http")) {
            product.setImageUrl(IMAGE_BASE_URL + product.getImageUrl());
        }
        return product;
    }
}
