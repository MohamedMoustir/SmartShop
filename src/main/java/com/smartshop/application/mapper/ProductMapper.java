package com.smartshop.application.mapper;

import com.smartshop.domain.model.Product;
import com.smartshop.presontation.dto.Request.ProductRequest;
import com.smartshop.presontation.dto.Response.ProductResponse;

public class ProductMapper {

    public static Product toEntity(ProductRequest product) {
        if (product == null) return null;

        return Product.builder()
                .nom(product.getNom())
                .prixUnitaire(product.getPrixUnitaire())
                .stockDisponible(product.getStockDisponible())
                .build();
    }

    public static ProductResponse toResponse(Product dto) {
        if (dto == null) return null;

        return ProductResponse.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .prixUnitaire(dto.getPrixUnitaire())
                .stockDisponible(dto.getStockDisponible())
                .deleted(dto.isDeleted())
                .build();
    }
}
