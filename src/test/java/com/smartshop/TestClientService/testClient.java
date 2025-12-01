package com.smartshop.TestClientService;

import com.smartshop.application.mapper.ClientMapper;
import com.smartshop.application.service.ClientService;
import com.smartshop.domain.Exception.BusinessLogicException;
import com.smartshop.domain.enums.UserRole;
import com.smartshop.domain.model.Client;
import com.smartshop.infrastructure.Repository.ClientRepository;
import com.smartshop.infrastructure.Repository.UserRepository;
import com.smartshop.presontation.dto.Request.ClientRequest;
import com.smartshop.presontation.dto.Response.ClientResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientService clientService;

    @Test
    void createClient_Success() {
        ClientRequest request = ClientRequest.builder()
                .email("test@smartshop.ma")
                .nom("Alpha Societe")
                .password("pass123")
                .build();

        Client clientEntity = Client.builder()
                .id(1L)
                .email("test@smartshop.ma")
                .build();

        Client savedClient = Client.builder()
                .id(1L)
                .email("test@smartshop.ma")
                .password("hashed_pass")
                .build();

        ClientResponse expectedResponse = ClientResponse.builder()
                .id(1L)
                .email("test@smartshop.ma")
                .build();

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(clientMapper.toEntity(any(ClientRequest.class))).thenReturn(clientEntity);
        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);
        when(clientMapper.toResponse(any(Client.class))).thenReturn(expectedResponse);

        ClientResponse result = clientService.createClient(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    void createClient_EmailExists_ThrowsException() {
        ClientRequest request = ClientRequest.builder()
                .email("exist@smartshop.ma")
                .build();

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(BusinessLogicException.class, () -> clientService.createClient(request));
        verify(clientRepository, never()).save(any());
    }

    @Test
    void getClientById_Success() {
        Long id = 1L;
        Client client = Client.builder().id(id).build();
        ClientResponse response = ClientResponse.builder().id(id).build();

        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        when(clientMapper.toResponse(client)).thenReturn(response);

        Optional<ClientResponse> result = clientService.getClientById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    void updateClient_Success() {
        Long id = 1L;

        ClientRequest updateRequest = ClientRequest.builder()
                .nom("New Name")
                .role("ADMIN")
                .build();

        Client existingClient = Client.builder()
                .id(id)
                .nom("Old Name")
                .role(UserRole.CLIENT)
                .build();

        Client updatedClient = Client.builder()
                .id(id)
                .nom("New Name")
                .role(UserRole.ADMIN)
                .build();

        ClientResponse response = ClientResponse.builder().id(id).nom("New Name").build();

        when(clientRepository.findById(id)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(any(Client.class))).thenReturn(updatedClient);
        when(clientMapper.toResponse(any(Client.class))).thenReturn(response);
        ClientResponse result = clientService.updateClient(id, updateRequest);
        assertEquals("New Name", result.getNom());
        verify(clientRepository).save(existingClient);
    }

    @Test
    void deleteClient_Success() {
        Long id = 1L;
        Client client = Client.builder().id(id).build();
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        clientService.deleteClient(id);
        verify(clientRepository).delete(client);
    }
}