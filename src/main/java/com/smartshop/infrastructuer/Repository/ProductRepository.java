package com.smartshop.infrastructuer.Repository;

import com.smartshop.domain.model.Product;
import com.smartshop.presontation.dto.Response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product>  findByNomContaining(@Param("nom") String nom , PageRequest pageable);
}
