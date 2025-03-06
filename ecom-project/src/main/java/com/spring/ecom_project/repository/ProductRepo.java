package com.spring.ecom_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.ecom_project.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>{

}
