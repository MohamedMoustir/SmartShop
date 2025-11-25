package com.smartshop.model;

import com.smartshop.enums.PaymentStatus;
import com.smartshop.enums.TypePaiement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "paiements")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    private Integer numeroPaiement;

    private Double montant;

    @Enumerated(EnumType.STRING)
    private TypePaiement typePaiement;

    private LocalDate datePaiement;
    private LocalDate dateEncaissement;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String reference;
    private String banque;
    private LocalDate dateEcheance;
}
