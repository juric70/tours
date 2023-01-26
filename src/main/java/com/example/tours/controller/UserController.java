package com.example.tours.controller;

import com.example.tours.model.Tour;
import com.example.tours.model.UserDetails;
import com.example.tours.model.Users;
import com.example.tours.repositories.TourRepository;
import com.example.tours.repositories.UserRepository;
import jakarta.jws.soap.SOAPBinding;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TourRepository tourRepository;
    @GetMapping("/home")
    public String homePage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<Users> listUsers = userRepository.findAll();
        List<Tour> listTours = tourRepository.findAll();
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("listTours", listTours);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("activeLink", "Kompas");
        return "home";
    }
    @GetMapping("/users")
    private String listUsers(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if(userDetails.getUsers().getRoleId() == 2){
            List<Users> listUsers = userRepository.findAll();
            model.addAttribute("listUsers", listUsers);
            model.addAttribute("userDetails", userDetails);
            model.addAttribute("activeLink", "Korisnici");
            return "users";
        }
        return "home";
    }
    @GetMapping("/profile/{id}")
    public String showProfile(@PathVariable("id")long id, Model model){
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Greška!"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("users",users );
        return "profile";
    }

    @GetMapping("profile/edit/{id}")
    public String updateUserForm(@PathVariable("id") long id, Model model){
        Users users = userRepository.
                findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Greška!"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("users",users );
        return "edit_profile";
    }

    @PostMapping("profile/update/{id}")
    public String updateUser(@PathVariable("id") long id, Users users, BindingResult result, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();

        Optional<Users> optionalUsers = userRepository.findById(id);

        if (optionalUsers.isPresent()) {
            Users editUsers = optionalUsers.get();
            editUsers.setFirstname(users.getFirstname());
            editUsers.setLastname(users.getLastname());
            editUsers.setGender(users.getGender());
            editUsers.setDateOfBirth(users.getDateOfBirth());
            editUsers.setRoleId(1);
            editUsers.setDeleted(false);
            userRepository.save(editUsers);
            return "redirect:/profile/{id}";
        } else {
            model.addAttribute("userDetails", userDetails);
            model.addAttribute("users", users);
            return "edit_profile";
        }
    }

    @GetMapping("profile/delete/{id}")
    public String deleteProfileForm(@PathVariable("id") long id, Model model){
        Users users = userRepository.
                findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Greška!"));

        userRepository.delete(users);
        return "redirect:/login";
    }

    @GetMapping("user/edit/{id}")
    public String editUserForm(@PathVariable("id") long id, Model model){
        Users users = userRepository.
                findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Greška!"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("users",users );
        model.addAttribute("activeLink", "Korisnici");
        return "edit_user";
    }

    @PostMapping("user/update/{id}")
    public String editUser(@PathVariable("id") long id, Users users, BindingResult result, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();

        Optional<Users> optionalUsers = userRepository.findById(id);

        if (optionalUsers.isPresent()) {
            Users editUsers = optionalUsers.get();
            editUsers.setRoleId(users.getRoleId());
            userRepository.save(editUsers);
            return "redirect:/users";
        } else {
            model.addAttribute("userDetails", userDetails);
            model.addAttribute("activeLink","Korisnici");
            model.addAttribute("users", users);
            return "edit_user";
        }
    }

    @GetMapping("user/delete/{id}")
    public String deleteUserForm(@PathVariable("id") long id, Model model){
        Users users = userRepository.
                findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Greška!"));

        userRepository.delete(users);
        return "redirect:/users";
    }



}
