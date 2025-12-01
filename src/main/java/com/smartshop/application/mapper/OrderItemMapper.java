package com.smartshop.application.mapper;

import com.smartshop.domain.model.OrderItem;
import com.smartshop.presontation.dto.Request.OrderItemRequest;
import com.smartshop.presontation.dto.Response.OrderItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "productId", target = "product.id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "commande", ignore = true)
    @Mapping(target = "prixUnitaireLigne", ignore = true)
    @Mapping(target = "totalLigne", ignore = true)
    OrderItem toEntity(OrderItemRequest request);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.nom", target = "productName")
    @Mapping(source = "prixUnitaireLigne", target = "prixUnitaire")
    OrderItemResponse toResponse(OrderItem entity);
}