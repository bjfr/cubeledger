package cubeledger.dto;

import cubeledger.model.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Request DTO for creating a new account.
 */
public class CreateAccountRequest {

    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "^[a-zA-Z0-9-]{3,50}$", message = "Account number must be 3-50 alphanumeric characters or hyphens")
    private String accountNumber;

    private Currency currency = Currency.USD; // Default to USD if not specified

    // Default constructor
    public CreateAccountRequest() {
    }

    // Constructor with all fields
    public CreateAccountRequest(String accountNumber, Currency currency) {
        this.accountNumber = accountNumber;
        this.currency = currency;
    }

    // Constructor with just account number (for backward compatibility)
    public CreateAccountRequest(String accountNumber) {
        this(accountNumber, Currency.USD);
    }

    // Getters and setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
