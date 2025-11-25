package com.smartshop.presontation.config;

import com.smartshop.domain.enums.UserRole;
import com.smartshop.domain.model.Admin;
import com.smartshop.infrastructuer.Repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);

    private final AdminRepository adminRepository ;

    @Override
    public void run(String... args) throws Exception {
    if(adminRepository.count() == 0){
       Admin admin = Admin.builder()
               .nom("Admin")
                .email("admin@smartshop.com")
                .password("admin123")
                .role(UserRole.ADMIN)
                .build();
        adminRepository.save(admin);
        logger.info("Admin user created");

    }

    }
}
