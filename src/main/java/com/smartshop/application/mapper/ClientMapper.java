package com.smartshop.application.mapper;

import com.smartshop.presontation.dto.ClientDTO;
import com.smartshop.domain.enums.UserRole;
import com.smartshop.domain.model.Client;

public class ClientMapper {


    public static ClientDTO toDto(Client client){
        if(client == null ) return null;
        return ClientDTO.builder()
                .id(client.getId())
                .email(client.getEmail())
                .nom(client.getNom())
                .role(client.getRole() != null ? client.getRole().name():null)
                .fidelityLevel(client.getFidelityLevel())
                .totalOrders(client.getTotalOrders())
                .totalSpent(client.getTotalSpent())
                .build();
    }

    public static Client toEntity(ClientDTO dto){
        if(dto == null)return null;
        return Client.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .role(dto.getRole() != null ? UserRole.valueOf(dto.getRole()):null)
                .nom(dto.getNom())
                .totalOrders(dto.getTotalOrders())
                .totalSpent(dto.getTotalSpent())
                .fidelityLevel(dto.getFidelityLevel())
                .build();
    }
}
