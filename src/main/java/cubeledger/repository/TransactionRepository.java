package cubeledger.repository;

import cubeledger.model.Account;
import cubeledger.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Transaction entities.
 * Provides methods for CRUD operations and custom queries.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Find all transactions where the given account is either the source or target.
     *
     * @param account the account to search for
     * @return a list of transactions involving the account
     */
    @Query("SELECT t FROM Transaction t WHERE t.sourceAccount = :account OR t.targetAccount = :account ORDER BY t.timestamp DESC")
    List<Transaction> findByAccount(@Param("account") Account account);

    /**
     * Find all transactions where the given account is either the source or target, with pagination.
     *
     * @param account the account to search for
     * @param pageable pagination information
     * @return a page of transactions involving the account
     */
    @Query("SELECT t FROM Transaction t WHERE t.sourceAccount = :account OR t.targetAccount = :account ORDER BY t.timestamp DESC")
    Page<Transaction> findByAccount(@Param("account") Account account, Pageable pageable);

    /**
     * Find all transactions where the account with the given account number is either the source or target.
     *
     * @param accountNumber the account number to search for
     * @return a list of transactions involving the account
     */
    @Query("SELECT t FROM Transaction t " +
           "WHERE t.sourceAccount.accountNumber = :accountNumber OR t.targetAccount.accountNumber = :accountNumber " +
           "ORDER BY t.timestamp DESC")
    List<Transaction> findByAccountNumber(@Param("accountNumber") String accountNumber);

    /**
     * Find all transactions where the account with the given account number is either the source or target, with pagination.
     *
     * @param accountNumber the account number to search for
     * @param pageable pagination information
     * @return a page of transactions involving the account
     */
    @Query("SELECT t FROM Transaction t " +
           "WHERE t.sourceAccount.accountNumber = :accountNumber OR t.targetAccount.accountNumber = :accountNumber " +
           "ORDER BY t.timestamp DESC")
    Page<Transaction> findByAccountNumber(@Param("accountNumber") String accountNumber, Pageable pageable);
}
