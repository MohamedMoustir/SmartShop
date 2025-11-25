package com.smartshop.infrastructuer.Repository;

import com.smartshop.domain.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}