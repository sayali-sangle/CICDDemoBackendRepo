package com.bank.allservices.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.allservices.models.JwtResponse;
import com.bank.allservices.models.LoginEntity;
import com.bank.allservices.models.User;
import com.bank.allservices.models.UserEntity;
import com.bank.allservices.userRepository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userrepo;

	@Autowired
	private JWTService jwtService;

	@Autowired
	AuthenticationManager authenticationManager;

//	private List<User> store = new ArrayList<>();
//
//	public UserService() {
//		store.add(new User(UUID.randomUUID().toString(), "Prathiksha Kini", "gpkini2002@gmail.com"));
//		store.add(new User(UUID.randomUUID().toString(), "Padmini Kini", "kinipadmini@gmail.com"));
//		store.add(new User(UUID.randomUUID().toString(), "Mahalasa Kini", "kinimahalasa@gmail.com"));
//		store.add(new User(UUID.randomUUID().toString(), "Gurudath Kini", "gurukini@gmail.com"));
//	}
//
//	public List<User> getUsers() {
//		return this.store;
//	}

	// Method to save a user
//	    public UserEntity saveUser(UserEntity user) {
//	        return userrepo.save(user);
//	    }

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	public UserEntity saveUser(UserEntity user) {

		user.setPassword(encoder.encode(user.getPassword()));
		return userrepo.save(user);

	}

	public Optional<UserEntity> getUserDetailsById(long id) {
		return userrepo.findById(id);
	}
	
	public UserEntity getUserDetailsByEmail(String email) {
		return userrepo.findByEmail(email);
	}

	public JwtResponse verifyUser(LoginEntity user) {
		JwtResponse jsp = new JwtResponse();
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		UserEntity userforId = userrepo.findByEmail(user.getUsername());

		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(user.getUsername(), userforId.getUserId());
		} else
			return jsp;
	}
}
