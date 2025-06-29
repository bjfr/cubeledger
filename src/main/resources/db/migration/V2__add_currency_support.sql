-- Add currency column to accounts table
ALTER TABLE accounts
ADD COLUMN currency VARCHAR(3) NOT NULL DEFAULT 'SEK';

-- Add currency column to transactions table
ALTER TABLE transactions
ADD COLUMN currency VARCHAR(3) NOT NULL DEFAULT 'SEK';

-- Create index for currency columns
CREATE INDEX idx_accounts_currency ON accounts(currency);
CREATE INDEX idx_transactions_currency ON transactions(currency);

-- Update existing accounts to use SEK currency
UPDATE accounts SET currency = 'SEK';

-- Update existing transactions to use SEK currency
UPDATE transactions SET currency = 'SEK';
