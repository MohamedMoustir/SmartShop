    package com.smartshop.domain.model;

    import com.smartshop.domain.enums.OrderStatus;
    import com.smartshop.presontation.dto.Response.OrderItemResponse;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.List;

    @Entity
    @Table(name = "Order")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class Order extends AbstractEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "client_id", nullable = false)
        private Client client;

        private LocalDateTime date;

        //Composition
        @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<OrderItem> orderItems = new ArrayList<>();

        private Double sousTotalHT;
        private Double montantRemise;
        private Double tva;
        private Double totalTTC;
        private Double montantHTApresRemise;
        private Double montantRestant;
        private String codePromo;
        @Enumerated(EnumType.STRING)
        private OrderStatus statut;

        private LocalDateTime createAt;
        private LocalDateTime updatedAt;
    }
