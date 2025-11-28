package com.smartshop.presontation.dto.Request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandeRequest {

    @NotNull(message = "Le Client ID est obligatoire")
    private Long clientId;

    @NotEmpty(message = "La commande doit contenir au moins un produit")
    @Valid
    private List<OrderItemRequest> items;

    @Pattern(regexp = "^PROMO-\\d{4}$", message = "Code promo invalide")
    private String codePromo;
}