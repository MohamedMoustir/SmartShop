package com.smartshop.presontation.dto.Response;

import com.smartshop.domain.enums.PaymentStatus;
import com.smartshop.domain.enums.TypePaiement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaiementResponse {

    private Long id;
    private Long commandeId;

    private Integer numeroPaiement;
    private Double montant;
    private TypePaiement typePaiement;

    private PaymentStatus status;

    private LocalDate datePaiement;
    private LocalDate dateEncaissement;
    private LocalDate dateEcheance;

    private Double montantRestant;
    private String reference;
    private String banque;
}
