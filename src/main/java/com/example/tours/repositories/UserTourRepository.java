package com.example.tours.repositories;

import com.example.tours.model.UserTour;
import com.example.tours.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserTourRepository extends JpaRepository<UserTour, Long>{


    @Query("SELECT ut FROM UserTour ut JOIN ut.user u WHERE u.id = ?1")
    public List<UserTour> findReservationsById(Long id);
}
