package com.smartshop.application.service;

import com.smartshop.domain.Exception.InvalidCredentialsException;
import com.smartshop.domain.model.User;
import com.smartshop.infrastructure.Repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String email , String password){
      User user = userRepository.findByEmail(email)
              .orElseThrow(()->new InvalidCredentialsException("email d'utilisateur ou mot de passe incorrect."));
           boolean passwordMatches = BCrypt.checkpw(password,user.getPassword());
       if(!passwordMatches){
           throw new InvalidCredentialsException("email d'utilisateur ou mot de passe incorrect.");
       }
        return user;
    }
}
