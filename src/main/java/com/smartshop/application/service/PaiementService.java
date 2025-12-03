package com.smartshop.application.service;

import com.smartshop.application.mapper.PaiementMapper;
import com.smartshop.domain.Exception.BusinessLogicException;
import com.smartshop.domain.Exception.CustomValidationException;
import com.smartshop.domain.Exception.ResourceNotFoundException;
import com.smartshop.domain.enums.PaymentStatus;
import com.smartshop.domain.enums.TypePaiement;
import com.smartshop.domain.model.Commande;
import com.smartshop.domain.model.Paiement;
import com.smartshop.infrastructure.Repository.CommandeRepository;
import com.smartshop.infrastructure.Repository.PaiementRepository;
import com.smartshop.presontation.dto.Request.PaiementRequest;
import com.smartshop.presontation.dto.Response.PaiementResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaiementService {

    private final PaiementRepository paiementRepository;
    private final CommandeRepository commandeRepository;
    private final PaiementMapper paiementMapper ;

    public PaiementResponse enregistrerPaiement(PaiementRequest request){
        Commande commande = commandeRepository.findById(request.getCommandeId())
                .orElseThrow(()-> new ResourceNotFoundException("Commande not found"));
       if(request.getMontant() > commande.getMontantRestant()){
           throw new CustomValidationException("Le montant du paiement dépasse le montant restant dû (" + commande.getMontantRestant() + " DH)");
       }
       if(request.getTypePaiement() == TypePaiement.ESPECES && request.getMontant() > 20000){
           throw new CustomValidationException("Le paiement en espèces ne peut pas dépasser 20,000 DH (Loi Art. 193 CGI)");
       }

       Paiement paiement = paiementMapper.toEntity(request);
       paiement.setCommande(commande);
       paiement.setDatePaiement(LocalDate.now());

        int numeroPaiement = paiementRepository.countByCommandeId(commande.getId())+1;
        paiement.setNumeroPaiement(numeroPaiement);


        if (request.getTypePaiement() == TypePaiement.ESPECES) {
            paiement.setStatus(PaymentStatus.ENCAISSE);
            paiement.setDateEncaissement(LocalDate.now());
        }else{
            paiement.setStatus(PaymentStatus.EN_ATTENTE);
        }

        double nouveauReste = commande.getMontantRestant() - paiement.getMontant();
        if(nouveauReste < 0) nouveauReste = 0.0;
        commande.setMontantRestant(nouveauReste);

        paiementRepository.save(paiement);
        commandeRepository.save(commande);
        return paiementMapper.toResponse(paiement);
    }

    @Transactional
    public PaiementResponse mettreAJourStatutPaiement(Long paiementId, PaymentStatus nouveauStatut) {
        Paiement paiement = paiementRepository.findById(paiementId)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement introuvable"));

        PaymentStatus ancienStatut = paiement.getStatus();

        if(nouveauStatut == PaymentStatus.REJETE && ancienStatut !=  PaymentStatus.REJETE){
            gererImpactCommande(paiement.getCommande(),paiement.getMontant(),true);
        }

        if(ancienStatut == PaymentStatus.REJETE && nouveauStatut ==  PaymentStatus.ENCAISSE){
            gererImpactCommande(paiement.getCommande(),paiement.getMontant(),false);
        }

        if(nouveauStatut == PaymentStatus.ENCAISSE){
      paiement.setDateEncaissement(LocalDate.now());

        }
        paiement.setStatus(nouveauStatut);
        Paiement saved = paiementRepository.save(paiement);

        return paiementMapper.toResponse(saved);
    }

    public void gererImpactCommande(Commande commande,Double montant, boolean estUnRejet){
        double montantRestant = commande.getMontantRestant();
        if(estUnRejet){
            montantRestant += montant;
            if(montantRestant > commande.getTotalTTC()) montantRestant = commande.getTotalTTC();

        }else{
            montantRestant -= montant;
            if (montantRestant < 0) montantRestant = 0.0;
        }
        commande.setMontantRestant(montantRestant);
        commandeRepository.save(commande);
    }



}
