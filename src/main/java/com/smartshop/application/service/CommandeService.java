package com.smartshop.application.service;

import com.smartshop.application.mapper.CommandeMapper;
import com.smartshop.application.mapper.ProductMapper;
import com.smartshop.domain.Exception.*;
import com.smartshop.domain.enums.OrderStatus;
import com.smartshop.domain.model.*;
import com.smartshop.infrastructure.Repository.*;
import com.smartshop.presontation.dto.Request.CommandeRequest;
import com.smartshop.presontation.dto.Request.OrderItemRequest;
import com.smartshop.presontation.dto.Response.CommandeResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final FidelityService fidelityService;
    private final CommandeMapper commandeMapper;
    private final OrderItemRepository orderItemRepository;
    private final String  CODEPROMO = "PROMO-2025";

    @Transactional
    public  CommandeResponse createOrder(CommandeRequest request){
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(()-> new ResourceNotFoundException("Client not found"));

            List<OrderItem> orderItems = validateAndPrepareItems(request.getItems());
            double sousTotalHT = calculateSousTotal(orderItems);
            double totalRemise = calculateTotalRemise(client, sousTotalHT, request.getCodePromo());
            Commande savedCommande = buildAndSaveCommande(client, orderItems, sousTotalHT, totalRemise, request.getCodePromo());
        return commandeMapper.toResponse(savedCommande);
    }

    private List<OrderItem> validateAndPrepareItems(List<OrderItemRequest> orderItemRequests){
        List<OrderItem>  items = new ArrayList<>();

        for(OrderItemRequest req : orderItemRequests){
            Product product = productRepository.findById(req.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable ID: " + req.getProductId()));
            if(product.getStockDisponible() < req.getQuantity()){
                throw new BusinessLogicException("Stock insuffisant pour : " + product.getNom());
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
                .montantRestant(totalTTC)
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
               .orElseThrow(()->new ResourceNotFoundException("Order not found"));

        if (!OrderStatus.PENDING.equals(commande.getStatut())) {
            throw new InvalidOrderStateException("Impossible de valider cette commande. Statut actuel: " + commande.getStatut());
        }
        if (commande.getMontantRestant() > 0.01) {
            throw new BusinessLogicException("Impossible de confirmer : La commande n'est pas totalement payée. Reste à payer : " + commande.getMontantRestant() + " DH");
        }
        for (OrderItem item : commande.getOrderItems()){
            Product p = item.getProduct();
            if(p.getStockDisponible() < item.getQuantity()){
             commande.setStatut(OrderStatus.REJECTED);
                Commande saved = commandeRepository.save(commande);
                return commandeMapper.toResponse(saved);
            }
            p.setStockDisponible(p.getStockDisponible() - item.getQuantity());
            productRepository.save(p);
        }

        Client client  = commande.getClient();
        client.setTotalOrders(client.getTotalOrders() + 1);
        client.setTotalSpent(client.getTotalSpent() + commande.getTotalTTC());
        fidelityService.updateClientLevel(client);
        commande.setStatut(OrderStatus.CONFIRMED);
        return commandeMapper.toResponse(commandeRepository.save(commande));
    }

    public List<CommandeResponse> getAllOrder(){
        return  commandeRepository.findAll().stream()
                .map(commandeMapper::toResponse)
                .collect(Collectors.toList());

   }

    public CommandeResponse getOrderById(Long id){
            Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande not found"));
        return commandeMapper.toResponse(commande);

    }

  public  List<CommandeResponse> getMyOrder(Long id){

      return commandeRepository.findByClient_Id(id).stream()
              .map(commandeMapper::toResponse)
              .collect(Collectors.toList());

  }

    public CommandeResponse getMyOrderById(Long id ,Long clientId){
         Commande commande = commandeRepository.findByIdAndClient_Id(id,clientId)
                 .orElseThrow(() -> new ForbiddenException("You do not have permission to access this order"));
           return commandeMapper.toResponse(commande);
    }






}
