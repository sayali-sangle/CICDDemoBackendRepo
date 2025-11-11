package com.bank.allservices.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bank.allservices.models.AccountEntity;
import com.bank.allservices.models.TransactionEntity;
import com.bank.allservices.userRepository.AccountRepository;
import com.bank.allservices.userRepository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountRepository accountRepository;

	// Create a new transaction
	/*public ResponseEntity<?> createTransaction(TransactionEntity transaction) {
		BigDecimal balance = null;
		 TransactionEntity receivertransaction = new TransactionEntity();
		TransactionEntity sendertransaction = new TransactionEntity();
		//TransactionEntity createdsenderTransaction = new TransactionEntity();
		
		System.out.println("transaction ******** "+transaction);
		
		System.out.println("transaction.getAccount().getId() sayali"+transaction.getAccount().getId());

		AccountEntity senderaccountOpt = accountRepository.findAccountById(transaction.getAccount().getId());
		System.out.println("senderaccountOpt account number" + senderaccountOpt.getAccountNumber());

		if (senderaccountOpt != null) {

			if (senderaccountOpt.getBalance().compareTo(transaction.getAmount()) > 0) {
                
				System.out.println("transaction.getBankAccountNumber() sayali"+transaction.getBankAccountNumber());
				
				AccountEntity receiveraccountOpt = accountRepository
						.findByAccountNumber(transaction.getBankAccountNumber());
				
				System.out.println("receiveraccountOpt " +receiveraccountOpt);

				
				if (receiveraccountOpt != null) {
					receivertransaction = addReceiverTransaction(transaction, receiveraccountOpt);
				} else {
					return new ResponseEntity<>("Receiver Account Not Found", HttpStatus.NOT_FOUND);
				}

				if (senderaccountOpt != null) {
					sendertransaction = addSenderTransaction(transaction,senderaccountOpt);
				}
				
//				HashMap<String,TransactionEntity> map = new HashMap<>();
//				System.out.println("sendertransaction " +sendertransaction);
//				System.out.println("receivertransaction " +receivertransaction);
//				map.put("sendertransaction", sendertransaction);
//				map.put("receivertransaction", receivertransaction);
//				System.out.println("map ********"+map);
//				
//				for (String key : map.keySet()) {
//				   /* TransactionEntity maptransaction = map.get(key);
//				    transactionRepository.save(maptransaction);
//				}

			} else {
				return new ResponseEntity<>("Insuffiecient Balance", HttpStatus.CREATED);
			}
		} else {
			return new ResponseEntity<>("Sender Account Not Found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(sendertransaction, HttpStatus.CREATED);
	}*/

	public ResponseEntity<TransactionEntity> addReceiverTransaction(TransactionEntity receivertransaction) {
		AccountEntity receiveraccountOpt = accountRepository.findByAccountNumber(receivertransaction.getBankAccountNumber());
		System.out.println("receiveraccountOpt" + receiveraccountOpt);
		receiveraccountOpt.setBalance(0.0);//(receiveraccountOpt.getBalance().add(receivertransaction.getAmount()));
		accountRepository.save(receiveraccountOpt);
		receivertransaction.setUserEmail(receiveraccountOpt.getEmail());
		receivertransaction.setAccount(receiveraccountOpt);
		receivertransaction.setTransactionType("credit");
		receivertransaction.setUserPhone(receiveraccountOpt.getPhoneNumber());
		receivertransaction.setBankAccountNumber(receiveraccountOpt.getAccountNumber());
		System.out.println("receivertransaction" + receivertransaction);
		transactionRepository.save(receivertransaction);
		return new ResponseEntity<>(receivertransaction, HttpStatus.CREATED);
	}
	
	public ResponseEntity<TransactionEntity> addSenderTransaction(TransactionEntity senderTransaction) {
		AccountEntity senderaccountOpt = accountRepository.findAccountById(senderTransaction.getAccount().getId());
		senderaccountOpt.setBalance(0.0);//(senderaccountOpt.getBalance().subtract(senderTransaction.getAmount()));
		accountRepository.save(senderaccountOpt);
		senderTransaction.setUserEmail(senderaccountOpt.getEmail());
		senderTransaction.setAccount(senderaccountOpt);
		senderTransaction.setTransactionType("debit");
		senderTransaction.setUserPhone(senderaccountOpt.getPhoneNumber());
		senderTransaction.setBankAccountNumber(senderaccountOpt.getAccountNumber());
		System.out.println("sendertransaction" + senderTransaction);
		TransactionEntity createdsenderTransaction = transactionRepository.save(senderTransaction);
		return new ResponseEntity<>(senderTransaction, HttpStatus.CREATED);
	}

	// Get all transactions
	public List<TransactionEntity> getAllTransactions() {
		return transactionRepository.findAll();
	}

	// Get transaction by ID
	public List<TransactionEntity> getTransactionByAccountId(Long accountid) {
		return transactionRepository.findByAccountId(accountid);
	}

	// Delete a transaction by ID
	public void deleteTransaction(Long id) {
		transactionRepository.deleteById(id);
	}
}
