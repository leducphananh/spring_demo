package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entity.Product;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductResponse createProduct(ProductRequest request) {
        Product product = productMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);
        log.info("Create product successfully: {}", savedProduct);
        return productMapper.toResponse(savedProduct);
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        return productMapper.toResponse(product);
    }

    public Page<ProductResponse> getProducts(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(keyword, pageable).map(productMapper::toResponse);
        }
        return productRepository.findAll(pageable).map(productMapper::toResponse);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            throw new ResourceNotFoundException("Product", "id", id);
        }

        productMapper.updateProduct(request, existingProduct);

        log.info("Update product successfully: {}", existingProduct);
        return productMapper.toResponse(productRepository.save(existingProduct));
    }

    public boolean deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            throw new ResourceNotFoundException("Product", "id", id);
        }

        productRepository.delete(existingProduct);
        log.info("Delete product successfully: {}", existingProduct);
        return true;
    }
}
