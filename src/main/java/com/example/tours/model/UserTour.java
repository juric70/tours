package com.example.tours.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "userstours")
public class UserTour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "users_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Users user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tours_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Tour tour;

    @Column(nullable = false)
    String reservation_date;

    @Column()
    private int rating;

    @Column()
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public String getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(String reservation_date) {
        this.reservation_date = reservation_date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public  UserTour(){}
    public UserTour(Long id, Users user, Tour tour, String reservation_date, int rating, String comment) {
        this.id = id;
        this.user = user;
        this.tour = tour;
        this.reservation_date = reservation_date;
        this.rating = rating;
        this.comment = comment;
    }
}
