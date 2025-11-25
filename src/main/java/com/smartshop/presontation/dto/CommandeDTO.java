package com.smartshop.dto;

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
public class CommandeDTO {

    private Long id;
    private Long clientId;
    private LocalDateTime date;
    private List<OrderItemDTO> orderItems;
    private Double sousTotalHT;
    private Double montantRemise;
    private Double tva;
    private Double totalTTC;
    private Double montantRestant;
    private String codePromo;
    private OrderStatus statut;
}
