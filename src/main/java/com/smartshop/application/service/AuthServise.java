package com.smartshop.application.service;

import com.smartshop.domain.Excption.InvalidCredentialsException;
import com.smartshop.domain.model.User;
import com.smartshop.infrastructuer.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthServise {

    private final UserRepository userRepository;
    public AuthServise(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String email , String password){
      User user = userRepository.findByEmail(email)
              .orElseThrow(()->new InvalidCredentialsException("email d'utilisateur ou mot de passe incorrect."));

       if(!user.getPassword().equals(password)){
           throw new InvalidCredentialsException("email d'utilisateur ou mot de passe incorrect.");
       }
        return user;
    }
}
