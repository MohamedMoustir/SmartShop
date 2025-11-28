package com.smartshop.presontation.controller;

import com.smartshop.application.service.PaiementService;
import com.smartshop.domain.enums.PaymentStatus;
import com.smartshop.presontation.dto.Request.PaiementRequest;
import com.smartshop.presontation.dto.Response.PaiementResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final PaiementService paiementService;
    @PostMapping
    public ResponseEntity<PaiementResponse> creerPaiement(@Valid @RequestBody PaiementRequest request) {
        PaiementResponse response = paiementService.enregistrerPaiement(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("{id}/status")
    public ResponseEntity<PaiementResponse> mettreAJourStatutPaiement(
            @Valid @PathVariable("id") Long id
            ,  @RequestParam("nouveauStatut") PaymentStatus nouveauStatut ) {
        PaiementResponse response = paiementService.mettreAJourStatutPaiement(id ,nouveauStatut);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
