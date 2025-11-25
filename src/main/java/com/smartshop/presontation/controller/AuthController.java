package com.smartshop.presontation.controller;

import com.smartshop.presontation.dto.LoginDTO;
import com.smartshop.domain.model.User;
import com.smartshop.application.service.AuthServise;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthServise authServise;
    public static final String USER_ID_KEY = "USER_ID";
    public static final String USER_ROLE_KEY = "USER_ROLE";
    public AuthController(AuthServise authServise) {
        this.authServise = authServise;
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

    @GetMapping("/me")
    public ResponseEntity<?> getSessionInfo(HttpSession session) {
        if (session.getAttribute(USER_ID_KEY) == null) {
            return ResponseEntity.status(401).body(Map.of("message", "Non authentifié."));
        }
        return ResponseEntity.ok(Map.of(
                "userId", session.getAttribute(USER_ID_KEY),
                "role", session.getAttribute(USER_ROLE_KEY)
        ));
    }


}
