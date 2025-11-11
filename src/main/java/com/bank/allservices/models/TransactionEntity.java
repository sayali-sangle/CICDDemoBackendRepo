package com.bank.allservices.models;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    private String status;

    private String description;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "payment_gateway_name")
    private String paymentGatewayName;

    @Column(name = "transaction_fees", precision = 15, scale = 2)
    private Double transactionFees = 0.0;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "bank_name")
    private String bankName;
    
    
    public TransactionEntity() {}
    
	public TransactionEntity(TransactionEntity transaction) {
		 if (transaction != null) {
	            this.id = transaction.id;
	            this.account = transaction.account;  
	            this.amount = transaction.amount;
	            this.currency = transaction.currency;
	            this.transactionType = transaction.transactionType;
	            this.timestamp = transaction.timestamp;
	            this.paymentMethod = transaction.paymentMethod;
	            this.status = transaction.status;
	            this.description = transaction.description;
	            this.invoiceNumber = transaction.invoiceNumber;
	            this.userId = transaction.userId;
	            this.userEmail = transaction.userEmail;
	            this.userPhone = transaction.userPhone;
	            this.paymentGatewayName = transaction.paymentGatewayName;
	            this.transactionFees = transaction.transactionFees;
	            this.bankAccountNumber = transaction.bankAccountNumber;
	            this.bankName = transaction.bankName;
	        }
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

  

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

   

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getPaymentGatewayName() {
        return paymentGatewayName;
    }

    public void setPaymentGatewayName(String paymentGatewayName) {
        this.paymentGatewayName = paymentGatewayName;
    }

    public Double getTransactionFees() {
        return transactionFees;
    }

    public void setTransactionFees(Double transactionFees) {
        this.transactionFees = transactionFees;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    
    
	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Override
	public String toString() {
		return "TransactionEntity [id=" + id + ", account=" + account + ", amount=" + amount + ", currency=" + currency
				+ ", transactionType=" + transactionType + ", timestamp=" + timestamp + ", paymentMethod="
				+ paymentMethod + ", status=" + status + ", description=" + description + ", invoiceNumber="
				+ invoiceNumber + ", userId=" + userId + ", userEmail=" + userEmail
				+ ", userPhone=" + userPhone + ", paymentGatewayName=" + paymentGatewayName + ", transactionFees="
				+ transactionFees + ", bankAccountNumber=" + bankAccountNumber + ", bankName=" + bankName + "]";
	}
}
