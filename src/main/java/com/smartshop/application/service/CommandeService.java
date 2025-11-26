package com.smartshop.application.service;

import com.smartshop.application.mapper.CommandeMapper;
import com.smartshop.domain.Excption.InvalidCredentialsException;
import com.smartshop.domain.Excption.InvalidOrderStateException;
import com.smartshop.domain.enums.OrderStatus;
import com.smartshop.domain.model.Client;
import com.smartshop.domain.model.Commande;
import com.smartshop.domain.model.OrderItem;
import com.smartshop.domain.model.Product;
import com.smartshop.infrastructuer.Repository.ClientRepository;
import com.smartshop.infrastructuer.Repository.CommandeRepository;
import com.smartshop.infrastructuer.Repository.ProductRepository;
import com.smartshop.presontation.dto.Request.CommandeRequest;
import com.smartshop.presontation.dto.Request.OrderItemRequest;
import com.smartshop.presontation.dto.Response.CommandeResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.smartshop.application.mapper.CommandeMapper.toResponse;

@Service
@RequiredArgsConstructor
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final FidelityService fidelityService;
    String  CODEPROMO = "PROMO-2025";

    @Transactional
    public  CommandeResponse createOrder(CommandeRequest request){
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(()-> new InvalidCredentialsException("Client not found"));

            List<OrderItem> orderItems = validateAndPrepareItems(request.getItems());
            double sousTotalHT = calculateSousTotal(orderItems);
        double totalRemise = calculateTotalRemise(client, sousTotalHT, request.getCodePromo());
        Commande savedCommande = buildAndSaveCommande(client, orderItems, sousTotalHT, totalRemise, request.getCodePromo());
        return CommandeMapper.toResponse(savedCommande);
    }

    private List<OrderItem> validateAndPrepareItems(List<OrderItemRequest> orderItemRequests){
        List<OrderItem>  items = new ArrayList<>();

        for(OrderItemRequest req : orderItemRequests){
            Product product = productRepository.findById(req.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produit introuvable ID: " + req.getProductId()));
            if(product.getStockDisponible() < req.getQuantity()){
                throw new RuntimeException("Stock insuffisant pour : " + product.getNom());
            }
            double lineTotal = product.getPrixUnitaire() * req.getQuantity();
            items.add(OrderItem.builder()
                    .product(product)
                    .quantity(req.getQuantity())
                    .prixUnitaireLigne(product.getPrixUnitaire())
                            .totalLigne(lineTotal)
                    .build());
        }

        return items;
    }
    private double calculateSousTotal(List<OrderItem> items){
        return items.stream()
                .mapToDouble(item->item.getPrixUnitaireLigne() * item.getQuantity())
                .sum();

    }
    private double calculateTotalRemise(Client client, double sousTotal, String codePromo){
       double  discountFidelity = fidelityService.calculateDiscount(client ,sousTotal);
        double discountPromo = 0.0;
        if (CODEPROMO.equals(codePromo)) {
            discountPromo = sousTotal * 0.05;
        }

        return discountFidelity + discountPromo;
    }
    private Commande buildAndSaveCommande(Client client, List<OrderItem> items, double sousTotal, double totalRemise, String codePromo) {
        double montantHTApresRemise = Math.max(0,sousTotal-totalRemise);
        double tva = montantHTApresRemise * 0.20;
        double totalTTC = montantHTApresRemise + tva;
        Commande commande = Commande.builder()
                .client(client)
                .codePromo(codePromo)
                .date(LocalDateTime.now())
                .statut(OrderStatus.PENDING)
                .montantHTApresRemise(montantHTApresRemise)
                .sousTotalHT(sousTotal)
                .montantRemise(totalRemise)
                .tva(tva)
                .totalTTC(totalTTC)
                .build();

        items.forEach(item->item.setCommande(commande));
        commande.setOrderItems(items);

        return commandeRepository.save(commande);
    }

    @Transactional
  public CommandeResponse validateOrder(Long orderId){
       Commande commande = commandeRepository.findById(orderId)
               .orElseThrow(()->new InvalidCredentialsException("Order not found"));

        if (!OrderStatus.PENDING.equals(commande.getStatut())) {
            throw new InvalidOrderStateException("Impossible de valider cette commande. Statut actuel: " + commande.getStatut());
        }

        for (OrderItem item : commande.getOrderItems()){
            Product p = item.getProduct();
            if(p.getStockDisponible() < item.getQuantity()){
             commande.setStatut(OrderStatus.REJECTED);
             commandeRepository.save(commande);
                throw new RuntimeException("Stock épuisé pour " + p.getNom());
            }
            p.setStockDisponible(p.getStockDisponible() - item.getQuantity());
            productRepository.save(p);
        }

        Client client  = commande.getClient();
        client.setTotalOrders(client.getTotalOrders() + 1);
        client.setTotalSpent(client.getTotalSpent() + commande.getTotalTTC());
        fidelityService.updateClientLevel(client);
        commande.setStatut(OrderStatus.CONFIRMED);
        return toResponse(commandeRepository.save(commande));
    }


}
