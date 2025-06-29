package cubeledger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

/**
 * Exception thrown when an account has insufficient funds for a transaction.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String accountNumber, BigDecimal currentBalance, BigDecimal requiredAmount) {
        super(String.format("Insufficient funds in account %s. Current balance: %s, Required amount: %s",
                accountNumber, currentBalance, requiredAmount));
    }
}
