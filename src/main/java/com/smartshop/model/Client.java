package com.smartshop.model;

import com.smartshop.enums.CustomerTier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerTier fidelityLevel = CustomerTier.BASIC;

    private Double totalSpent = 0.0;
    private Integer totalOrders = 0;

}
