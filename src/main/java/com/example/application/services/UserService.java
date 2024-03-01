package com.example.application.services;

import com.example.application.data.UserRestricted;
import com.example.application.data.entities.Notification;
import com.example.application.data.entities.User;
import com.example.application.data.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    public ResponseEntity<String> getUser(String identityNumber) {
        System.out.println("GET USER RUNNED");

        User user = userRepository.findById(identityNumber).orElse(null);
        UserRestricted userRestricted = new UserRestricted(user);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        String json = "";
        try {
            json = ow.writeValueAsString(userRestricted);
            System.out.println("USER JSON OBJ: " + json);
        } catch (Exception e) {
            System.out.println(e);
        }
        //return ResponseEntity.ok().body("{\"success\": true}");


        return ResponseEntity.ok().body(json);

    }


    //UNREAD NOTIFICATIONS AND READ NOTIFICATIONS SHOULD BE SEPERATELY FETCHED
    public ResponseEntity<String> getUserNotifications(String identityNumber) {
        System.out.println("GET USER RUNNED");

        User user = userRepository.findById(identityNumber).orElse(null);
        //Set<Notification> notifications = user.getNotifications();
        Set<Notification> notifications = Set.of(new Notification());
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Notification notification = notifications.stream().findFirst().orElse(null);
        String json = "";
        try {
            json = ow.writeValueAsString(notifications);
            System.out.println("NOTIFICATION JSON OBJ: " + json);
        } catch (Exception e) {
            System.out.println(e);
        }
        //return ResponseEntity.ok().body("{\"success\": true}");


        return ResponseEntity.ok().body(json);

    }


    public List<User> findAllUsers(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return userRepository.findAll();
        } else {
            return userRepository.search(stringFilter);
        }
    }

    public long countUsers() {
        return userRepository.count();
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void saveUser(User user) {
        if (user == null) {
            System.err.println("User is null. Are you sure you have connected your form to the application?");
            return;
        }
        userRepository.save(user);
    }


    public User saveAndFlush(User user) {
        return userRepository.saveAndFlush(user);
    }

    public User findById(String identityNumber) {
        return userRepository.findById(identityNumber).get();
    }

    public boolean saveConfirmationInfo(String identityNumber) {
        try {
            User user = userRepository.findById(identityNumber).get();

            user.setLastConfirmationStatus("OK");
            user.setLastConfirmationDateTime(LocalDateTime.now());
            userRepository.save(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}