package com.bank.allservices.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.allservices.models.AccountEntity;
import com.bank.allservices.services.AccountService;
import com.bank.allservices.services.JWTService;

import io.jsonwebtoken.Claims;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {

	@Autowired
	AccountService accountservice;
	
	@Autowired
	JWTService jwtservice;
	

	@GetMapping("/accounttest")
	public String getUserDetails() {
		return "account details";
	}

	@PostMapping("/accountwithoutauth")
	public ResponseEntity<?> createAccount(@RequestBody AccountEntity account) {
		try {
			System.out.println("Account"+account);
			account.setCreatedAt(LocalDateTime.now());
			ResponseEntity<?> resp = accountservice.createAccount(account);
			return resp;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
	

	@PostMapping("/account")
	public ResponseEntity<?> createAccount(@RequestBody AccountEntity account, 
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
	        Claims claims = jwtservice.decodeToken(token);
	        
	        Object userIdObj = claims.get("user_id");
	        if (userIdObj instanceof Integer) {
	            Integer userId = (Integer) userIdObj;
	            account.setUserId(userId);
	        }
	        String randomAccountNumber = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
	        account.setAccountNumber(randomAccountNumber);
	        account.setCreatedAt(LocalDateTime.now());
	        ResponseEntity<?> resp = accountservice.createAccount(account);
	        return resp;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return ResponseEntity.internalServerError().build();
	    }
	}
	
	
	@GetMapping("/accountlist")
	public ResponseEntity<List<AccountEntity>> getAllAccountsByUsername(@RequestParam String username, 
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
	        System.out.println("username" + username);
	        List<AccountEntity> accountlist = accountservice.getAllAccountsByUsername(username);
	        return ResponseEntity.ok(accountlist);
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return ResponseEntity.internalServerError().build();
	    }
	}
	
	
	@GetMapping("/accountDetails")
	public ResponseEntity<Optional<AccountEntity>> getAccountsByAccountId(@RequestParam int accId, 
	                                                   @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
		System.out.println("account "+accId);
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
	        System.out.println("accId" + accId);
	        Optional<AccountEntity> entity = accountservice.getAccountsByAccountId(accId);
	        return ResponseEntity.ok(entity);
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return ResponseEntity.internalServerError().build();
	    }
	}
	
	   @PutMapping("/updateAccount/{id}")
	    public ResponseEntity<AccountEntity> updateAccount(@PathVariable int id, @RequestBody AccountEntity updatedAccount) {
		   AccountEntity account = accountservice.updateAccount(id, updatedAccount);
	        
	        if (account != null) {
	            return new ResponseEntity<>(account, HttpStatus.OK);  // Account updated successfully
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Account not found
	        }
	    }

}
