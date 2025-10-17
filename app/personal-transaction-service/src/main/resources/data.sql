-- Account Types
INSERT INTO account_type (name, code)
VALUES
    ('Savings Account', 'SAV'),
    ('Checking Account', 'CHK')
    ON CONFLICT DO NOTHING;

-- Nicknames
INSERT INTO nickname (nickname)
VALUES
    ('john_savings'),
    ('emma_checking')
    ON CONFLICT DO NOTHING;

-- Account Details
INSERT INTO account_detail (
    user_id, account_number, account_type_id, group_id,
    current_balance, role_id, nickname_id, kyc_id, created_at, updated_at
)
VALUES
    (123, '1002003001', 1, 10, 2500.00, 1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (456, '1002003002', 2, 11, 4800.00, 2, 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
    ON CONFLICT DO NOTHING;

-- Transactions (for reference)
INSERT INTO transactions (
    credit_account_id, debit_account_id, amount, status, created_at, updated_at
)
VALUES
    (1, 2, 500.00, 'COMPLETED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 1, 150.00, 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
    ON CONFLICT DO NOTHING;
