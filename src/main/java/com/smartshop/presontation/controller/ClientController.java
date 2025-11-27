package com.smartshop.presontation.controller;

import com.smartshop.application.service.ClientService;
import com.smartshop.presontation.dto.Request.ClientRequest;
import com.smartshop.presontation.dto.Response.ClientResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/client")
public class ClientController {

    private final ClientService clientService;
    public static final String USER_ROLE_KEY = "USER_ROLE";


   public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createClient(@Validated(ClientRequest.OnCreate.class) @RequestBody ClientRequest request,
                                          HttpSession session) {
        Object role = session.getAttribute(USER_ROLE_KEY);
        if (role == null || !"ADMIN".equals(role.toString())) {
            return ResponseEntity.status(403).body("Access denied");
        }
        ClientResponse result  = clientService.createClient(request);
       return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<?> getAllClients(){
        List<ClientResponse> clients = clientService.getAllClient();
        return ResponseEntity.ok(clients);
    }
     @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id){
         Optional<ClientResponse> clients = clientService.getClientById(id);
        return ResponseEntity.ok(clients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(
            @PathVariable Long id,
            @Validated(ClientRequest.OnUpdate.class) @RequestBody ClientRequest request) {
        ClientResponse clientResponse = clientService.updateClient(id, request);
        return ResponseEntity.ok(clientResponse);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponse> updateRole(
            @PathVariable Long id) {
        ClientResponse clientResponse = clientService.updateRole(id);
        return ResponseEntity.ok(clientResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Client supprimé avec succès"));

    }


}
