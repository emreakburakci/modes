package com.example.application.services;

import com.example.application.data.entities.User;
import com.example.application.data.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class GSMConfirmationService {

    private UserRepository userRepository;

    public GSMConfirmationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> confirmGSM(String enteredCode, String identityNumber) {
        User user = userRepository.findById(identityNumber).orElse(null);
        String generatedCode = user.getGSMConfirmationCode().toString();

        System.out.println("Users Code:" + generatedCode + "  Code From Mobile: " + enteredCode);

        if (enteredCode.equals(generatedCode)) {
            return ResponseEntity.ok().body("{\"success\": true}");
        }
        return ResponseEntity.ok().body("{\"success\": false}");
    }


    public ResponseEntity<String> requestGSMCode(@RequestParam("identityNumber") String identityNumber) {
        User user = userRepository.findById(identityNumber).orElse(null);
        String generated = String.valueOf((int) Math.floor(100000 + Math.random() * 900000));

        //CODE WILL SEND TO THE USERS GSM NUMBER
        user.setGSMConfirmationCode(generated);
        userRepository.save(user);

        return ResponseEntity.ok().body("{\"success\": " + generated + "}");
    }

    public ResponseEntity<String> clearGSMCode(String identityNumber) {
        try {
            User user = userRepository.findById(identityNumber).orElse(null);
            user.setGSMConfirmationCode("");
            userRepository.saveAndFlush(user);
            return ResponseEntity.ok().body("{\"success\": true}");
        } catch (Exception e) {
            return ResponseEntity.ok().body("{\"success\": false}");

        }
    }
}
