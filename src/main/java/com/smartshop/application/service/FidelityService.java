package com.smartshop.application.service;

import com.smartshop.domain.enums.CustomerTier;
import com.smartshop.domain.model.Client;
import com.smartshop.infrastructuer.Repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class FidelityService {

    private final ClientRepository clientRepository ;

    private static final double SILVER_THRESHOLD = 500.0;
    private static final double SILVER_DISCOUNT = 0.05;

    private static final double GOLD_THRESHOLD = 800.0;
    private static final double GOLD_DISCOUNT = 0.10;

    private static final double PLATINUM_THRESHOLD = 1200.0;
    private static final double PLATINUM_DISCOUNT = 0.15;

    public FidelityService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void updateClientLevel(Client client) {
        int orders = client.getTotalOrders();
        double spent = client.getTotalSpent();

        if(orders >= 20 || spent >= 15000){
            client.setFidelityLevel(CustomerTier.PLATINUM);
        }else if(orders>= 10 || spent >= 5000){
            client.setFidelityLevel(CustomerTier.GOLD);
        }else if(orders >= 3 || spent >= 1000){
            client.setFidelityLevel(CustomerTier.SILVER);
        }else{
            client.setFidelityLevel(CustomerTier.BASIC);
        }
        clientRepository.save(client);
    }

    public double calculateDiscount(Client client, double orderSubTotal) {

        if(client == null || client.getFidelityLevel() == null){
            return 0.0;
        }

        switch (client.getFidelityLevel()){
            case SILVER:
                if(orderSubTotal >= SILVER_THRESHOLD) return orderSubTotal * SILVER_DISCOUNT;
                break;

            case GOLD :
                if(orderSubTotal >= GOLD_THRESHOLD) return orderSubTotal * GOLD_DISCOUNT;
                break;

            case PLATINUM :
                if(orderSubTotal >= PLATINUM_THRESHOLD) return orderSubTotal * PLATINUM_DISCOUNT;
                break;

            case BASIC:
            default:
                return 0.0;
        }

        return 0.0;
    }
}
