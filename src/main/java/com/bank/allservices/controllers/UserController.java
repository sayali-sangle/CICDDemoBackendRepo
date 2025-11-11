package com.bank.allservices.controllers;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.allservices.models.JwtResponse;
import com.bank.allservices.models.LoginEntity;
import com.bank.allservices.models.TransactionEntity;
import com.bank.allservices.models.UserEntity;
import com.bank.allservices.services.AccountService;
import com.bank.allservices.services.JWTService;
import com.bank.allservices.services.OtpService;
import com.bank.allservices.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
//@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	UserService userservice;

	@Autowired
	AccountService accountservice;

	@Autowired
	JWTService jwtservice;

	@Autowired
	private OtpService otpService;

	@GetMapping("/usertest")
	public String getUserDetails() {
		return "user details";
	}

	@PostMapping("/register")
	public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user) {
		try {
			user.setCreatedAt(LocalDateTime.now());
			user.setUpdatedAt(LocalDateTime.now());
			userservice.saveUser(user);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/csrf-token")
	public CsrfToken retrieveCsrfToken(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
	}

	@PostMapping("/login")
	public JwtResponse loginUser(@RequestBody LoginEntity user) {
		System.out.println(user);
		return userservice.verifyUser(user);
	}

	
	@GetMapping("/user/{username}")
	public ResponseEntity<UserEntity> getUserDetailsById(@PathVariable String username,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
		try {
			String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
			if (token != null) {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
				if (!jwtservice.validateToken(token, userDetails)) {
					return ResponseEntity.status(403).build();
				}
			} else {
				return ResponseEntity.status(401).build();
			}

			UserEntity user = userservice.getUserDetailsByEmail(username);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

	// Endpoint to send OTP
	@PostMapping("/send-otp")
	public String sendOtp(@RequestParam String email) {
		// Generate OTP
		String otp = otpService.generateOtp();

		// Send OTP to the user's email
		otpService.sendOtpEmail(email, otp);

		return "OTP has been sent to your email.";
	}
}