package com.smartshop.application.mapper;

import com.smartshop.domain.model.Paiement;
import com.smartshop.presontation.dto.Request.PaiementRequest;
import com.smartshop.presontation.dto.Response.PaiementResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaiementMapper {

    @Mapping(source = "commandeId", target = "commande.id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numeroPaiement", ignore = true)
    @Mapping(target = "datePaiement", ignore = true)
    @Mapping(target = "dateEncaissement", ignore = true)
    @Mapping(target = "status", ignore = true)
    Paiement toEntity(PaiementRequest request);

    @Mapping(source = "commande.id", target = "commandeId")
    @Mapping(source = "commande.montantRestant", target = "montantRestant")
    PaiementResponse toResponse(Paiement paiement);
}