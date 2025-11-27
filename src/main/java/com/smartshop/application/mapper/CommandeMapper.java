package com.smartshop.application.mapper;


import com.smartshop.domain.model.Client;
import com.smartshop.domain.model.Commande;
import com.smartshop.domain.model.OrderItem;
import com.smartshop.presontation.dto.Request.CommandeRequest;
import com.smartshop.presontation.dto.Response.CommandeResponse;

import java.util.stream.Collectors;

public class CommandeMapper {

    public static Commande toEntity(CommandeRequest request){
        if (request == null) return null;
        return  Commande.builder()
                .client(Client.builder().id(request.getClientId()).build())
                .codePromo(request.getCodePromo())
                .build();
    }

        public static CommandeResponse toResponse(Commande entity){

            return CommandeResponse.builder()
                    .id(entity.getId())
                    .clientId(entity.getClient() != null ? entity.getClient().getId() : null)                    .dateCommande(entity.getDate())
                    .sousTotalHT(entity.getSousTotalHT())
                    .montantHTApresRemise(entity.getMontantHTApresRemise())
                    .tva(entity.getTva())
                    .totalTTC(entity.getTotalTTC())
                    .montantRemise(entity.getMontantRemise())
                    .codePromoUtilise(entity.getCodePromo())
                    .statut(entity.getStatut())
                    .montantRestant(entity.getMontantRestant())
                    .items(entity.getOrderItems() != null ?
                            entity.getOrderItems().stream()
                                    .map(OrderItemMapper::toResponse)
                                    .collect(Collectors.toList())
                            : null)
                    .build();

        }
}
