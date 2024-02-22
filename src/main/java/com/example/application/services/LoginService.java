package com.example.application.services;

import com.example.application.data.entities.User;
import com.example.application.data.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticateCredentials(String identityNumber, String password) {
        System.out.println("DATA FROM MOBILE APP: " + identityNumber + " " + password);

        User user = userRepository.findById(identityNumber).orElse(null);
        if (user != null) {

            return password.equals(user.getPassword());

        }
        return false;
    }

}

