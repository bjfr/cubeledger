package cubeledger.controller;

import cubeledger.model.Currency;
import cubeledger.model.Transaction;
import cubeledger.model.TransactionType;
import cubeledger.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class TransactionControllerTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public AccountService accountService() {
            return Mockito.mock(AccountService.class);
        }
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountService accountService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testListTransactions() throws Exception {
        // Arrange
        String accountNumber = "TEST-ACCOUNT";
        List<Transaction> mockTransactions = new ArrayList<>();

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.setCurrency(Currency.SEK);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDescription("Test transaction");
        transaction.setType(TransactionType.DEPOSIT);

        mockTransactions.add(transaction);

        when(accountService.listTransactions(anyString())).thenReturn(mockTransactions);

        // Act & Assert
        mockMvc.perform(get("/api/transactions/account/{accountNumber}", accountNumber)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].amount").value(100.0))
                .andExpect(jsonPath("$[0].currency").value("SEK"))
                .andExpect(jsonPath("$[0].type").value("DEPOSIT"));
    }
}
