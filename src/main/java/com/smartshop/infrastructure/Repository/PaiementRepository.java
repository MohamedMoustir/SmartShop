package com.smartshop.infrastructure.Repository;

import com.smartshop.domain.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement,Long> {

    int countByCommandeId(Long commandeId);
}
