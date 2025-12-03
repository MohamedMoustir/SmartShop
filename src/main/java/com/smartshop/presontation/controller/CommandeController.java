package com.smartshop.presontation.controller;

import com.smartshop.application.service.CommandeService;

import com.smartshop.domain.enums.OrderStatus;
import com.smartshop.domain.model.Commande;
import com.smartshop.presontation.dto.Request.CommandeRequest;
import com.smartshop.presontation.dto.Response.CommandeResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.smartshop.presontation.controller.AuthController.USER_ID_KEY;
import static com.smartshop.presontation.controller.AuthController.USER_ROLE_KEY;

@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
public class CommandeController {

    private final CommandeService commandeService;


    @PostMapping
    public ResponseEntity<CommandeResponse> createOrder(
            @RequestBody @Valid CommandeRequest request
    ) {
        CommandeResponse response = commandeService.createOrder(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<CommandeResponse> confirmOrder(@PathVariable Long id) {
        CommandeResponse response = commandeService.validateOrder(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders(){
        List<CommandeResponse> response = commandeService.getAllOrder();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandeResponse> getOrderById(@PathVariable("id") Long id){
        CommandeResponse response = commandeService.getOrderById(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/my_orders")
    public ResponseEntity<?> getMyOrder(HttpSession session){

        if (session.getAttribute(USER_ID_KEY) == null) {
            return ResponseEntity.status(401).body(Map.of("message", "Non authentifié."));
        }

        List<CommandeResponse> response = commandeService.getMyOrder((Long) session.getAttribute(USER_ID_KEY));
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}/my_orders")
    public ResponseEntity<?> getMyOrderById(@PathVariable("id") Long id , HttpSession session){

        if (session.getAttribute(USER_ID_KEY) == null) {
            return ResponseEntity.status(401).body(Map.of("message", "Non authentifié."));
        }
        Long userId = (Long) session.getAttribute(USER_ID_KEY);
        CommandeResponse response = commandeService.getMyOrderById(id,userId);
        return ResponseEntity.ok(response);
    }



}