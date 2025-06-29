package cubeledger.model;

/**
 * Enum representing the different types of transactions in the ledger system.
 */
public enum TransactionType {
    /**
     * Represents a deposit into an account (money coming in)
     */
    DEPOSIT,

    /**
     * Represents a withdrawal from an account (money going out)
     */
    WITHDRAWAL,

    /**
     * Represents a transfer between two accounts
     */
    TRANSFER
}
