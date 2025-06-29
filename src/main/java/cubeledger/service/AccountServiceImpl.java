package cubeledger.service;

import cubeledger.actuator.TransactionMetrics;
import cubeledger.exception.AccountNotFoundException;
import cubeledger.exception.InsufficientFundsException;
import cubeledger.exception.InvalidCurrencyException;
import cubeledger.exception.InvalidTransactionException;
import cubeledger.model.Account;
import cubeledger.model.Currency;
import cubeledger.model.Transaction;
import cubeledger.model.TransactionType;
import cubeledger.repository.AccountRepository;
import cubeledger.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementation of the AccountService interface.
 * This class provides thread-safe operations for account and transaction management.
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMetrics transactionMetrics;

    public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository, TransactionMetrics transactionMetrics) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionMetrics = transactionMetrics;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getBalance(String accountNumber) {
        Account account = findAccountByNumber(accountNumber);
        return account.getBalance();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Transaction transfer(String sourceAccountNumber, String targetAccountNumber, BigDecimal amount, Currency currency, String description) {
        validateAmount(amount);
        validateCurrency(currency);

        if (sourceAccountNumber.equals(targetAccountNumber)) {
            throw new InvalidTransactionException("Source and target accounts cannot be the same");
        }

        // Use pessimistic locking to prevent concurrent modifications
        Account sourceAccount = accountRepository.findByAccountNumberWithLock(sourceAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException(sourceAccountNumber));

        Account targetAccount = accountRepository.findByAccountNumberWithLock(targetAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException(targetAccountNumber));

        // Check if source account has sufficient funds
        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(sourceAccountNumber, sourceAccount.getBalance(), amount);
        }

        // Update account balances
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        targetAccount.setBalance(targetAccount.getBalance().add(amount));

        // Save updated accounts
        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        // Create and save transaction record
        Transaction transaction = new Transaction(sourceAccount, targetAccount, amount, currency, TransactionType.TRANSFER, description);
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Increment transfer counter
        transactionMetrics.incrementTransferCounter();

        return savedTransaction;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Transaction transfer(String sourceAccountNumber, String targetAccountNumber, BigDecimal amount, String description) {
        // Use SEK as the default currency
        return transfer(sourceAccountNumber, targetAccountNumber, amount, Currency.SEK, description);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Transaction deposit(String accountNumber, BigDecimal amount, Currency currency, String description) {
        validateAmount(amount);
        validateCurrency(currency);

        // Use pessimistic locking to prevent concurrent modifications
        Account account = accountRepository.findByAccountNumberWithLock(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));

        // Update account balance
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        // Create and save transaction record
        Transaction transaction = new Transaction(null, account, amount, currency, TransactionType.DEPOSIT, description);
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Increment deposit counter
        transactionMetrics.incrementDepositCounter();

        return savedTransaction;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Transaction deposit(String accountNumber, BigDecimal amount, String description) {
        // Use SEK as the default currency
        return deposit(accountNumber, amount, Currency.SEK, description);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Transaction withdraw(String accountNumber, BigDecimal amount, Currency currency, String description) {
        validateAmount(amount);
        validateCurrency(currency);

        // Use pessimistic locking to prevent concurrent modifications
        Account account = accountRepository.findByAccountNumberWithLock(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));

        // Check if account has sufficient funds
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(accountNumber, account.getBalance(), amount);
        }

        // Update account balance
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        // Create and save transaction record
        Transaction transaction = new Transaction(account, null, amount, currency, TransactionType.WITHDRAWAL, description);
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Increment withdrawal counter
        transactionMetrics.incrementWithdrawalCounter();

        return savedTransaction;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Transaction withdraw(String accountNumber, BigDecimal amount, String description) {
        // Use SEK as the default currency
        return withdraw(accountNumber, amount, Currency.SEK, description);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> listTransactions(String accountNumber) {
        // Verify account exists
        if (!accountRepository.existsByAccountNumber(accountNumber)) {
            throw new AccountNotFoundException(accountNumber);
        }

        return transactionRepository.findByAccountNumber(accountNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Transaction> listTransactions(String accountNumber, Pageable pageable) {
        // Verify account exists
        if (!accountRepository.existsByAccountNumber(accountNumber)) {
            throw new AccountNotFoundException(accountNumber);
        }

        return transactionRepository.findByAccountNumber(accountNumber, pageable);
    }

    @Override
    @Transactional
    public Account createAccount(String accountNumber, Currency currency) {
        validateCurrency(currency);

        // Check if account already exists
        if (accountRepository.existsByAccountNumber(accountNumber)) {
            throw new InvalidTransactionException("Account with number " + accountNumber + " already exists");
        }

        Account account = new Account(accountNumber, currency);
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account createAccount(String accountNumber) {
        // Use SEK as the default currency
        return createAccount(accountNumber, Currency.SEK);
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccount(String accountNumber) {
        return findAccountByNumber(accountNumber);
    }

    /**
     * Helper method to find an account by its account number.
     *
     * @param accountNumber the account number
     * @return the account
     * @throws AccountNotFoundException if the account is not found
     */
    private Account findAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    /**
     * Validate that an amount is positive.
     *
     * @param amount the amount to validate
     * @throws InvalidTransactionException if the amount is not positive
     */
    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException("Transaction amount must be positive");
        }
    }

    /**
     * Validate that the currency is supported.
     * Currently, only SEK is supported.
     *
     * @param currency the currency to validate
     * @throws InvalidCurrencyException if the currency is not supported
     */
    private void validateCurrency(Currency currency) {
        if (currency != Currency.SEK) {
            throw new InvalidCurrencyException(currency);
        }
    }
}
