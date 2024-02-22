package com.example.application.data.repositories;

import com.example.application.data.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from Notification n " +
            "where lower(n.title) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(n.content) like lower(concat('%', :searchTerm, '%'))")
    List<Notification> search(@Param("searchTerm") String searchTerm);
}
