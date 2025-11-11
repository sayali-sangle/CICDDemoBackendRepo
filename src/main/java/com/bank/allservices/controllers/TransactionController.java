package com.bank.allservices.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.allservices.models.TransactionEntity;

import com.bank.allservices.services.JWTService;
import com.bank.allservices.services.TransactionService;

@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	JWTService jwtservice;

	@PostMapping("/createTransaction")
	public ResponseEntity<?> createTransaction(@RequestBody TransactionEntity transaction,
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
			transaction.setTimestamp(LocalDateTime.now());
			TransactionEntity transactionForSender = new TransactionEntity(transaction);
			TransactionEntity transactionForReceiver = new TransactionEntity(transaction);
			
			if(transactionService.addReceiverTransaction(transactionForReceiver) != null) {
				System.out.println("transaction "+transaction);
				return transactionService.addSenderTransaction(transactionForSender);
			}
			
			return null;
			//return transactionService.createTransaction(transaction);
			// return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/getAllTransactions")
	public ResponseEntity<List<TransactionEntity>> getAllTransactions(
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
			List<TransactionEntity> transactions = transactionService.getAllTransactions();
			return new ResponseEntity<>(transactions, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// Get transaction by ID
	@GetMapping("/getTransactionHistory/{accountid}")
	public ResponseEntity<List<TransactionEntity>> getTransactionByAccountId(@PathVariable Long accountid,@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

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
		    List<TransactionEntity> transactions = transactionService.getTransactionByAccountId(accountid);
			return new ResponseEntity<>(transactions, HttpStatus.OK);
		} catch (Exception e) {  
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 

	// Delete a transaction by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
		transactionService.deleteTransaction(id);
		return ResponseEntity.noContent().build();
	}
}
