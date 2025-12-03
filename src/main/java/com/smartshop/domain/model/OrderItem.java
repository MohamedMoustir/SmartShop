package com.smartshop.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orderItems")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Commande commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
    private Double prixUnitaireLigne;
    private Double totalLigne;

    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
}
