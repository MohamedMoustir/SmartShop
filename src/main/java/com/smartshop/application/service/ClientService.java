package com.smartshop.application.service;

import com.smartshop.application.mapper.ClientMapper;
import com.smartshop.domain.model.Client;
import com.smartshop.infrastructuer.Repository.ClientRepository;
import com.smartshop.infrastructuer.Repository.UserRepository;
import com.smartshop.presontation.dto.ClientDTO;
import com.smartshop.presontation.dto.Request.ClientCreateRequest;
import com.smartshop.presontation.dto.Response.ClientResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository  clientRepository ;
    private final UserRepository userRepository ;

    public ClientService(ClientRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    public ClientResponse createClient(ClientCreateRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already used");
        }

        Client clientEntity = ClientMapper.toEntity(request);

        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(request.getPassword(), salt);
        clientEntity.setPassword(hashedPassword);
        Client savedClient  = clientRepository.save(clientEntity);

        return ClientMapper.toResponse(savedClient);

    }
}
