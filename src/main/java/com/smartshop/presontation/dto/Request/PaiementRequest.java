package com.smartshop.presontation.dto.Request;

import com.smartshop.domain.enums.TypePaiement;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaiementRequest {

    @NotNull(message = "L'ID de la commande ne doit pas être nul.")
    @Min(value = 1, message = "L'ID de la commande doit être supérieur ou égal à 1.")
    private Long commandeId;

    @NotNull(message = "Le montant est obligatoire.")
    @DecimalMin(value = "0.01", message = "Le montant doit être strictement positif.")
    private Double montant;

    @NotNull(message = "Le type de paiement est obligatoire.")
    private TypePaiement typePaiement;


    private String reference;
    private String banque;
    private LocalDate dateEcheance;
}