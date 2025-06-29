package cubeledger.controller;

import cubeledger.dto.AccountDTO;
import cubeledger.dto.CreateAccountRequest;
import cubeledger.model.Account;
import cubeledger.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * REST controller for account-related operations.
 */
@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Account", description = "Account management API")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Get the balance of an account.
     *
     * @param accountNumber the account number
     * @return the account balance
     */
    @Operation(
        summary = "Get account balance",
        description = "Retrieves the current balance of an account by its account number"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Balance retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BigDecimal.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Account not found",
            content = @Content
        )
    })
    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<BigDecimal> getBalance(
            @Parameter(description = "Account number", required = true) @PathVariable String accountNumber) {
        BigDecimal balance = accountService.getBalance(accountNumber);
        return ResponseEntity.ok(balance);
    }

    /**
     * Get account details.
     *
     * @param accountNumber the account number
     * @return the account details
     */
    @Operation(
        summary = "Get account details",
        description = "Retrieves the details of an account by its account number"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Account details retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Account not found",
            content = @Content
        )
    })
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> getAccount(
            @Parameter(description = "Account number", required = true) @PathVariable String accountNumber) {
        Account account = accountService.getAccount(accountNumber);
        AccountDTO accountDTO = convertToDTO(account);
        return ResponseEntity.ok(accountDTO);
    }

    /**
     * Create a new account.
     *
     * @param request the account creation request
     * @return the created account
     */
    @Operation(
        summary = "Create a new account",
        description = "Creates a new account with the specified account number"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Account created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request (e.g., account number already exists)",
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(
            @Parameter(description = "Account creation request", required = true)
            @Valid @RequestBody CreateAccountRequest request) {
        Account account = accountService.createAccount(request.getAccountNumber(), request.getCurrency());
        AccountDTO accountDTO = convertToDTO(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountDTO);
    }

    /**
     * Convert an Account entity to an AccountDTO.
     *
     * @param account the account entity
     * @return the account DTO
     */
    private AccountDTO convertToDTO(Account account) {
        return new AccountDTO(
                account.getAccountNumber(),
                account.getBalance(),
                account.getCurrency(),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }
}
