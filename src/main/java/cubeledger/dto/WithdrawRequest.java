package cubeledger.dto;

import cubeledger.model.Currency;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Request DTO for withdrawing funds from an account.
 */
public class WithdrawRequest {

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    private Currency currency = Currency.USD; // Default to USD if not specified

    private String description;

    // Default constructor
    public WithdrawRequest() {
    }

    // Constructor with all fields
    public WithdrawRequest(String accountNumber, BigDecimal amount, Currency currency, String description) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
    }

    // Constructor without currency (for backward compatibility)
    public WithdrawRequest(String accountNumber, BigDecimal amount, String description) {
        this(accountNumber, amount, Currency.USD, description);
    }

    // Getters and setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
