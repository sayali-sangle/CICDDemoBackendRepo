package com.bank.allservices.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bank.allservices.models.AccountEntity;
import com.bank.allservices.userRepository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	AccountRepository accrepo;

	// Method to save a user 	
	public ResponseEntity<?> createAccount(AccountEntity acc) {
		System.out.println("acc" + acc.getAccountType());
		Optional<AccountEntity> account = accrepo.findByAccountTypeAndEmail(acc.getAccountType(), acc.getEmail());
        System.out.println("hello"+account);
		if (account.isEmpty()) {
			AccountEntity newAccount = accrepo.save(acc);
			return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Account with this type and email already exists.");
		}
	}

	public List<AccountEntity> getAllAccountsByUsername(String username) {
		return accrepo.findAllByEmail(username);
	}

	public Optional<AccountEntity> getAccountsByAccountId(int accId) {
		return accrepo.findById(accId);
	}

	public AccountEntity updateAccount(int id, AccountEntity updatedAccount) {
		Optional<AccountEntity> accountOptional = accrepo.findById(id);

		if (accountOptional.isPresent()) {
			AccountEntity existingAccount = accountOptional.get();
			existingAccount.setAddress(updatedAccount.getAddress());
			existingAccount.setPhoneNumber(updatedAccount.getPhoneNumber());
			existingAccount.setStatus(updatedAccount.getStatus());
			existingAccount.setUpdatedAt(LocalDateTime.now());
			return accrepo.save(existingAccount);
		}
		return null; // Account not found
	}
}