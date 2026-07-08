package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductRequest;
import com.example.demo.dto.ProductResponse;
import com.example.demo.entity.Product;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest request) {
        Product product = ProductMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toResponse(savedProduct);
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        return ProductMapper.toResponse(product);
    }

    public Page<ProductResponse> getProducts(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(keyword, pageable).map(ProductMapper::toResponse);
        }
        return productRepository.findAll(pageable).map(ProductMapper::toResponse);
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setStock(product.getStock());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    public boolean deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct != null) {
            productRepository.delete(existingProduct);
            return true;
        }
        return false;
    }
}
