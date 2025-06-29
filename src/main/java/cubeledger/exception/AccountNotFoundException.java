package cubeledger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an account is not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String accountNumber) {
        super("Account not found with account number: " + accountNumber);
    }

    public AccountNotFoundException(Long id) {
        super("Account not found with id: " + id);
    }
}
