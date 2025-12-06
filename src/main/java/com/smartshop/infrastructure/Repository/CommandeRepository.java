package com.smartshop.infrastructure.Repository;

import com.smartshop.domain.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommandeRepository extends JpaRepository<Commande,Long> {

    List<Commande> findByClient_Id(Long clientId);
    Optional<Commande> findByIdAndClient_Id(Long id, Long clientId);

}
