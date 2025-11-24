package com.smartshop.mapper;


import com.smartshop.dto.CommandeDTO;
import com.smartshop.model.Client;
import com.smartshop.model.Commande;

import java.util.stream.Collectors;

public class CommandeMapper {

    public static CommandeDTO toDto(Commande commande){
        if (commande == null) return null;
        return CommandeDTO.builder()
                .id(commande.getId())
                .clientId(commande.getClient() != null ? commande.getClient().getId() :null)
                .date(commande.getDate())
                .orderItems(commande.getOrderItems() != null ?
                        commande.getOrderItems().stream()
                                .map(OrderItemMapper::toDto)
                                .collect(Collectors.toList()):null)
                .sousTotalHT(commande.getSousTotalHT())
                .montantRestant(commande.getMontantRestant())
                .tva(commande.getTva())
                .totalTTC(commande.getTotalTTC())
                .montantRemise(commande.getMontantRemise())
                .codePromo(commande.getCodePromo())
                .statut(commande.getStatut())
                .build();
    }

        public static Commande toEntity(CommandeDTO dto){

            Commande commande = Commande.builder()
                    .id(dto.getId())
                    .client(dto.getClientId() != null ?
                            Client.builder()
                                    .id(dto.getClientId())
                                    .build():null)
                    .date(dto.getDate())
                    .sousTotalHT(dto.getSousTotalHT())
                    .montantRestant(dto.getMontantRestant())
                    .tva(dto.getTva())
                    .totalTTC(dto.getTotalTTC())
                    .montantRemise(dto.getMontantRemise())
                    .codePromo(dto.getCodePromo())
                    .statut(dto.getStatut())
                    .build();

            if(dto.getOrderItems() != null){
                commande.setOrderItems(dto.getOrderItems().stream()
                        .map(itemDTO->{
                            var item = OrderItemMapper.toEntity(itemDTO);
                            item.setCommande(commande);
                            return item;
                        }).collect(Collectors.toList()));
            }
            return commande;
        }
}
