package cubeledger.controller;

import cubeledger.model.Account;
import cubeledger.model.Currency;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AccountControllerTest {

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
    public void testGetAccount() throws Exception {
        // Arrange
        String accountNumber = "TEST-ACCOUNT";
        Account mockAccount = new Account(accountNumber, Currency.SEK);
        mockAccount.setBalance(new BigDecimal("1000.00"));
        LocalDateTime now = LocalDateTime.now();
        mockAccount.setCreatedAt(now);
        mockAccount.setUpdatedAt(now);

        when(accountService.getAccount(anyString())).thenReturn(mockAccount);

        // Act & Assert
        mockMvc.perform(get("/api/accounts/{accountNumber}", accountNumber)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value(accountNumber))
                .andExpect(jsonPath("$.balance").value("1000.0"))
                .andExpect(jsonPath("$.currency").value("SEK"));
    }

    @Test
    public void testGetBalance() throws Exception {
        // Arrange
        String accountNumber = "TEST-ACCOUNT";
        BigDecimal balance = new BigDecimal("1000.00");

        when(accountService.getBalance(anyString())).thenReturn(balance);

        // Act & Assert
        mockMvc.perform(get("/api/accounts/{accountNumber}/balance", accountNumber)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1000.0));
    }
}
