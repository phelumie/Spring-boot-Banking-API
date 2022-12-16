
CREATE INDEX idx_loan
ON loan (borrower_id,status,is_fully_paid, due_date);

CREATE INDEX idx_withdraw
ON borrower_details (email);
