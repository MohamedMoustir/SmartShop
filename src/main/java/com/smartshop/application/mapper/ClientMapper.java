package com.smartshop.application.mapper;

import com.smartshop.domain.enums.UserRole;
import com.smartshop.domain.model.Client;
import com.smartshop.presontation.dto.Request.ClientRequest;
import com.smartshop.presontation.dto.Response.ClientResponse;

public class ClientMapper {


    public static Client toEntity(ClientRequest client){
        if(client == null ) return null;
        return Client.builder()
                .email(client.getEmail())
                .nom(client.getNom())
                .role(UserRole.CLIENT)
                .build();
    }

    public static ClientResponse toResponse(Client dto){
        if(dto == null)return null;
        return ClientResponse.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .role(String.valueOf(dto.getRole()))
                .nom(dto.getNom())
                .totalOrders(dto.getTotalOrders())
                .totalSpent(dto.getTotalSpent())
                .fidelityLevel(dto.getFidelityLevel())
                .build();
    }
}
