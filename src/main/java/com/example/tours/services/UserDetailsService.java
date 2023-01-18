package com.example.tours.services;


import com.example.tours.model.Users;
import com.example.tours.model.UserDetails;
import com.example.tours.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByEmail(username);
        if(users == null){
            throw new UsernameNotFoundException("Korisnik nije pronađen");
        }
        return new UserDetails(users);
    }
}
