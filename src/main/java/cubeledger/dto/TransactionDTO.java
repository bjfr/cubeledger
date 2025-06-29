package cubeledger.dto;

import cubeledger.model.Currency;
import cubeledger.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Transaction information.
 */
public class TransactionDTO {
    private Long id;
    private String sourceAccountNumber;
    private String targetAccountNumber;
    private BigDecimal amount;
    private Currency currency;
    private LocalDateTime timestamp;
    private String description;
    private TransactionType type;

    // Default constructor
    public TransactionDTO() {
    }

    // Constructor with all fields
    public TransactionDTO(Long id, String sourceAccountNumber, String targetAccountNumber,
                         BigDecimal amount, Currency currency, LocalDateTime timestamp, String description,
                         TransactionType type) {
        this.id = id;
        this.sourceAccountNumber = sourceAccountNumber;
        this.targetAccountNumber = targetAccountNumber;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
        this.description = description;
        this.type = type;
    }

    // Constructor without currency (for backward compatibility)
    public TransactionDTO(Long id, String sourceAccountNumber, String targetAccountNumber,
                         BigDecimal amount, LocalDateTime timestamp, String description,
                         TransactionType type) {
        this(id, sourceAccountNumber, targetAccountNumber, amount, Currency.SEK, timestamp, description, type);
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public void setSourceAccountNumber(String sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }

    public void setTargetAccountNumber(String targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
