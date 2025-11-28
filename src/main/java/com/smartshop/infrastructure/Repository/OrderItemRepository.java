package com.smartshop.infrastructure.Repository;

import com.smartshop.domain.model.Commande;
import com.smartshop.domain.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository  extends JpaRepository<OrderItem,Long> {
    Boolean existsByProduct_Id(Long id);

}
