package cubeledger.actuator;

import cubeledger.repository.AccountRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Custom health indicator that checks if the database is accessible
 * by counting the number of accounts.
 */
@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    private final AccountRepository accountRepository;

    public DatabaseHealthIndicator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Health health() {
        try {
            long accountCount = accountRepository.count();
            return Health.up()
                    .withDetail("accountCount", accountCount)
                    .withDetail("status", "Database is accessible")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .withDetail("status", "Database is not accessible")
                    .build();
        }
    }
}
