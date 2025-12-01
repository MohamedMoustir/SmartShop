package com.smartshop.application.mapper;

import com.smartshop.domain.model.Client;
import com.smartshop.presontation.dto.Request.ClientRequest;
import com.smartshop.presontation.dto.Response.ClientResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "role", constant = "CLIENT")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalOrders", ignore = true)
    @Mapping(target = "totalSpent", ignore = true)
    @Mapping(target = "fidelityLevel", ignore = true)
    @Mapping(target = "firstOrderAt", ignore = true)
    @Mapping(target = "lastOrderAt", ignore = true)
    Client  toEntity(ClientRequest clientRequest);

    ClientResponse toResponse(Client client);
}


