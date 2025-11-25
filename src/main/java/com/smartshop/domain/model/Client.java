package com.smartshop.domain.model;

import com.smartshop.domain.enums.CustomerTier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "clients")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Client extends User{

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerTier fidelityLevel = CustomerTier.BASIC;

    @Builder.Default
    private Double totalSpent = 0.0;

    @Builder.Default
    private Integer totalOrders = 0;

}
