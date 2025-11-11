package com.bank.allservices.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bank.allservices.models.LoginEntity;
import com.bank.allservices.models.UserEntity;
import com.bank.allservices.models.UserPrincipal;
import com.bank.allservices.userRepository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("Searching for user: " + username);
        UserEntity user = userRepo.findByEmail(username);

        if(user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User Not Found");
        }

        System.out.println("User Found");
        return new UserPrincipal(user);
    }
}
