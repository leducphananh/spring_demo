package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRequest request);

    ProductResponse toResponse(Product product);

    void updateProduct(ProductRequest request, @MappingTarget Product product);
}
