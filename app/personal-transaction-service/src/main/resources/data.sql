-- =====================
-- Account Types
-- =====================
INSERT INTO account_type (name, code)
VALUES
    ('Savings Account', 'SAV'),
    ('Checking Account', 'CHK'),
    ('Business Account', 'BUS'),
    ('Joint Account', 'JNT')
    ON CONFLICT (code) DO NOTHING;

-- =====================
-- Nicknames
-- =====================
INSERT INTO nickname (nickname)
VALUES
    ('john_savings'),
    ('emma_checking'),
    ('alex_business'),
    ('lucy_joint')
    ON CONFLICT DO NOTHING;

-- =====================
-- Account Details
-- =====================
-- user_id, account_number, account_type_id, group_id, balance, role_id, nickname_id, kyc_id, created_at, updated_at
INSERT INTO account_detail (user_id, account_number, account_type_id, group_id, current_balance, role_id, nickname_id, kyc_id, created_at, updated_at)
VALUES
    (123, 'ACC1001', 1, 10, 5000.00, 1, 1, 100, '2025-10-17 12:00:00', '2025-10-17 12:00:00'),
    (456, 'ACC2001', 2, 10, 3000.00, 2, 2, 200, '2025-10-17 12:00:00', '2025-10-17 12:00:00'),
    (789, 'ACC3001', 3, 20, 15000.00, 3, 3, 300, '2025-10-17 12:00:00', '2025-10-17 12:00:00'),
    (999, 'ACC4001', 4, 20, 8000.00, 4, 4, 400, '2025-10-17 12:00:00', '2025-10-17 12:00:00')
    ON CONFLICT (account_number) DO NOTHING;

-- =====================
-- Transactions
-- =====================
INSERT INTO transactions (credit_account_id, debit_account_id, amount, status, created_at, updated_at)
VALUES
    -- John → Emma
    (2, 1, 150.00, 'COMPLETED', '2025-10-17 12:10:00', '2025-10-17 12:10:00'),

    -- Emma → John
    (1, 2, 75.50, 'PENDING', '2025-10-17 12:20:00', '2025-10-17 12:20:00'),

    -- Alex → Lucy
    (4, 3, 1200.00, 'PREPARED', '2025-10-17 12:30:00', '2025-10-17 12:30:00'),

    -- Lucy → Alex
    (3, 4, 450.00, 'FAILED', '2025-10-17 12:40:00', '2025-10-17 12:40:00'),

    -- John → Alex (business)
    (3, 1, 999.99, 'COMPLETED', '2025-10-17 12:50:00', '2025-10-17 12:50:00')
    ON CONFLICT DO NOTHING;

-- =====================
-- Quick sanity indexes check (optional)
-- =====================
SELECT COUNT(*) AS account_types FROM account_type;
SELECT COUNT(*) AS nicknames FROM nickname;
SELECT COUNT(*) AS accounts FROM account_detail;
SELECT COUNT(*) AS transactions FROM transactions;
