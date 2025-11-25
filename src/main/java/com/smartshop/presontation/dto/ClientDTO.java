package com.smartshop.dto;

import com.smartshop.domain.enums.CustomerTier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    private Long id;
    private String nom;
    private String role;
    private String email;
    private CustomerTier fidelityLevel;
    private Double totalSpent;
    private Integer totalOrders;

}
