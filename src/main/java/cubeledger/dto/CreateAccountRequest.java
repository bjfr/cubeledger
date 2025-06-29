package cubeledger.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Request DTO for creating a new account.
 */
public class CreateAccountRequest {

    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "^[a-zA-Z0-9-]{3,50}$", message = "Account number must be 3-50 alphanumeric characters or hyphens")
    private String accountNumber;

    // Default constructor
    public CreateAccountRequest() {
    }

    // Constructor with all fields
    public CreateAccountRequest(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    // Getters and setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
