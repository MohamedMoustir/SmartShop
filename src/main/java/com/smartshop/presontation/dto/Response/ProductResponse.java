package com.smartshop.presontation.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String nom;
    private Double prixUnitaire;
    private Integer stockDisponible;
    private boolean deleted;

}
