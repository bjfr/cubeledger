-- Insert sample accounts
INSERT INTO accounts (account_number, balance, created_at, updated_at, version, currency)
VALUES
('ACC-001', 1000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, 'SEK'),
('ACC-002', 500.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, 'SEK'),
('ACC-003', 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, 'SEK');

-- Insert sample transactions
-- Deposit into ACC-001
INSERT INTO transactions (source_account_id, target_account_id, amount, timestamp, description, type, currency)
VALUES
(NULL, (SELECT id FROM accounts WHERE account_number = 'ACC-001'), 1000.00, CURRENT_TIMESTAMP, 'Initial deposit', 'DEPOSIT', 'SEK');

-- Transfer from ACC-001 to ACC-002
INSERT INTO transactions (source_account_id, target_account_id, amount, timestamp, description, type, currency)
VALUES
((SELECT id FROM accounts WHERE account_number = 'ACC-001'),
 (SELECT id FROM accounts WHERE account_number = 'ACC-002'),
 500.00, CURRENT_TIMESTAMP, 'Transfer to ACC-002', 'TRANSFER', 'SEK');

-- Withdrawal from ACC-002
INSERT INTO transactions (source_account_id, target_account_id, amount, timestamp, description, type, currency)
VALUES
((SELECT id FROM accounts WHERE account_number = 'ACC-002'),
 NULL,
 0.00, CURRENT_TIMESTAMP, 'ATM Withdrawal', 'WITHDRAWAL', 'SEK');
