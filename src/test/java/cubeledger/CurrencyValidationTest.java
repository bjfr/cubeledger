package cubeledger;

import cubeledger.exception.InvalidCurrencyException;
import cubeledger.model.Currency;
import cubeledger.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class CurrencyValidationTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void testCreateAccountWithSEKCurrency() {
        // SEK should be accepted
        assertDoesNotThrow(() -> {
            accountService.createAccount("TEST-SEK-ACCOUNT", Currency.SEK);
        });
    }

    @Test
    public void testCreateAccountWithNonSEKCurrency() {
        // USD should be rejected
        assertThrows(InvalidCurrencyException.class, () -> {
            accountService.createAccount("TEST-USD-ACCOUNT", Currency.USD);
        });

        // EUR should be rejected
        assertThrows(InvalidCurrencyException.class, () -> {
            accountService.createAccount("TEST-EUR-ACCOUNT", Currency.EUR);
        });
    }

    @Test
    public void testDepositWithSEKCurrency() {
        // Create account with SEK currency
        assertDoesNotThrow(() -> {
            accountService.createAccount("TEST-DEPOSIT-SEK", Currency.SEK);
        });

        // SEK deposit should be accepted
        assertDoesNotThrow(() -> {
            accountService.deposit("TEST-DEPOSIT-SEK", new BigDecimal("100.00"), Currency.SEK, "Test deposit");
        });
    }

    @Test
    public void testDepositWithNonSEKCurrency() {
        // Create account with SEK currency
        assertDoesNotThrow(() -> {
            accountService.createAccount("TEST-DEPOSIT-NON-SEK", Currency.SEK);
        });

        // USD deposit should be rejected
        assertThrows(InvalidCurrencyException.class, () -> {
            accountService.deposit("TEST-DEPOSIT-NON-SEK", new BigDecimal("100.00"), Currency.USD, "Test deposit");
        });
    }

    @Test
    public void testWithdrawWithSEKCurrency() {
        // Create account with SEK currency and deposit funds
        assertDoesNotThrow(() -> {
            accountService.createAccount("TEST-WITHDRAW-SEK", Currency.SEK);
            accountService.deposit("TEST-WITHDRAW-SEK", new BigDecimal("200.00"), Currency.SEK, "Initial deposit");
        });

        // SEK withdrawal should be accepted
        assertDoesNotThrow(() -> {
            accountService.withdraw("TEST-WITHDRAW-SEK", new BigDecimal("50.00"), Currency.SEK, "Test withdrawal");
        });
    }

    @Test
    public void testWithdrawWithNonSEKCurrency() {
        // Create account with SEK currency and deposit funds
        assertDoesNotThrow(() -> {
            accountService.createAccount("TEST-WITHDRAW-NON-SEK", Currency.SEK);
            accountService.deposit("TEST-WITHDRAW-NON-SEK", new BigDecimal("200.00"), Currency.SEK, "Initial deposit");
        });

        // USD withdrawal should be rejected
        assertThrows(InvalidCurrencyException.class, () -> {
            accountService.withdraw("TEST-WITHDRAW-NON-SEK", new BigDecimal("50.00"), Currency.USD, "Test withdrawal");
        });
    }

    @Test
    public void testTransferWithSEKCurrency() {
        // Create source and target accounts with SEK currency
        assertDoesNotThrow(() -> {
            accountService.createAccount("TEST-TRANSFER-SOURCE-SEK", Currency.SEK);
            accountService.createAccount("TEST-TRANSFER-TARGET-SEK", Currency.SEK);
            accountService.deposit("TEST-TRANSFER-SOURCE-SEK", new BigDecimal("300.00"), Currency.SEK, "Initial deposit");
        });

        // SEK transfer should be accepted
        assertDoesNotThrow(() -> {
            accountService.transfer("TEST-TRANSFER-SOURCE-SEK", "TEST-TRANSFER-TARGET-SEK",
                    new BigDecimal("100.00"), Currency.SEK, "Test transfer");
        });
    }

    @Test
    public void testTransferWithNonSEKCurrency() {
        // Create source and target accounts with SEK currency
        assertDoesNotThrow(() -> {
            accountService.createAccount("TEST-TRANSFER-SOURCE-NON-SEK", Currency.SEK);
            accountService.createAccount("TEST-TRANSFER-TARGET-NON-SEK", Currency.SEK);
            accountService.deposit("TEST-TRANSFER-SOURCE-NON-SEK", new BigDecimal("300.00"), Currency.SEK, "Initial deposit");
        });

        // USD transfer should be rejected
        assertThrows(InvalidCurrencyException.class, () -> {
            accountService.transfer("TEST-TRANSFER-SOURCE-NON-SEK", "TEST-TRANSFER-TARGET-NON-SEK",
                    new BigDecimal("100.00"), Currency.USD, "Test transfer");
        });
    }
}
