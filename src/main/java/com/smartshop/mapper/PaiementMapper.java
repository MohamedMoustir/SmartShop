package com.smartshop.mapper;

import com.smartshop.dto.PaiementDTO;
import com.smartshop.model.Commande;
import com.smartshop.model.Paiement;

public class PaiementMapper {

    public static PaiementDTO toDto(Paiement paiement){
        if(paiement == null ) return null;
        return PaiementDTO.builder()
                .id(paiement.getId())
                .banque(paiement.getBanque())
                .numeroPaiement(paiement.getNumeroPaiement())
                .commandeId(paiement.getCommande() != null ? paiement.getCommande().getId():null)
                .datePaiement(paiement.getDatePaiement())
                .dateEcheance(paiement.getDateEcheance())
                .dateEncaissement(paiement.getDateEncaissement())
                .montant(paiement.getMontant())
                .typePaiement(paiement.getTypePaiement())
                .reference(paiement.getReference())
                .status(paiement.getStatus())
                .build();
    }
    public static Paiement toEntity(PaiementDTO dto){
        if(dto == null ) return null;
        return Paiement.builder()
                .id(dto.getId())
                .banque(dto.getBanque())
                .numeroPaiement(dto.getNumeroPaiement())
                .commande(dto.getCommandeId() != null ?
                        Commande.builder()
                                .id(dto.getCommandeId())
                        .build() :null )
                .datePaiement(dto.getDatePaiement())
                .dateEcheance(dto.getDateEcheance())
                .dateEncaissement(dto.getDateEncaissement())
                .montant(dto.getMontant())
                .typePaiement(dto.getTypePaiement())
                .reference(dto.getReference())
                .status(dto.getStatus())
                .build();
    }
}
