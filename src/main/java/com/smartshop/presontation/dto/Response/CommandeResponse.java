package com.smartshop.presontation.dto.Response;

import com.smartshop.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private Long clientId;

    private LocalDateTime dateCommande;
    private OrderStatus statut;

    private List<OrderItemResponse> items;

    private Double sousTotalHT;
    private Double montantRemise;
    private Double montantHTApresRemise;
    private Double tva;
    private Double totalTTC;

    private String codePromoUtilise;
}