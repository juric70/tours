package com.example.tours.repositories;

import com.example.tours.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TourRepository extends JpaRepository<Tour,Long> {
    @Query("SELECT COUNT(ut) FROM UserTour ut JOIN ut.tour t WHERE t.id = ?1 and t.rating is not null ")
    public int countRatings(Long id);

    @Query("SELECT SUM(ut.rating) FROM UserTour ut JOIN ut.tour t WHERE t.id = ?1 and t.rating is not null")
    public int sumRaitings(Long id);
}
