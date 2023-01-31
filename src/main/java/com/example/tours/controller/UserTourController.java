package com.example.tours.controller;

import com.example.tours.model.Tour;
import com.example.tours.model.UserDetails;
import com.example.tours.model.UserTour;
import com.example.tours.model.Users;
import com.example.tours.repositories.TourRepository;
import com.example.tours.repositories.UserTourRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class UserTourController {
    @Autowired
    private UserTourRepository userTourRepository;
    @Autowired
    private TourRepository tourRepository;

    @GetMapping("/reservations")
    public String myReservations(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<UserTour> listUserTour = userTourRepository.findReservationsById(userDetails.getId());
        model.addAttribute("listUserTour", listUserTour);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("activeLink", "Moje rezervacije");
        return "reservations";
    }

    @GetMapping("/allreservations")
    public String reservations(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<UserTour> listUserTour = userTourRepository.findAll();
        model.addAttribute("listUserTour", listUserTour);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("activeLink", "Sve rezervacije");
        return "all_reservations";
    }
    @GetMapping("/reservation/add/{tour_id}")
    public String addReservation(@PathVariable("tour_id") long tour_id,  Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
        UserTour userTour = new UserTour();
        Tour tour = tourRepository.findById(tour_id) .orElseThrow(() -> new IllegalArgumentException("Id putovanja ne postoji"));
        userTour.setReservation_date(LocalDate.now().toString());
        Users user = new Users();
        user = userDetails.getUsers();
        userTour.setUser(user);
        userTour.setTour(tour);
        userTourRepository.save(userTour);
        return "redirect:/reservations";
    }
    @GetMapping("/reservation/delete/{id}")
    public String deleteTour(@PathVariable("id") long id, Model model){
        UserTour userTour = userTourRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rezervacija ne postoji"));
        userTourRepository.delete(userTour);
        return "redirect:/reservations";

    }

    @GetMapping("reservation/edit/{id}")
    public String editReservationForm(@PathVariable("id") long id, Model model){
        UserTour userTour = userTourRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rezervacija ne postoji"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("userTour",userTour );
        model.addAttribute("activeLink", "Moje rezervacije");
        return "edit_reservation";
    }

    @PostMapping("reservation/update/{id}")
    public String editReservation(@PathVariable("id") long id, UserTour userTour, BindingResult result, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();

        Optional<UserTour> optionalReservations = userTourRepository.findById(id);

        if (optionalReservations.isPresent()) {
            UserTour editReservation = optionalReservations.get();
            editReservation.setRating(userTour.getRating());
            userTourRepository.save(editReservation);
            return "redirect:/reservations";
        } else {
            model.addAttribute("userDetails", userDetails);
            model.addAttribute("activeLink","Moje rezervacije");
            model.addAttribute("userTour",userTour);
            return "edit_reservation";
        }
    }


}
