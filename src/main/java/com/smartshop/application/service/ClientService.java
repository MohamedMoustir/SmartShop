package com.smartshop.application.service;

import com.smartshop.application.mapper.ClientMapper;
import com.smartshop.domain.Exception.BusinessLogicException;
import com.smartshop.domain.Exception.ResourceNotFoundException;
import com.smartshop.domain.enums.UserRole;
import com.smartshop.domain.model.Client;
import com.smartshop.infrastructure.Repository.ClientRepository;
import com.smartshop.infrastructure.Repository.UserRepository;
import com.smartshop.presontation.dto.Request.ClientRequest;
import com.smartshop.presontation.dto.Response.ClientResponse;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository  clientRepository ;
    private final UserRepository userRepository ;
    private final ClientMapper clientMapper;


    public ClientResponse createClient(ClientRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessLogicException("Email already used");
        }

        Client clientEntity = clientMapper.toEntity(request);

        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(request.getPassword(), salt);
        clientEntity.setPassword(hashedPassword);
        Client savedClient  = clientRepository.save(clientEntity);

        return clientMapper.toResponse(savedClient);

    }

    public List<ClientResponse> getAllClient(){
        return clientRepository.findAll().stream()
                .map(clientMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<ClientResponse> getClientById(Long id){
        return clientRepository.findById(id).map(clientMapper::toResponse);
    }
    
    public ClientResponse updateClient(Long id, ClientRequest request){
       Client client =  clientRepository.findById(id)
               .orElseThrow(()->  new ResourceNotFoundException("Client not found"));

        if(request.getNom() != null) {
            client.setNom(request.getNom());
        }

        if (request.getEmail() != null) {
            client.setEmail(request.getEmail());
        }

        if (request.getRole() != null) {
            try {
                client.setRole(UserRole.valueOf(request.getRole()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Role invalide: " + request.getRole());
            }
        }

        Client savedClient = clientRepository.save(client);

        return clientMapper.toResponse(savedClient);
    }

   public ClientResponse updateRole(Long id){
       Client client =  clientRepository.findById(id).orElseThrow(()->  new ResourceNotFoundException("Client not found"));
       client.setRole(UserRole.ADMIN);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toResponse(savedClient);
    }

    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client introuvable avec l'id : " + id));
        clientRepository.delete(client);
    }




}
