package com.smartshop.application.mapper;

import com.smartshop.domain.model.Commande;
import com.smartshop.domain.model.OrderItem;
import com.smartshop.domain.model.Product;
import com.smartshop.presontation.dto.Request.OrderItemRequest;
import com.smartshop.presontation.dto.Response.OrderItemResponse;

public class OrderItemMapper {

    public static OrderItem toEntity(OrderItemRequest request) {
        if (request == null) return null;

        return OrderItem.builder()
                .quantity(request.getQuantity())
                .product(Product.builder().id(request.getProductId()).build())
                .build();
    }

    public static OrderItemResponse toResponse(OrderItem entity) {
        if (entity == null) return null;

        return OrderItemResponse.builder()
                .productId(entity.getProduct().getId())
                .productName(entity.getProduct().getNom())
                .quantity(entity.getQuantity())
                .prixUnitaire(entity.getPrixUnitaireLigne())
                .totalLigne(entity.getTotalLigne())
                .build();

    }
}
