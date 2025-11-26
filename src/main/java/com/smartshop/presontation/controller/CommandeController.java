package com.smartshop.presontation.controller;

import com.smartshop.application.service.CommandeService;

import com.smartshop.presontation.dto.Request.CommandeRequest;
import com.smartshop.presontation.dto.Response.CommandeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}