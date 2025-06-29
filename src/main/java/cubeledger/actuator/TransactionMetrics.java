package cubeledger.actuator;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

/**
 * Custom metrics for tracking transaction operations.
 */
@Component
public class TransactionMetrics {

    private final Counter depositCounter;
    private final Counter withdrawalCounter;
    private final Counter transferCounter;

    public TransactionMetrics(MeterRegistry registry) {
        this.depositCounter = Counter.builder("cubeledger.transactions.deposit")
                .description("Number of deposit transactions processed")
                .register(registry);

        this.withdrawalCounter = Counter.builder("cubeledger.transactions.withdrawal")
                .description("Number of withdrawal transactions processed")
                .register(registry);

        this.transferCounter = Counter.builder("cubeledger.transactions.transfer")
                .description("Number of transfer transactions processed")
                .register(registry);
    }

    /**
     * Increment the deposit counter.
     */
    public void incrementDepositCounter() {
        depositCounter.increment();
    }

    /**
     * Increment the withdrawal counter.
     */
    public void incrementWithdrawalCounter() {
        withdrawalCounter.increment();
    }

    /**
     * Increment the transfer counter.
     */
    public void incrementTransferCounter() {
        transferCounter.increment();
    }
}
