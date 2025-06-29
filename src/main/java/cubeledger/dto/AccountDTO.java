package cubeledger.dto;

import cubeledger.model.Currency;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Account information.
 */
public class AccountDTO {
    private String accountNumber;
    private BigDecimal balance;
    private Currency currency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default constructor
    public AccountDTO() {
    }

    // Constructor with all fields
    public AccountDTO(String accountNumber, BigDecimal balance, Currency currency, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Constructor without currency (for backward compatibility)
    public AccountDTO(String accountNumber, BigDecimal balance, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(accountNumber, balance, Currency.USD, createdAt, updatedAt);
    }

    // Getters and setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
