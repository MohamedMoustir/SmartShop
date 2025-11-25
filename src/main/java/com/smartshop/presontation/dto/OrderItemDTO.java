package com.smartshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Long commandeId;
    private Long productId;
    private Integer quantity;
    private Double prixUnitaireLigne;
    private Double totalLigne;
}
