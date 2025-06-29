package cubeledger.exception;

import cubeledger.model.Currency;

/**
 * Exception thrown when an invalid currency is used.
 * Currently, only SEK is supported.
 */
public class InvalidCurrencyException extends RuntimeException {

    private final Currency requestedCurrency;

    /**
     * Constructs a new InvalidCurrencyException with the specified requested currency.
     *
     * @param requestedCurrency the currency that was requested but is not supported
     */
    public InvalidCurrencyException(Currency requestedCurrency) {
        super("Currency " + requestedCurrency + " is not supported. Only SEK is currently supported.");
        this.requestedCurrency = requestedCurrency;
    }

    /**
     * Get the requested currency that caused this exception.
     *
     * @return the requested currency
     */
    public Currency getRequestedCurrency() {
        return requestedCurrency;
    }
}
