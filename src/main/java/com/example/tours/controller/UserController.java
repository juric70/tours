package com.example.tours.controller;

import com.example.tours.model.Tour;
import com.example.tours.model.UserDetails;
import com.example.tours.model.Users;
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

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/home")
    public String listUsers(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<Users> listUsers = userRepository.findAll();
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("activeLink", "Korisnici");
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
        model.addAttribute("activeLink", "user");
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
        model.addAttribute("activeLink", "user");
        return "edit_profile";
    }

    @PostMapping("profile/update/{id}")
    public String   updateUser(@PathVariable("id") long id, @Valid Users users, BindingResult result, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        if(result.hasErrors()){
            System.out.println(result);
            model.addAttribute("userDetails", userDetails);
            model.addAttribute("activeLink", "Putovanja");
            model.addAttribute("users", users);
            return "edit_profile";
        }
        users.setDateOfCreation(LocalDate.now().toString());
        users.setRoleId(1);
        users.setDeleted(false);
        userRepository.save(users);
        return "redirect:/home";
    }


}
