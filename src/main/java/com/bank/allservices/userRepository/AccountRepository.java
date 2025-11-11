package com.bank.allservices.userRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bank.allservices.models.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

	List<AccountEntity> findAllByEmail(String username);
	Optional<AccountEntity> findByAccountTypeAndEmail(String accountType,String email);
	
	@Query("SELECT a.balance FROM AccountEntity a WHERE a.id = ?1")
	Double findBalanceById(long accid);
	
	AccountEntity findByAccountNumber(String accountNumber);
	
	
	AccountEntity findAccountById(int id);
	
}
	