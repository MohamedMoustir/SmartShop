//package com.smartshop.TestClientService;
//
//import com.smartshop.application.mapper.ClientMapper;
//import com.smartshop.application.service.ClientService;
//import com.smartshop.domain.enums.UserRole;
//import com.smartshop.domain.model.Client;
//import com.smartshop.infrastructure.Repository.ClientRepository;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mindrot.jbcrypt.BCrypt;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.mockito.Mockito.*;
//
//public class testClient {
//
//
//    @Mock
//    private ClientMapper clientMapper ;
//
//    @InjectMocks
//    private ClientRepository clientRepository;
//
//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.openMocks(this);
//    }
//
////    @Test
////    void create_client_test(){
////     Client = Client.builder()
////             .id(1L)
////             .nom("mohamed")
////             .email("mohamed@gmail.com")
////             .password("test12z34")
////             .totalOrders(0)
////             .totalSpent(0.00)
////             .build();
////
////        String salt = BCrypt.gensalt();
////        String hashedPassword = BCrypt.hashpw(client.getPassword(),salt);
////        client.setPassword(hashedPassword);
////        when(clientRepository.save(client)).thenReturn(client);
////        verify(clientRepository ,times(1)).save(client);
////
////    }
//
//
//
//}
