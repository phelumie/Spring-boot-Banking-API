CREATE INDEX idx_deposit
ON deposit (id,source_account, transaction_date);

CREATE INDEX idx_withdraw
ON withdraw (id,source_account, transaction_date);

CREATE INDEX idx_transfer
ON withdraw (id,recipient_account, transaction_date);

CREATE INDEX idx_transaction_detail
ON transaction_detail (transaction_id,transaction_type,transaction_status);