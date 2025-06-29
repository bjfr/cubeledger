# CubeLedger - Transaction Wallet Application [![Build and Test](https://github.com/bjfr/cubeledger/actions/workflows/build.yml/badge.svg)](https://github.com/bjfr/cubeledger/actions/workflows/build.yml)

CubeLedger is a simple bookkeeping (accounting) application that keeps track of funds, similar to a wallet in online gaming terminology. It provides a REST API for managing accounts and transactions.

## Features

- Account management (create accounts, get account details, get balance)
- Transaction management (transfer funds, deposit, withdraw)
- Transaction history (list transactions for an account)
- Thread-safe operations with proper concurrency control
- Comprehensive error handling

## Technology Stack

- Java 21
- Spring Boot 3.5.3
- Spring Data JPA
- Flyway (database migration)
- OpenAPI 3.0 (API documentation)
- H2 Database (for development)
- PostgreSQL (for production)

## API Endpoints

### Account Operations

- `GET /api/accounts/{accountNumber}` - Get account details
- `GET /api/accounts/{accountNumber}/balance` - Get account balance
- `POST /api/accounts` - Create a new account

### Transaction Operations

- `POST /api/transactions/transfer` - Transfer funds between accounts
- `POST /api/transactions/deposit` - Deposit funds into an account
- `POST /api/transactions/withdraw` - Withdraw funds from an account
- `GET /api/transactions/account/{accountNumber}` - List all transactions for an account
- `GET /api/transactions/account/{accountNumber}/paged` - List transactions with pagination

## Implementation Details

### Thread Safety and Concurrency

The application ensures thread safety and proper concurrency control through:

1. **Pessimistic Locking**: When updating account balances, the application uses pessimistic locking to prevent concurrent modifications to the same account.

2. **Transaction Isolation**: The application uses the SERIALIZABLE isolation level for financial transactions to ensure consistency.

3. **Optimistic Locking**: The Account and Transaction entities include a version field for optimistic locking, which helps detect concurrent modifications.

### Data Consistency

To ensure data consistency:

1. All financial operations are executed within a transaction boundary.
2. Account balances are updated atomically.
3. Transaction records are created for all financial operations.
4. Validation is performed to ensure that accounts have sufficient funds for withdrawals and transfers.

### Database Migration

The application uses Flyway for database schema migration and version control:

1. **Schema Versioning**: All database schema changes are versioned and tracked using Flyway migration scripts.
2. **Automated Migration**: Migrations are automatically applied when the application starts.
3. **Consistent Schema**: Ensures that the database schema is consistent across all environments.
4. **Migration Scripts**: Located in `src/main/resources/db/migration` with naming convention `V{version}__{description}.sql`.
5. **Sample Data**: Includes a migration script for sample data to facilitate testing and development.

### API Documentation

The application uses OpenAPI 3.0 (formerly known as Swagger) for API documentation:

1. **Interactive Documentation**: Access the Swagger UI at `http://localhost:8080/swagger-ui.html` when the application is running.
2. **API Specification**: The OpenAPI specification is available at `http://localhost:8080/api-docs`.
3. **Try It Out**: The Swagger UI includes a "Try it out" feature that allows you to test the API endpoints directly from the documentation.
4. **Detailed Information**: Each endpoint is documented with descriptions, request/response schemas, and possible response codes.
5. **Customization**: The OpenAPI configuration is customized in the `OpenApiConfig` class and `application.properties`.

### Continuous Integration

The application uses GitHub Actions for continuous integration:

1. **Automated Builds**: Every push to the repository triggers an automated build.
2. **Test Execution**: All unit tests are automatically executed on each build.
3. **Build Artifacts**: Test reports are generated and stored as build artifacts.
4. **Java Environment**: The CI pipeline uses Java 21, matching the application's requirements.
5. **Dependency Caching**: Maven dependencies are cached to speed up subsequent builds.

The GitHub Actions workflow configuration is located in `.github/workflows/build.yml`.

## Implementation Shortcuts and Proper Solutions

### Shortcuts Taken

1. **In-Memory Database**: The application uses an H2 in-memory database for development, which is not suitable for production use as it doesn't persist data across application restarts.

   **Proper Solution**: Use a production-grade database like PostgreSQL with proper configuration for high availability and data durability. The application includes commented configuration for PostgreSQL in the application.properties file and uses Flyway for database schema migration.

2. **No Authentication/Authorization**: The application doesn't include authentication or authorization mechanisms.

   **Proper Solution**: Implement OAuth2 or JWT-based authentication and role-based authorization to secure the API endpoints.

3. **Limited Error Handling**: While the application includes basic error handling, it may not cover all edge cases.

   **Proper Solution**: Implement more comprehensive error handling, including logging, monitoring, and alerting for production use.

4. **No Rate Limiting**: The application doesn't include rate limiting to prevent abuse.

   **Proper Solution**: Implement rate limiting using a library like Bucket4j or a gateway solution like Spring Cloud Gateway.

6. **Limited Testing**: The application doesn't include comprehensive tests.

   **Proper Solution**: Implement unit tests, integration tests, and end-to-end tests to ensure the application works as expected.

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run `mvn spring-boot:run`
4. The application will be available at `http://localhost:8080`
5. Access the API documentation at `http://localhost:8080/swagger-ui.html`
6. The OpenAPI specification is available at `http://localhost:8080/api-docs`

### API Examples

#### Create an Account

```bash
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountNumber": "ACC123"}'
```

#### Get Account Balance

```bash
curl -X GET http://localhost:8080/api/accounts/ACC123/balance
```

#### Transfer Funds

```bash
curl -X POST http://localhost:8080/api/transactions/transfer \
  -H "Content-Type: application/json" \
  -d '{
    "sourceAccountNumber": "ACC123",
    "targetAccountNumber": "ACC456",
    "amount": 100.00,
    "description": "Payment for services"
  }'
```

#### List Transactions

```bash
curl -X GET http://localhost:8080/api/transactions/account/ACC123
```
