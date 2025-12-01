package com.smartshop.application.mapper;

import com.smartshop.domain.model.Commande;
import com.smartshop.presontation.dto.Request.CommandeRequest;
import com.smartshop.presontation.dto.Response.CommandeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface CommandeMapper {

    @Mapping(source = "clientId", target = "client.id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "statut", ignore = true)
    Commande toEntity(CommandeRequest request);

    @Mapping(source = "date", target = "dateCommande")
    @Mapping(source = "codePromo", target = "codePromoUtilise")
    @Mapping(source = "orderItems", target = "items")
    CommandeResponse toResponse(Commande entity);
}