package com.smartshop.presontation.dto;

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
public class PaiementDTO {
    private Long id;
    private Long commandeId;
    private Integer numeroPaiement;
    private Double montant;
    private TypePaiement typePaiement;
    private LocalDate datePaiement;
    private LocalDate dateEncaissement;
    private PaymentStatus status;
    private String reference;
    private String banque;
    private LocalDate dateEcheance;
}
