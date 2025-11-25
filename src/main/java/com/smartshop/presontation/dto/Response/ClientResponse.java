package com.smartshop.presontation.dto.Response;

import com.smartshop.domain.enums.CustomerTier;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientResponse {
    private Long id;
    private String nom;
    private String email;
    private String role;
    private CustomerTier fidelityLevel;
    private Double totalSpent;
    private Integer totalOrders;
}