package com.example.tours.controller;

import com.example.tours.model.Tour;
import com.example.tours.model.Users;
import com.example.tours.repositories.TourRepository;
import com.example.tours.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;




    @GetMapping("/")
    public String home(Model model){


        model.addAttribute("activeLink", "Početna stranica");
        return "home";
    }
    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("users", new Users());
        return "register_form";
    }
    @PostMapping("/register_user")
    public String registerUser(@Valid Users users, BindingResult result, Model model){
        boolean errors = result.hasErrors();
        if(errors){
            return "register_form";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(users.getPassword());
        users.setPasswordRepeat(encodedPassword);
        users.setPassword(encodedPassword);
        users.setDateOfCreation(LocalDate.now().toString());
        users.setRoleId(1);
        users.setDeleted(false);
        userRepository.save(users);
        return "login_form";
    }


    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("user", new Users());
        return "login_form";
    }


}
