-- Add currency column to accounts table
ALTER TABLE accounts
ADD COLUMN currency VARCHAR(3) NOT NULL DEFAULT 'USD';

-- Add currency column to transactions table
ALTER TABLE transactions
ADD COLUMN currency VARCHAR(3) NOT NULL DEFAULT 'USD';

-- Create index for currency columns
CREATE INDEX idx_accounts_currency ON accounts(currency);
CREATE INDEX idx_transactions_currency ON transactions(currency);

-- Update existing accounts to use USD currency
UPDATE accounts SET currency = 'USD';

-- Update existing transactions to use USD currency
UPDATE transactions SET currency = 'USD';
