package com.example.demo.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;

    private Double price;

    private Integer stock;

    private String imageUrl;

    private Boolean active;
}
