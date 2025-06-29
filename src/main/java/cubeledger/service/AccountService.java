package cubeledger.service;

import cubeledger.model.Account;
import cubeledger.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for managing accounts and transactions.
 */
public interface AccountService {

    /**
     * Get the balance of an account.
     *
     * @param accountNumber the account number
     * @return the account balance
     * @throws cubeledger.exception.AccountNotFoundException if the account is not found
     */
    BigDecimal getBalance(String accountNumber);

    /**
     * Transfer funds between accounts.
     *
     * @param sourceAccountNumber the source account number
     * @param targetAccountNumber the target account number
     * @param amount the amount to transfer
     * @param description optional description of the transfer
     * @return the created transaction
     * @throws cubeledger.exception.AccountNotFoundException if either account is not found
     * @throws cubeledger.exception.InsufficientFundsException if the source account has insufficient funds
     * @throws cubeledger.exception.InvalidTransactionException if the transaction is invalid
     */
    Transaction transfer(String sourceAccountNumber, String targetAccountNumber, BigDecimal amount, String description);

    /**
     * Deposit funds into an account.
     *
     * @param accountNumber the account number
     * @param amount the amount to deposit
     * @param description optional description of the deposit
     * @return the created transaction
     * @throws cubeledger.exception.AccountNotFoundException if the account is not found
     * @throws cubeledger.exception.InvalidTransactionException if the transaction is invalid
     */
    Transaction deposit(String accountNumber, BigDecimal amount, String description);

    /**
     * Withdraw funds from an account.
     *
     * @param accountNumber the account number
     * @param amount the amount to withdraw
     * @param description optional description of the withdrawal
     * @return the created transaction
     * @throws cubeledger.exception.AccountNotFoundException if the account is not found
     * @throws cubeledger.exception.InsufficientFundsException if the account has insufficient funds
     * @throws cubeledger.exception.InvalidTransactionException if the transaction is invalid
     */
    Transaction withdraw(String accountNumber, BigDecimal amount, String description);

    /**
     * List all transactions for an account.
     *
     * @param accountNumber the account number
     * @return a list of transactions
     * @throws cubeledger.exception.AccountNotFoundException if the account is not found
     */
    List<Transaction> listTransactions(String accountNumber);

    /**
     * List transactions for an account with pagination.
     *
     * @param accountNumber the account number
     * @param pageable pagination information
     * @return a page of transactions
     * @throws cubeledger.exception.AccountNotFoundException if the account is not found
     */
    Page<Transaction> listTransactions(String accountNumber, Pageable pageable);

    /**
     * Create a new account.
     *
     * @param accountNumber the account number
     * @return the created account
     * @throws cubeledger.exception.InvalidTransactionException if the account number is already in use
     */
    Account createAccount(String accountNumber);

    /**
     * Get an account by its account number.
     *
     * @param accountNumber the account number
     * @return the account
     * @throws cubeledger.exception.AccountNotFoundException if the account is not found
     */
    Account getAccount(String accountNumber);
}
