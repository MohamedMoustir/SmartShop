package com.smartshop.mapper;

import com.smartshop.dto.OrderItemDTO;
import com.smartshop.model.Commande;
import com.smartshop.model.OrderItem;
import com.smartshop.model.Product;

public class OrderItemMapper {

    public static OrderItem toEntity(OrderItemDTO dto) {
        if (dto == null) return null;

        return OrderItem.builder()
                .id(dto.getId())
                .quantity(dto.getQuantity())
                .prixUnitaireLigne(dto.getPrixUnitaireLigne())
                .totalLigne(dto.getTotalLigne())

                .commande(Commande.builder().id(dto.getCommandeId()).build())
                .product(Product.builder().id(dto.getProductId()).build())

                .build();
    }

    public static OrderItemDTO toDto(OrderItem entity) {
        if (entity == null) return null;

        return OrderItemDTO.builder()
                .id(entity.getId())
                .quantity(entity.getQuantity())
                .prixUnitaireLigne(entity.getPrixUnitaireLigne())
                .totalLigne(entity.getTotalLigne())

                .commandeId(entity.getCommande() != null ? entity.getCommande().getId() : null)
                .productId(entity.getProduct() != null ? entity.getProduct().getId() : null)

                .build();
    }
}
