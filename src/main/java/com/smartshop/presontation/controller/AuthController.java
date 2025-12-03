package com.smartshop.presontation.controller;

import com.smartshop.application.service.AuthService;
import com.smartshop.application.service.ClientService;
import com.smartshop.domain.Exception.ForbiddenException;
import com.smartshop.domain.Exception.ResourceNotFoundException;
import com.smartshop.domain.model.Client;
import com.smartshop.presontation.dto.LoginDTO;
import com.smartshop.domain.model.User;
import com.smartshop.presontation.dto.Response.ClientResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authServise;
    private final ClientService clientService;
    public static final String USER_ID_KEY = "USER_ID";
    public static final String USER_ROLE_KEY = "USER_ROLE";
    public AuthController(AuthService authServise, ClientService clientService) {
        this.authServise = authServise;
        this.clientService = clientService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid  @RequestBody LoginDTO loginDTO , HttpSession session){
      User UserAuthenticate = authServise.authenticate(
              loginDTO.getEmail(),
              loginDTO.getPassword()
      );

      session.setAttribute(USER_ID_KEY,UserAuthenticate.getId());
      session.setAttribute(USER_ROLE_KEY,UserAuthenticate.getRole().name());
      return ResponseEntity.ok(Map.of(
              "message", "Connexion réussie.",
              "userId" ,UserAuthenticate.getId(),
              "role" ,UserAuthenticate.getRole().name()
      ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session){

        session.invalidate();

        return ResponseEntity.ok(Map.of("message", "Déconnexion réussie."));
    }

    @GetMapping("/profile")
    public ResponseEntity<ClientResponse> getMyProfile(HttpSession session) {

        Long currentUserId = (Long) session.getAttribute(USER_ID_KEY);

        String role = session.getAttribute(USER_ROLE_KEY).toString();
        if (!"CLIENT".equals(role)) {
            throw new ForbiddenException("Seul un client a un profil 'Client'.");
        }

        ClientResponse response = clientService.getCurrentProfile(currentUserId);

        return ResponseEntity.ok(response);
    }


}
