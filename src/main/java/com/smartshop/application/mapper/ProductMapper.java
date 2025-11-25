package com.smartshop.mapper;

import com.smartshop.dto.ProductDto;
import com.smartshop.model.Product;

public class ProductMapper {

    public static ProductDto toDto(Product product) {
        if (product == null) return null;

        return ProductDto.builder()
                .id(product.getId())
                .nom(product.getNom())
                .prixUnitaire(product.getPrixUnitaire())
                .stockDisponible(product.getStockDisponible())
                .build();
    }

    public static Product toEntity(ProductDto dto) {
        if (dto == null) return null;

        return Product.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .prixUnitaire(dto.getPrixUnitaire())
                .stockDisponible(dto.getStockDisponible())
                .build();
    }
}
