//package com.bank.allservices.services;
//
//import java.util.ArrayList;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.bank.allservices.models.LoginEntity;
//import com.bank.allservices.models.UserEntity;
//import com.bank.allservices.userRepository.UserRepository;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        // Retrieve the user from the database by email
//        LoginEntity user = userRepository.findByEmail(email);
//        
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with email: " + email);
//        }
//
//        // Return the UserDetails object
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(), user.getPassword(), new ArrayList<>()); // Assuming no authorities for simplicity
//    }
//}
//
