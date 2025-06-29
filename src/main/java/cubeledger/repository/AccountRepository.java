package cubeledger.repository;

import cubeledger.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;

/**
 * Repository interface for Account entities.
 * Provides methods for CRUD operations and custom queries.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Find an account by its account number.
     *
     * @param accountNumber the account number to search for
     * @return an Optional containing the account if found, or empty if not found
     */
    Optional<Account> findByAccountNumber(String accountNumber);

    /**
     * Find an account by its account number with a pessimistic write lock.
     * This ensures that the account is locked for the duration of the transaction.
     *
     * @param accountNumber the account number to search for
     * @return an Optional containing the account if found, or empty if not found
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Account a WHERE a.accountNumber = :accountNumber")
    Optional<Account> findByAccountNumberWithLock(@Param("accountNumber") String accountNumber);

    /**
     * Check if an account with the given account number exists.
     *
     * @param accountNumber the account number to check
     * @return true if an account with the given account number exists, false otherwise
     */
    boolean existsByAccountNumber(String accountNumber);
}
