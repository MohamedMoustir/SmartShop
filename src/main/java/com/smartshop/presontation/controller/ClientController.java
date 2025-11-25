package com.smartshop.presontation.controller;

import com.smartshop.application.service.ClientService;
import com.smartshop.presontation.dto.Request.ClientCreateRequest;
import com.smartshop.presontation.dto.Response.ClientResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
public class ClientController {

    private final ClientService clientService;
    public static final String USER_ROLE_KEY = "USER_ROLE";
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("create")
    public ResponseEntity<?> createClient(@Valid @RequestBody ClientCreateRequest request,
                                          HttpSession session) {
        Object role = session.getAttribute(USER_ROLE_KEY);
        if (role == null || !"ADMIN".equals(role.toString())) {
            return ResponseEntity.status(403).body("Access denied");
        }
        ClientResponse result  = clientService.createClient(request);
       return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
