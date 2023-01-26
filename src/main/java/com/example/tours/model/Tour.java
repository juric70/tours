package com.example.tours.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "tours")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    @NotBlank(message = "Molimo Vas da unesete naslov putovanja!")
    String title;

    @Column(nullable = false)
    @NotBlank(message = "Molimo Vas da unesete odredište putovanja!")
    String location;

    @Column(nullable = false, length = 10000)
    @NotBlank(message = "Molimo Vas unesite opis putovanja!")
    String description;

    @Column(nullable = false)
    String upload_date;

    @Column(nullable = false)
    @NotBlank(message = "Molimo Vas unesite datum početka putovanja!")
    String start_date;

    @Column(nullable = false)
    @NotBlank(message = "Molimo Vas unesite datum završetka putovanja!")
    String end_date;

    @Column(nullable = false)
    int price;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "users_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Users creator;

    @Column()
    private int rating;

    @Column()
    private int numb_of_ratings;



    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getNumb_of_ratings() {
        return numb_of_ratings;
    }

    public void setNumb_of_ratings(int numb_of_ratings) {
        this.numb_of_ratings = numb_of_ratings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(String upload_date) {
        this.upload_date = upload_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Users getCreator() {
        return creator;
    }

    public void setCreator(Users creator) {
        this.creator = creator;
    }
    public Tour (){}
    public Tour(Long id, String title, String location, String description, String upload_date, String start_date, String end_date, int price, Users creator, int rating, int numb_of_ratings) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.description = description;
        this.upload_date = upload_date;
        this.start_date = start_date;
        this.end_date = end_date;
        this.price = price;
        this.creator = creator;
        this.rating = rating;
        this.numb_of_ratings = numb_of_ratings;
    }
}
