package cubeledger.controller;

import cubeledger.dto.DepositRequest;
import cubeledger.dto.TransactionDTO;
import cubeledger.dto.TransferRequest;
import cubeledger.dto.WithdrawRequest;
import cubeledger.model.Transaction;
import cubeledger.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for transaction-related operations.
 */
@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transaction", description = "Transaction management API")
public class TransactionController {

    private final AccountService accountService;

    public TransactionController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Transfer funds between accounts.
     *
     * @param request the transfer request
     * @return the created transaction
     */
    @Operation(
        summary = "Transfer funds between accounts",
        description = "Transfers a specified amount from one account to another"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Transfer completed successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request (e.g., insufficient funds)",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Account not found",
            content = @Content
        )
    })
    @PostMapping("/transfer")
    public ResponseEntity<TransactionDTO> transfer(
            @Parameter(description = "Transfer request details", required = true)
            @Valid @RequestBody TransferRequest request) {
        Transaction transaction = accountService.transfer(
                request.getSourceAccountNumber(),
                request.getTargetAccountNumber(),
                request.getAmount(),
                request.getDescription()
        );
        return ResponseEntity.ok(convertToDTO(transaction));
    }

    /**
     * Deposit funds into an account.
     *
     * @param request the deposit request
     * @return the created transaction
     */
    @Operation(
        summary = "Deposit funds into an account",
        description = "Deposits a specified amount into an account"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Deposit completed successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Account not found",
            content = @Content
        )
    })
    @PostMapping("/deposit")
    public ResponseEntity<TransactionDTO> deposit(
            @Parameter(description = "Deposit request details", required = true)
            @Valid @RequestBody DepositRequest request) {
        Transaction transaction = accountService.deposit(
                request.getAccountNumber(),
                request.getAmount(),
                request.getDescription()
        );
        return ResponseEntity.ok(convertToDTO(transaction));
    }

    /**
     * Withdraw funds from an account.
     *
     * @param request the withdrawal request
     * @return the created transaction
     */
    @Operation(
        summary = "Withdraw funds from an account",
        description = "Withdraws a specified amount from an account"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Withdrawal completed successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request (e.g., insufficient funds)",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Account not found",
            content = @Content
        )
    })
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionDTO> withdraw(
            @Parameter(description = "Withdrawal request details", required = true)
            @Valid @RequestBody WithdrawRequest request) {
        Transaction transaction = accountService.withdraw(
                request.getAccountNumber(),
                request.getAmount(),
                request.getDescription()
        );
        return ResponseEntity.ok(convertToDTO(transaction));
    }

    /**
     * List all transactions for an account.
     *
     * @param accountNumber the account number
     * @return a list of transactions
     */
    @Operation(
        summary = "List all transactions for an account",
        description = "Retrieves all transactions associated with the specified account"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Transactions retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Account not found",
            content = @Content
        )
    })
    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<List<TransactionDTO>> listTransactions(
            @Parameter(description = "Account number", required = true)
            @PathVariable String accountNumber) {
        List<Transaction> transactions = accountService.listTransactions(accountNumber);
        List<TransactionDTO> transactionDTOs = transactions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transactionDTOs);
    }

    /**
     * List transactions for an account with pagination.
     *
     * @param accountNumber the account number
     * @param pageable pagination information
     * @return a page of transactions
     */
    @Operation(
        summary = "List transactions for an account with pagination",
        description = "Retrieves transactions associated with the specified account with pagination support"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Transactions retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Account not found",
            content = @Content
        )
    })
    @GetMapping("/account/{accountNumber}/paged")
    public ResponseEntity<Page<TransactionDTO>> listTransactionsPaged(
            @Parameter(description = "Account number", required = true)
            @PathVariable String accountNumber,
            @Parameter(description = "Pagination information (page, size, sort)", required = true)
            Pageable pageable) {
        Page<Transaction> transactionsPage = accountService.listTransactions(accountNumber, pageable);
        Page<TransactionDTO> transactionDTOsPage = transactionsPage.map(this::convertToDTO);
        return ResponseEntity.ok(transactionDTOsPage);
    }

    /**
     * Convert a Transaction entity to a TransactionDTO.
     *
     * @param transaction the transaction entity
     * @return the transaction DTO
     */
    private TransactionDTO convertToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getSourceAccount() != null ? transaction.getSourceAccount().getAccountNumber() : null,
                transaction.getTargetAccount() != null ? transaction.getTargetAccount().getAccountNumber() : null,
                transaction.getAmount(),
                transaction.getTimestamp(),
                transaction.getDescription(),
                transaction.getType()
        );
    }
}
