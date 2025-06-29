-- Create accounts table
CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(255) NOT NULL UNIQUE,
    balance DECIMAL(19, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version BIGINT
);

-- Create transactions table
CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    source_account_id BIGINT,
    target_account_id BIGINT,
    amount DECIMAL(19, 2) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    description VARCHAR(255),
    type VARCHAR(20) NOT NULL,
    version BIGINT,
    FOREIGN KEY (source_account_id) REFERENCES accounts(id),
    FOREIGN KEY (target_account_id) REFERENCES accounts(id)
);

-- Create indexes for better performance
CREATE INDEX idx_transactions_source_account ON transactions(source_account_id);
CREATE INDEX idx_transactions_target_account ON transactions(target_account_id);
CREATE INDEX idx_transactions_timestamp ON transactions(timestamp);
CREATE INDEX idx_accounts_account_number ON accounts(account_number);
