package com.example.tours.controller;

import com.example.tours.model.Tour;
import com.example.tours.model.UserDetails;
import com.example.tours.model.Users;
import com.example.tours.repositories.TourRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TourController {
    @Autowired
    TourRepository tourRepository;

    @GetMapping("tours")
    public String listTours(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        List<Tour> listTours = tourRepository.findAll();
        model.addAttribute("listTours", listTours);
        model.addAttribute("activeLink", "Putovanja");
        return "tours";
    }

    @GetMapping("tour/{id}")
    public String getTour(@PathVariable("id") long id, Model model ){
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id putovanja ne postoji"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        List<Tour> listTours = tourRepository.findAll();
        model.addAttribute("listTours", listTours);
        model.addAttribute("activeLink", "Putovanja");
        return "tour";
    }

    @GetMapping("tours/add")
    public String addTourForm(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("activeLink", "Putovanja");
        model.addAttribute("tour", new Tour());
        return "add_tour";
    }

    @PostMapping("tours/add")
    public String addTour(@Valid Tour tour, BindingResult result, Model model){
        boolean errors = result.hasErrors();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
        if(errors){

            model.addAttribute("userDetails", userDetails);
            model.addAttribute("activeLink", "Putovanja");
            model.addAttribute("tour", tour);
            return "add_tour";
        }
        tour.setUpload_date(LocalDate.now().toString());
        Users user = new Users();
        user = userDetails.getUsers();
        tour.setCreator(user);
        tourRepository.save(tour);
        return "redirect:/tours";
    }

    @GetMapping("tours/edit/{id}")
    public String updateForm(@PathVariable("id") long id, Model model){
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id putovanja ne postoji"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("activeLink", "Putovanja");
        model.addAttribute("tour", tour);
        return "edit_tour";
    }

    @PostMapping("tours/update/{id}")
    public String updateTour(@PathVariable("id") long id, @Valid Tour tour, BindingResult result, Model model){
        boolean errors = result.hasErrors();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        if(errors){
            tour.setId(id);
            model.addAttribute("activeLink", "Putovanja");
            model.addAttribute("tour", tour);
            return "edit_tour";

        }
        tour.setUpload_date(LocalDate.now().toString());
        Users user = new Users();
        user = userDetails.getUsers();
        tour.setCreator(user);
        tourRepository.save(tour);
        return "redirect:/tours";
    }

    @GetMapping("/tours/delete/{id}")
    public String deleteTour(@PathVariable("id") long id, Model model){
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id putovanja ne postoji"));
        tourRepository.delete(tour);
        return "redirect:/tours";

    }

}
