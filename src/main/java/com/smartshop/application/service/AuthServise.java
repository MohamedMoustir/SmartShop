package com.smartshop.service;

import com.smartshop.dto.LoginDTO;
import com.smartshop.model.User;
import com.smartshop.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthServise {

    private final UserRepository userRepository;
    public AuthServise(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String email , String password){
      User user = userRepository.findByEmail(email)
              .orElseThrow(()->new RuntimeException("email d'utilisateur ou mot de passe incorrect."));

       if(!user.getPassword().equals(password)){
           throw new RuntimeException("email d'utilisateur ou mot de passe incorrect.");
       }
        return user;
    }
}
