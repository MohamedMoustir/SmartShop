package com.smartshop.application.mapper;

import com.smartshop.domain.model.Commande;
import com.smartshop.domain.model.Paiement;
import com.smartshop.presontation.dto.Request.PaiementRequest;
import com.smartshop.presontation.dto.Response.PaiementResponse;

public class PaiementMapper {

    public static Paiement toEntity(PaiementRequest request){
        if(request == null ) return null;
        return Paiement.builder()
                .banque(request.getBanque())
                .commande(request.getCommandeId() != null ?
                        Commande.builder()
                                .id(request.getCommandeId())
                                .build() :null )
                .dateEcheance(request.getDateEcheance())
                .montant(request.getMontant())
                .typePaiement(request.getTypePaiement())
                .reference(request.getReference())
                .build();
    }
    public static PaiementResponse toResponse(Paiement paiement){
        if(paiement == null ) return null;
        return PaiementResponse.builder()
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
                .montantRestant(paiement.getCommande() != null ? paiement.getCommande().getMontantRestant():null)
                .status(paiement.getStatus())
                .build();
    }
}
